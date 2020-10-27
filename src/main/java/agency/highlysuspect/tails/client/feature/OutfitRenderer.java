package agency.highlysuspect.tails.client.feature;

import agency.highlysuspect.tails.client.ClientInit;
import agency.highlysuspect.tails.client.feature.renderer.PartRenderer;
import agency.highlysuspect.tails.client.outfit.Outfit;
import agency.highlysuspect.tails.util.AbstractClientPlayerEntityExt;
import agency.highlysuspect.tails.util.Junk;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import java.util.List;
import java.util.function.Consumer;

public class OutfitRenderer {
	public static final OutfitRenderer EMPTY = new OutfitRenderer(Outfit.EMPTY);
	
	public OutfitRenderer(Outfit outfit) {
		ImmutableList.Builder<PartRenderer<?>> list = ImmutableList.builder();
		outfit.forEachPart(part -> {
			PartRenderer.Factory<?> rendererFactory = ClientInit.PART_RENDERER_REGISTRY.get(ClientInit.PART_TYPE_REGISTRY.getId(part.type));
			if(rendererFactory != null) list.add(rendererFactory.apply(Junk.uncheckedCast(part.config)));
		});
		
		partRenderers = list.build();
		this.outfit = outfit;
	}
	
	private final ImmutableList<PartRenderer<?>> partRenderers;
	private final Outfit outfit;
	
	public static OutfitRenderer forPlayer(AbstractClientPlayerEntity player) {
		return ((AbstractClientPlayerEntityExt) player).tails$getOutfitRenderer();
	}
	
	public List<PartRenderer<?>> getPartRenderers() {
		return partRenderers;
	}
	
	public void forEachPartRenderer(Consumer<PartRenderer<?>> thing) {
		partRenderers.forEach(thing);
	}
	
	public Outfit getOutfit() {
		return outfit;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		OutfitRenderer that = (OutfitRenderer) o;
		
		if(!partRenderers.equals(that.partRenderers)) return false;
		return outfit.equals(that.outfit);
	}
	
	@Override
	public int hashCode() {
		return 31 * partRenderers.hashCode() + outfit.hashCode();
	}
}
