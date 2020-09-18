package agency.highlysuspect.tails.client.feature;

import agency.highlysuspect.tails.client.ClientInit;
import agency.highlysuspect.tails.client.feature.renderer.PartRenderer;
import agency.highlysuspect.tails.client.outfit.ConfiguredPart;
import agency.highlysuspect.tails.client.outfit.Outfit;
import agency.highlysuspect.tails.client.outfit.PartConfig;
import agency.highlysuspect.tails.client.outfit.PartType;
import agency.highlysuspect.tails.util.AbstractClientPlayerEntityExt;
import agency.highlysuspect.tails.util.Junk;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class OutfitRenderer {
	public static OutfitRenderer empty() {
		return new OutfitRenderer();
	}
	
	public static Optional<OutfitRenderer> forPlayer(AbstractClientPlayerEntity player) {
		return ((AbstractClientPlayerEntityExt) player).tails$getOutfitRenderer();
	}
	
	public static OutfitRenderer fromOutfit(Outfit outfit) {
		OutfitRenderer r = new OutfitRenderer();
		outfit.forEachPart(cfgPart -> convertPart(cfgPart).ifPresent(r.partRenderers::add));
		return r;
	}
	
	private static Optional<PartRenderer<?>> convertPart(ConfiguredPart<?> cfgPart) {
		PartType<?> type = cfgPart.type;
		PartConfig config = cfgPart.config;
		
		PartRenderer.Factory<?> rendererFactory = ClientInit.PART_RENDERER_REGISTRY.get(ClientInit.PART_TYPE_REGISTRY.getId(type));
		if(rendererFactory == null) {
			//Could not find a part renderer for this
			return Optional.empty();
		}
		else return Optional.of(rendererFactory.apply(Junk.uncheckedCast(config)));
	}
	
	private final List<PartRenderer<?>> partRenderers = new ArrayList<>();
	
	public List<PartRenderer<?>> getPartRenderers() {
		return partRenderers;
	}
	
	public void forEachPartRenderer(Consumer<PartRenderer<?>> thing) {
		partRenderers.forEach(thing);
	}
}
