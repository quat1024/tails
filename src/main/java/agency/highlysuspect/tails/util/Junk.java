package agency.highlysuspect.tails.util;

public class Junk {
	public static <T> T uncheckedCast(Object thing) {
		//noinspection unchecked
		return (T) thing;
	}
}
