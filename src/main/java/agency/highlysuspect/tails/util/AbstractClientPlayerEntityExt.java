package agency.highlysuspect.tails.util;

import agency.highlysuspect.tails.client.feature.OutfitRenderer;

import java.util.Optional;

public interface AbstractClientPlayerEntityExt {
	/**
	 * @see agency.highlysuspect.tails.mixin.client.AbstractClientPlayerEntityMixin
	 */
	Optional<OutfitRenderer> tails$getOutfitRenderer();
}
