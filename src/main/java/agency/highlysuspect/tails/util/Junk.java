package agency.highlysuspect.tails.util;

public class Junk {
	public static <T> T uncheckedCast(Object thing) {
		//noinspection unchecked
		return (T) thing;
	}
	
	public static int hashBoolean(boolean thing) {
		return thing ? 0xF0F0F0F0 : 0x0F0F0F0F;
	}
}
