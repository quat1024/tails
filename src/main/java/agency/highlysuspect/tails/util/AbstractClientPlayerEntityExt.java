package agency.highlysuspect.tails.util;

import agency.highlysuspect.tails.client.feature.OutfitRenderer;
import agency.highlysuspect.tails.client.outfit.Outfit;

public interface AbstractClientPlayerEntityExt {
	/**
	 * @see agency.highlysuspect.tails.mixin.client.AbstractClientPlayerEntityMixin
	 */
	Outfit tails$getOutfit();
	void tails$setOutfit(Outfit outfit);
	OutfitRenderer tails$getOutfitRenderer();
	
	default boolean tails$hasNonemptyOutfit() {
		return tails$getOutfit() != Outfit.EMPTY;
	}
}
