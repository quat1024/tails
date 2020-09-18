package agency.highlysuspect.tails.client.outfit;

import agency.highlysuspect.tails.util.Junk;

public class PartConfig {
	//Some concept for encapsulating the idea of a part type with "config options"
	//E.g. a texture that can be overridden, choosing from alternate versions, etc
	
	//TODO this is a crap system for configs - mainly there's no validation or min-maxes on stuff
	// However it is nice and performant code-wide to simply access fields.
	// But you can't really procedurally create a config GUI/Codec from this information alone.
	// I could probably pull it off with some reflection/annotation nonsense?
	
	public MountPoint mountPoint = MountPoint.byName("tail");
	
	@Override
	public int hashCode() {
		return mountPoint.hashCode();
	}
	
	public static class Blank extends PartConfig {
		//No additional options.
	}
	
	public static class AltSwitch extends PartConfig {
		public boolean alternate;
		
		@Override
		public int hashCode() {
			return super.hashCode() ^ Junk.hashBoolean(alternate);
		}
	}
	
	public static class SmallSwitch extends PartConfig {
		public boolean small;
		
		@Override
		public int hashCode() {
			return super.hashCode() ^ Junk.hashBoolean(small);
		}
	}
	
	public static class TippedSwitch extends PartConfig {
		public boolean tipped;
		
		@Override
		public int hashCode() {
			return super.hashCode() ^ Junk.hashBoolean(tipped);
		}
	}
	
	public static class Muzzle extends PartConfig {
		public boolean alternate;
		public int thickness;
		public int length;
		
		@Override
		public int hashCode() {
			return super.hashCode() ^ Junk.hashBoolean(alternate) ^ thickness ^ (length * 31);
		}
	}
}
