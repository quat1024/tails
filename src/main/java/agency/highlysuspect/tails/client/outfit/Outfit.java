package agency.highlysuspect.tails.client.outfit;

import agency.highlysuspect.tails.client.ClientInit;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;
import java.util.function.Consumer;

public class Outfit {
	public static Outfit empty() {
		return Outfit.ofParts(Collections.emptyList());
	}
	
	public static Optional<Outfit> forPlayer(PlayerEntity player) {
		//TODO actually check something about the player
		// Maybe have a field mixed on to PlayerEntity
		// For now this is recreated every call intentionally, to allow hot-reloading it
		
		ConfiguredPart<PartConfig.AltSwitch> xd = ClientInit.TEST.create(c -> c.mountPoint = MountPoint.byName("tail"));
		return Optional.of(Outfit.ofParts(Collections.singleton(xd)));
	}
	
	public static Outfit ofParts(Collection<ConfiguredPart<?>> parts) {
		Outfit o = new Outfit();
		o.parts.addAll(parts);
		return o;
	}
	
	private final List<ConfiguredPart<?>> parts = new ArrayList<>();
	
	public List<ConfiguredPart<?>> getParts() {
		return parts;
	}
	
	public void forEachPart(Consumer<ConfiguredPart<?>> thing) {
		parts.forEach(thing);
	}
	
	@Override
	public int hashCode() {
		//In lieu of a dirty flag to rebuild outfit renderers for each player, I just cache the hash value and rebuild it when the hashes differ.
		//Premature optimization? Yeah, probably. I don't know if it's even faster.
		//TODO: profile this.
		return parts.hashCode();
	}
}