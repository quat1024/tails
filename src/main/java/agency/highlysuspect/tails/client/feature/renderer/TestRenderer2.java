package agency.highlysuspect.tails.client.feature.renderer;

import agency.highlysuspect.tails.Init;
import agency.highlysuspect.tails.client.feature.PartRenderContext;
import agency.highlysuspect.tails.client.outfit.PartConfig;
import agency.highlysuspect.tails.client.skele.*;
import agency.highlysuspect.tails.util.Junk;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TestRenderer2 extends PartRenderer<PartConfig.Blank> {
	public TestRenderer2(PartConfig.Blank config) {
		super(config);
		buildSkeleton();
	}
	
	private static void buildSkeleton() {
		//TODO: Make these not ModelParts, just using them as a lazy way to get ez textured cuboids really
		// Doesn't need to be modelpart at all
		ModelPart seg1 = new ModelPart(64, 32, 22, 0).addCuboid(-2.5F, -2.5F, -2F, 5, 5, 8);
		ModelPart seg2 = new ModelPart(64, 32, 0, 0).addCuboid(-2F, -2F, 0F, 4, 4, 7);
		ModelPart seg3 = new ModelPart(64, 32, 0, 11).addCuboid(-1.5F, -1.5F, 0F, 3, 3, 8);
		ModelPart seg4 = new ModelPart(64, 32, 0, 22).addCuboid(-1F, -1F, 0F, 2, 2, 7);
		
		doot = new Skeleton(
			new Bone.Builder("root", new Vector3f(0f, 0f, 3f / 16f))
				.addBone(
					new Bone.Builder("segment1", new Vector3f(0f, 0f, 0))
						.addDrawable(new ModelPartBoneDrawable(seg1))
						.addBone(
							new Bone.Builder("segment2", new Vector3f(0f, 0.3f / 16f, 5f / 16f))
								.addDrawable(new ModelPartBoneDrawable(seg2))
								.addBone(
									new Bone.Builder("segment3", new Vector3f(0f, 0.2f / 16f, 5.5f / 16f))
										.addDrawable(new ModelPartBoneDrawable(seg3))
										.addBone(
											new Bone.Builder("segment4", new Vector3f(0f, 0.4f / 16f, 7.5f / 16f))
												.addDrawable(new ModelPartBoneDrawable(seg4))
										)
								)
						)
				).build()
		);
	}
	
	public static Skeleton doot;
	
	public static final Pose.Interpolator<Pose.EulerRotationPose> interp = new Pose.EulerRotationPose.Interpolator();
	
	public static boolean poggies = false;
	
	private float sneakiness;
	
	@Override
	public void renderPart(PartRenderContext ctx) {
		if(Math.random() < 0.05) buildSkeleton(); //ease of hot reloading
		
		//TODO: This is the wrong way to do framerate-independent exponential decay curves. It's not actually framerate independent
		sneakiness = MathHelper.lerp(0.03f * ctx.tickDelta, sneakiness, ctx.player.isSneaking() ? 1f : 0f);
		
		//these can technically go in buildSkeleton btw i'm just doing them inline for ease of hot-reloading as well
		Pose.EulerRotationPose.Immutable bindPose = new Pose.EulerRotationPose.Immutable.Builder()
			.put("root", new EulerAngles((float) Math.toRadians(-100f), 0, 0))
			.build();
		
		Pose.EulerRotationPose.Immutable wagLeft = new Pose.EulerRotationPose.Immutable.Builder()
			.put("segment1", new EulerAngles((float) Math.toRadians(-8F), -0.16f, 0))
			.put("segment2", new EulerAngles((float) Math.toRadians(-8F), -0.16f, 0))
			.put("segment3", new EulerAngles((float) Math.toRadians(10F), -0.16f, 0))
			.put("segment4", new EulerAngles((float) Math.toRadians(20F), -0.16f, 0))
			.build();
		
		Pose.EulerRotationPose.Immutable wagRight = new Pose.EulerRotationPose.Immutable.Builder()
			.put("segment1", new EulerAngles((float) Math.toRadians(-8F), 0.16f, 0))
			.put("segment2", new EulerAngles((float) Math.toRadians(-8F), 0.16f, 0))
			.put("segment3", new EulerAngles((float) Math.toRadians(10F), 0.16f, 0))
			.put("segment4", new EulerAngles((float) Math.toRadians(20F), 0.16f, 0))
			.build();
		
		Pose.EulerRotationPose wag = interp.interpolate(wagLeft, wagRight, Junk.sin0_1(ctx.seconds * 4));
		
		Pose.EulerRotationPose.Immutable sneakyWagLeft = new Pose.EulerRotationPose.Immutable.Builder()
			.put("segment1", new EulerAngles((float) Math.toRadians(-30F), -0.08f, -0.1f))
			.put("segment2", new EulerAngles((float) Math.toRadians(-40F), -0.08f, 0))
			.put("segment3", new EulerAngles((float) Math.toRadians(80F), -0.1f, 0))
			.put("segment4", new EulerAngles((float) Math.toRadians(90F), -0.15f, 0))
			.build();
		
		Pose.EulerRotationPose.Immutable sneakyWagRight = new Pose.EulerRotationPose.Immutable.Builder()
			.put("segment1", new EulerAngles((float) Math.toRadians(-30F), 0.08f, 0.1f))
			.put("segment2", new EulerAngles((float) Math.toRadians(-40F), 0.08f, 0))
			.put("segment3", new EulerAngles((float) Math.toRadians(80F), 0.1f, 0))
			.put("segment4", new EulerAngles((float) Math.toRadians(90F), 0.15f, 0))
			.build();
		
		Pose.EulerRotationPose sneakyWag = interp.interpolate(sneakyWagLeft, sneakyWagRight, Junk.sin0_1(ctx.seconds * 1.5f));
		
		Pose.EulerRotationPose hah = interp.interpolate(wag, sneakyWag, sneakiness);
		Pose.EulerRotationPose hmm = interp.add(bindPose, hah);
		
		doot.draw(hmm, ctx);
		
		if(poggies) {
			Init.LOGGER.info(Skeleton.CODEC.encodeStart(JsonOps.INSTANCE, doot).getOrThrow(false, Init.LOGGER::fatal));
			poggies ^= true;
		}
	}
	
	//Just fucking around, put this somewhere else later probably
	public static class ModelPartBoneDrawable implements BoneDrawable {
		public ModelPartBoneDrawable(ModelPart part) {
			this.part = part;
		}
		
		private final ModelPart part;
		
		@Override
		public void draw(Bone b, PartRenderContext ctx) {
			//obvious TODO
			VertexConsumer v = ctx.vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("tails", "textures/stolen/dragon_tail.png")));
			part.render(ctx.matrices, v, ctx.light, ctx.overlay);
		}
	}
}
