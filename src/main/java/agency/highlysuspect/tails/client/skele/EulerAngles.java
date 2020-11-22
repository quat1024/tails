package agency.highlysuspect.tails.client.skele;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

public class EulerAngles {
	public EulerAngles(float pitch, float yaw, float roll) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}
	
	public static final EulerAngles ZERO = new EulerAngles(0, 0, 0);
	
	public final float pitch, yaw, roll;
	
	public void applyRotation(MatrixStack matrices) {
		//Lifted straight from ModelPart
		if (roll != 0.0F) {
			matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(roll));
		}
		
		if (yaw != 0.0F) {
			matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(yaw));
		}
		
		if (pitch != 0.0F) {
			matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(pitch));
		}
	}
	
	public static EulerAngles interpolate(EulerAngles a, EulerAngles b, float t) {
		return new EulerAngles(
			a.pitch * (1 - t) + b.pitch * t,
			a.yaw * (1 - t) + b.yaw * t,
			a.roll * (1 - t) + b.roll * t
		);
	}
	
	public static EulerAngles add(EulerAngles a, EulerAngles b) {
		return new EulerAngles(
			a.pitch + b.pitch,
			a.yaw + b.yaw,
			a.roll + b.roll
		);
	}
}
