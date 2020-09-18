package agency.highlysuspect.tails.client.feature.renderer;

import agency.highlysuspect.tails.client.outfit.PartConfig;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.function.Function;

public abstract class PartRenderer<CFG extends PartConfig> {
	public interface Factory<X extends PartConfig> extends Function<X, PartRenderer<X>> {}
	
	public PartRenderer(CFG config) {
		this.config = config;
	}
	
	protected final PartConfig config;
	
	//TODO: What is the proper API here, what pieces do I need in order to render.
	public void transformAndRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, PlayerEntityModel<AbstractClientPlayerEntity> playerModel) {
		matrices.push();
		config.mountPoint.apply(matrices, playerModel);
		renderPart(matrices, vertexConsumers, light, player, playerModel);
		matrices.pop();
	}
	
	public abstract void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, PlayerEntityModel<AbstractClientPlayerEntity> playerModel);
}
