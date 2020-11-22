package agency.highlysuspect.tails.client.feature.renderer;

import agency.highlysuspect.tails.client.feature.PartRenderContext;
import agency.highlysuspect.tails.client.outfit.PartConfig;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class TestRenderer extends PartRenderer<PartConfig.AltSwitch> {
	public TestRenderer(PartConfig.AltSwitch config) {
		super(config);
		
		tailBase = new ModelPart(64, 32, 22, 0);
		tailBase.addCuboid(-2.5F, -2.5F, -2F, 5, 5, 8);
		tailBase.pitch = (float) Math.toRadians(-40);
		
		ModelPart tail1 = new ModelPart(64, 32, 0, 0);
		tail1.addCuboid(-2F, -2F, 0F, 4, 4, 7);
		tail1.setPivot(0F, 0.3F, 5F);
		tail1.pitch = (float) Math.toRadians(-8F);
		
		tailBase.addChild(tail1);
		
		ModelPart tail2 = new ModelPart(64, 32, 0, 11);
		tail2.addCuboid(-1.5F, -1.5F, 0F, 3, 3, 8);
		tail2.setPivot(0F, 0.2F, 5.5F);
		tail2.pitch = (float) Math.toRadians(10F);
		
		tail1.addChild(tail2);
		
		ModelPart tail3 = new ModelPart(64, 32, 0, 22);
		tail3.addCuboid(-1F, -1F, 0F, 2, 2, 7);
		tail3.setPivot(0F, 0.4F, 7.5F);
		tail3.pitch = (float) Math.toRadians(20F);
		
		tail2.addChild(tail3);
	}
	
	private final ModelPart tailBase;
	
	@Override
	public void renderPart(PartRenderContext ctx) {
		VertexConsumer v = ctx.vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("tails", "textures/stolen/dragon_tail.png")));
		Matrix4f model = ctx.matrices.peek().getModel();
		Matrix3f normal = ctx.matrices.peek().getNormal();
		
//		matrices.push();
//		matrices.translate(0.5f - 2/16f, 0f, 0f);
//		playerModel.rightArm.render(matrices, v, light, overlay);
//		matrices.pop();
		
		ctx.matrices.push();
		ctx.matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-45));
		tailBase.render(ctx.matrices, v, ctx.light, ctx.overlay);
		ctx.matrices.pop();
		
//		for(int i = 0; i < 4; i++) {
//			float x = i;
//			float y = i;
//			float z = i;
//			v.vertex(model, x, y, z).color(1f, 1f, 1f, 1f).texture(0, 0).overlay(overlay).light(light).normal(normal, 0, 0, 1).next();
//			v.vertex(model, x, y, z + 1).color(1f, 1f, 1f, 1f).texture(0, 1).overlay(overlay).light(light).normal(normal, 0, 0, 1).next();
//			v.vertex(model, x + 1, y, z + 1).color(1f, 1f, 1f, 1f).texture(1, 1).overlay(overlay).light(light).normal(normal, 0, 0, 1).next();
//			v.vertex(model, x + 1, y, z).color(1f, 1f, 1f, 1f).texture(1, 0).overlay(overlay).light(light).normal(normal, 0, 0, 1).next();
//		}
//		
//		//This works though
//		ItemRenderer pog2 = MinecraftClient.getInstance().getItemRenderer();
//		matrices.push();
//		matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-45f));
//		pog2.renderItem(new ItemStack(Items.BREAD), ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
//		matrices.pop();
	}
}
