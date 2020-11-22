package agency.highlysuspect.tails.util;

import net.minecraft.util.math.MathHelper;

public class Junk {
	public static <T> T uncheckedCast(Object thing) {
		//noinspection unchecked
		return (T) thing;
	}
	
	public static float PIf = (float) Math.PI;
	public static float HALF_PIf = (float) (Math.PI / 2);
	
	public static float sin0_1(float input) {
		return MathHelper.sin(input) / 2 + 0.5f;
	}
}
