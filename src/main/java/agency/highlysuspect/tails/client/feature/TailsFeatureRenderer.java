package agency.highlysuspect.tails.client.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.Map;

public class TailsFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	public TailsFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
		super(context);
	}
	
	private static final long start = System.currentTimeMillis(); 
	private final Map<String, Object> persistentData = new HashMap<>();
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		//TODO uncomment before release; just for testing to hide the player model
		//if(player.isInvisible()) return;
		
		getTexture(player);
		
		PlayerEntityModel<AbstractClientPlayerEntity> playerModel = getContextModel();
		int overlay = LivingEntityRenderer.getOverlay(player, 0);
		
		//todo uhh, idk this sucks lmao
		float seconds = (System.currentTimeMillis() - start) / 1000f; 
		
		PartRenderContext partRenderContext = new PartRenderContext(matrices, vertexConsumers, light, overlay, player, playerModel, tickDelta, seconds, persistentData);
		
		OutfitRenderer.forPlayer(player)
			.forEachPartRenderer(p -> p.transformAndRender(partRenderContext));
	}
}
