package agency.highlysuspect.tails.mixin.client;

import agency.highlysuspect.tails.client.feature.OutfitRenderer;
import agency.highlysuspect.tails.client.outfit.Outfit;
import agency.highlysuspect.tails.util.AbstractClientPlayerEntityExt;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin implements AbstractClientPlayerEntityExt {
	private OutfitRenderer tails$outfitRenderer;
	private int tails$cachedOutfitHash = 0;
	
	@Override
	public Optional<OutfitRenderer> tails$getOutfitRenderer() {
		Optional<Outfit> maybeOutfit = Outfit.forPlayer((PlayerEntity) (Object) this);
		if(!maybeOutfit.isPresent()) {
			tails$outfitRenderer = null;
			tails$cachedOutfitHash = 0;
			return Optional.empty();
		}
		
		Outfit outfit = maybeOutfit.get();
		int outfitHash = outfit.hashCode();
		if(tails$cachedOutfitHash != outfitHash || tails$outfitRenderer == null) {
			tails$cachedOutfitHash = outfitHash;
			tails$outfitRenderer = OutfitRenderer.fromOutfit(outfit);
		}
		
		return Optional.of(tails$outfitRenderer);
	}
}