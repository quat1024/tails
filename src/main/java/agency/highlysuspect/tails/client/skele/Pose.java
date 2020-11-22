package agency.highlysuspect.tails.client.skele;

import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.Map;

//TODO: Can poses encapsulate translations as well, allowing you to animate the position/offset of bones as well as their angles?
// Currently bones are rigid and the Bone controls its own translation.
// This is not ideal, but it makes the math a whooooooollllle lot easier.
// Math on, and interpolation of, rotations, is a million times easier than translation + rotations.
// You can interpolate Euler angles super easily and it works, or for slightly better quality can interpolate quaternions
// (that's kinda hard, which is why the Pose implementation is left open, so i can implement quat interpolation later).
// It's also fairly easy to add rotations to eachother.
// It's possible to interpolate translation and rotation, but it's really hard - you need to get into some cursed
// geometric algebra bullshit, or like, dual quaternions, or whatever
// And it makes my head spin.
// ...
// So we're starting with a minimum viable product. I can branch out later.
public interface Pose {
	//maybe swap this out for simply a data-gathering method, and make this apply method a default one
	void applyTransformation(Bone bone, MatrixStack matrices);
	
	interface Interpolator<P extends Pose> {
		P interpolate(P a, P b, float t);
		P add(P a, P b);
	}
	
	interface EulerRotationPose extends Pose {
		@Override
		default void applyTransformation(Bone b, MatrixStack matrices) {
			getRotation(b).applyRotation(matrices);
		}
		
		EulerAngles getRotation(Bone bone);
		
		class Immutable implements EulerRotationPose {
			public Immutable(Map<String, EulerAngles> rotations) {
				this.rotations = rotations;
			}
			
			private final Map<String, EulerAngles> rotations;
			
			@Override
			public EulerAngles getRotation(Bone bone) {
				return rotations.get(bone.name);
			}
			
			public static class Builder {
				Map<String, EulerAngles> rotations = new HashMap<>();
				
				public EulerRotationPose.Immutable.Builder put(String name, EulerAngles rotation) {
					rotations.put(name, rotation);
					return this;
				}
				
				public EulerRotationPose.Immutable build() {
					return new EulerRotationPose.Immutable(rotations);
				}
			}
		}
		
		class Interpolator implements Pose.Interpolator<EulerRotationPose> {
			//TODO: This smells somewhat monoial, if monoids had an interpolate method
			// Is this some kind of algebra i can leverage? Lmao
			
			@Override
			public EulerRotationPose interpolate(EulerRotationPose a, EulerRotationPose b, float t) {
				return bone -> {
					EulerAngles aAng = a.getRotation(bone);
					EulerAngles bAng = b.getRotation(bone);
					if(aAng == null && bAng == null) return EulerAngles.ZERO;
					else if(aAng == null) return bAng;
					else if(bAng == null) return aAng;
					else return EulerAngles.interpolate(aAng, bAng, t);
				};
			}
			
			@Override
			public EulerRotationPose add(EulerRotationPose a, EulerRotationPose b) {
				return bone -> {
					EulerAngles aAng = a.getRotation(bone);
					EulerAngles bAng = b.getRotation(bone);
					
					if(aAng == null && bAng == null) return EulerAngles.ZERO;
					else if(aAng == null) return bAng;
					else if(bAng == null) return aAng;
					else return EulerAngles.add(aAng, bAng);
				};
			}
		}
	}
}
