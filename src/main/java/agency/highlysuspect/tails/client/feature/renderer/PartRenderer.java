package agency.highlysuspect.tails.client.feature.renderer;

import agency.highlysuspect.tails.client.feature.PartRenderContext;
import agency.highlysuspect.tails.client.outfit.PartConfig;

import java.util.function.Function;

public abstract class PartRenderer<CFG extends PartConfig> {
	public interface Factory<X extends PartConfig> extends Function<X, PartRenderer<X>> {}
	
	public PartRenderer(CFG config) {
		this.config = config;
	}
	
	protected final PartConfig config;
	
	public void transformAndRender(PartRenderContext ctx) {
		ctx.matrices.push();
		config.mountPoint.apply(ctx.matrices, ctx.playerModel);
		renderPart(ctx);
		ctx.matrices.pop();
	}
	
	public abstract void renderPart(PartRenderContext ctx);
}
