package agency.highlysuspect.tails.client.feature.renderer;

import agency.highlysuspect.tails.client.outfit.PartConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Random;

public class TestRenderer extends PartRenderer<PartConfig.AltSwitch> {
	public TestRenderer(PartConfig.AltSwitch config) {
		super(config);
	}
	
	@Override
	public void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, PlayerEntityModel<AbstractClientPlayerEntity> playerModel) {
//		//I don't know why this doesn't draw anything
//		VertexConsumer pog = vertexConsumers.getBuffer(RenderLayer.getItemEntityTranslucentCull(new Identifier("oops", "whoops")));
//		
//		Random asd = new Random();
//		for(int i = 0; i < 100; i++) {
//			pog.vertex(asd.nextDouble() * 10, asd.nextDouble()* 10, asd.nextDouble()* 10).color(asd.nextFloat(), asd.nextFloat(), asd.nextFloat(), asd.nextFloat()).texture(asd.nextFloat(), asd.nextFloat()).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0, 1, 0).next();
//		}
		
		//This works though
		ItemRenderer pog2 = MinecraftClient.getInstance().getItemRenderer();
		pog2.renderItem(new ItemStack(Items.BREAD), ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
	}
}
