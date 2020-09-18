package agency.highlysuspect.tails.client.outfit;

import agency.highlysuspect.tails.mixin.client.ModelPartAccessor;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

//TODO: client server separation, maybe? does it make sense here?

//A mount point defines a transformation to some point along the player model.
//After following a mount point, you can think of all drawing as relative to a point on the player's body,
//and not as "relative to a point in 3d space where the player happens to be standing" or whatever.
//(i.e. you don't have to manually rotate/translate to compensate for the player sneaking)
public abstract class MountPoint {
	public MountPoint(String name) {
		this.name = name;
	}
	
	public final String name;
	
	private static final Map<String, MountPoint> byName = new HashMap<>();
	public static MountPoint byName(String name) {
		return byName.get(name);
	}
	
	public abstract void apply(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model);
	
	public static class Cuboidal extends MountPoint {
		public Cuboidal(String name, Function<PlayerEntityModel<AbstractClientPlayerEntity>, ModelPart> modelPartGetter, BiConsumer<MatrixStack, ModelPart.Cuboid> transformer) {
			super(name);
			this.modelPartGetter = modelPartGetter;
			this.transformer = transformer;
		}
		
		private final Function<PlayerEntityModel<AbstractClientPlayerEntity>, ModelPart> modelPartGetter;
		private final BiConsumer<MatrixStack, ModelPart.Cuboid> transformer;
		
		@Override
		public void apply(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model) {
			ModelPart part = modelPartGetter.apply(model);
			part.rotate(matrices);
			ModelPart.Cuboid cuboid = ((ModelPartAccessor) part).tails$getCuboids().get(0);
			transformer.accept(matrices, cuboid);
		}
	}
	
	static {
		//This giant block of code initializes 42 mount points along the player's body.
		//With names like head_bottom, left_leg_right, etc.
		//There's a little bit on the end that adds a "tail" point for a total of 43.
		
		Map<String, Function<PlayerEntityModel<AbstractClientPlayerEntity>, ModelPart>> namedParts = new HashMap<>();
		namedParts.put("head", model -> model.head);
		namedParts.put("torso", model -> model.torso);
		namedParts.put("right_arm", model -> model.rightArm);
		namedParts.put("left_arm", model -> model.leftArm);
		namedParts.put("right_leg", model -> model.rightLeg);
		namedParts.put("left_leg", model -> model.leftLeg);
		
		Map<String, BiConsumer<MatrixStack, ModelPart.Cuboid>> namedTransformers = new HashMap<>();
		namedTransformers.put("origin", (matrices, cuboid) -> {
			//average the x, y, and z coordinates to go to the center of the cuboid
			//this is inside the cuboid (so this mount point isn't super useful)
			matrices.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
		});
		namedTransformers.put("top", (matrices, cuboid) -> {
			//average the x and z coordinates, but use the top y coordinate
			matrices.translate((cuboid.minX + cuboid.maxX) / 32f, cuboid.minY / 16f, (cuboid.minZ + cuboid.maxZ) / 32f);
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
		});
		namedTransformers.put("bottom", (matrices, cuboid) -> {
			//average the x and z coordinates, but use the bottom y coordinate
			matrices.translate((cuboid.minX + cuboid.maxX) / 32f, cuboid.maxY / 16f, (cuboid.minZ + cuboid.maxZ) / 32f);
		});
		namedTransformers.put("left", (matrices, cuboid) -> {
			//average the y and z coordinates, but use the left x coordinate
			matrices.translate(cuboid.maxX / 16f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90));
		});
		namedTransformers.put("right", (matrices, cuboid) -> {
			//average the y and z coordinates, but use the right x coordinate
			matrices.translate(cuboid.minX / 16f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90));
		});
		namedTransformers.put("front", (matrices, cuboid) -> {
			//average the x and y coordinates, but use the front z coordinate
			matrices.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, cuboid.minZ / 16f);
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
		});
		namedTransformers.put("back", (matrices, cuboid) -> {
			//average the x and y coordinates, but use the back z coordinate
			matrices.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, cuboid.maxZ / 16f);
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
		});
		
		//take the cartesian product of those two sets
		namedParts.forEach((partName, partGetter) -> {
			namedTransformers.forEach((transformerName, transformer) -> {
				String name = partName + '_' + transformerName;
				byName.put(name, new MountPoint.Cuboidal(name, partGetter, transformer));
			});
		});
		
		//and throw on a tail-base mount point for good measure
		byName.put("tail", new MountPoint.Cuboidal("tail",
			namedParts.get("torso"),
			(matrices, cuboid) -> {
				//average the x coordinate, use the bottom y coordinate and the back z coordinate
				matrices.translate((cuboid.minX + cuboid.maxX) / 32f, cuboid.maxY / 16f, cuboid.maxZ / 16f);
				matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
			}
		));
	}
}
