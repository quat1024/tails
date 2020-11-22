package agency.highlysuspect.tails.client.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Map;

public class PartRenderContext {
	public PartRenderContext(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, AbstractClientPlayerEntity player, PlayerEntityModel<AbstractClientPlayerEntity> playerModel, float tickDelta, float seconds, Map<String, Object> persistentData) {
		this.matrices = matrices;
		this.vertexConsumers = vertexConsumers;
		this.light = light;
		this.overlay = overlay;
		this.player = player;
		this.playerModel = playerModel;
		this.tickDelta = tickDelta;
		this.seconds = seconds;
		this.persistentData = persistentData;
	}
	
	public MatrixStack matrices;
	public VertexConsumerProvider vertexConsumers;
	public int light;
	public int overlay;
	public AbstractClientPlayerEntity player;
	public PlayerEntityModel<AbstractClientPlayerEntity> playerModel;
	public float tickDelta;
	public float seconds;
	//TODO this should be a) more type-safe, and b) should be resistant against name-clashes with different part renderers
	// i.e. every part renderer should have its own "namespace"
	public Map<String, Object> persistentData;
}
