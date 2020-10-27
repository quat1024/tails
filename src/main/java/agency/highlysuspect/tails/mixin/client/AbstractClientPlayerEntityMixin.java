package agency.highlysuspect.tails.mixin.client;

import agency.highlysuspect.tails.client.ClientInit;
import agency.highlysuspect.tails.client.feature.OutfitRenderer;
import agency.highlysuspect.tails.client.outfit.MountPoint;
import agency.highlysuspect.tails.client.outfit.Outfit;
import agency.highlysuspect.tails.util.AbstractClientPlayerEntityExt;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin implements AbstractClientPlayerEntityExt {
	private Outfit tails$outfit = Outfit.EMPTY;
	private OutfitRenderer tails$outfitRenderer = OutfitRenderer.EMPTY;
	
	@Override
	public Outfit tails$getOutfit() {
		//todo TEST
		// Reference equality is intentional
		if(tails$outfit == Outfit.EMPTY) {
			tails$outfit = Outfit.createTestOutfit();
		}
		
		return tails$outfit;
	}
	
	@Override
	public void tails$setOutfit(Outfit outfit) {
		tails$outfit = outfit;
	}
	
	@Override
	public OutfitRenderer tails$getOutfitRenderer() {
		//Reference equality is intentional
		if(tails$outfitRenderer.getOutfit() != tails$getOutfit()) {
			tails$outfitRenderer = new OutfitRenderer(tails$outfit);
		}
		
		return tails$outfitRenderer;
	}
}