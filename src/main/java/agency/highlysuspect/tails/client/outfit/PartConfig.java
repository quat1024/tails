package agency.highlysuspect.tails.client.outfit;

public class PartConfig {
	//Some concept for encapsulating the idea of a part type with "config options"
	//E.g. a texture that can be overridden, choosing from alternate versions, etc
	
	//TODO this is a crap system for configs - mainly there's no validation or min-maxes on stuff
	// However it is nice and performant code-wide to simply access fields.
	// But you can't really procedurally create a config GUI/Codec from this information alone.
	// I could probably pull it off with some reflection/annotation nonsense?
	
	public MountPoint mountPoint = MountPoint.byName("tail");
	
	public static class Blank extends PartConfig {
		//No additional options.
	}
	
	public static class AltSwitch extends PartConfig {
		public boolean alternate;
	}
	
	public static class SmallSwitch extends PartConfig {
		public boolean small;
	}
	
	public static class TippedSwitch extends PartConfig {
		public boolean tipped;
	}
	
	public static class Muzzle extends PartConfig {
		public boolean alternate;
		public int thickness;
		public int length;
	}
}
