package agency.highlysuspect.tails.client.outfit;

import agency.highlysuspect.tails.client.ClientInit;
import agency.highlysuspect.tails.util.AbstractClientPlayerEntityExt;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class Outfit {
	public static final Outfit EMPTY = new Outfit(Collections.emptyList());
	
	public Outfit(Collection<ConfiguredPart<?>> parts) {
		this.parts = ImmutableList.copyOf(parts);
	}
	
	private final ImmutableList<ConfiguredPart<?>> parts;
	
	public static Outfit forPlayer(PlayerEntity player) {
		return ((AbstractClientPlayerEntityExt) player).tails$getOutfit();
	}
	
	public static Outfit createTestOutfit() {
		return new Outfit(Collections.singleton(ClientInit.TEST_PART_TYPE.create(c -> c.mountPoint = MountPoint.byName("tail"))));
	}
	
	public ImmutableList<ConfiguredPart<?>> getParts() {
		return parts;
	}
	
	public void forEachPart(Consumer<ConfiguredPart<?>> thing) {
		parts.forEach(thing);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Outfit outfit = (Outfit) o;
		
		return parts.equals(outfit.parts);
	}
	
	@Override
	public int hashCode() {
		return parts.hashCode();
	}
}