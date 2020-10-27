package agency.highlysuspect.tails.client.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TailsFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	public TailsFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
		super(context);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		//TODO uncomment; for testing
		//if(player.isInvisible()) return;
		
		getTexture(player);
		
		PlayerEntityModel<AbstractClientPlayerEntity> playerModel = getContextModel();
		int overlay = LivingEntityRenderer.getOverlay(player, 0);
		
		OutfitRenderer.forPlayer(player)
			.forEachPartRenderer(p -> p.transformAndRender(matrices, vertexConsumers, light, overlay, player, playerModel));
	}
}
