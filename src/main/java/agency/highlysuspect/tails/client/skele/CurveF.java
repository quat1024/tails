package agency.highlysuspect.tails.client.skele;

import net.minecraft.util.math.MathHelper;

import static agency.highlysuspect.tails.util.Junk.PIf;
import static agency.highlysuspect.tails.util.Junk.HALF_PIf;

public interface CurveF {
	float apply(float in);
	
	default CurveF clamped() {
		return f -> apply(MathHelper.clamp(f, 0, 1));
	}
	
	CurveF LINEAR = f -> f;
	CurveF SIN = f -> MathHelper.sin(f * PIf - HALF_PIf) / 2 + 0.5f;
	CurveF SIN_OUT = f -> MathHelper.sin(f * HALF_PIf);
	CurveF SIN_IN = f -> MathHelper.sin(f * HALF_PIf + HALF_PIf);
	
	static CurveF step(int steps) {
		return f -> MathHelper.floor(f * steps + 0.001f) / (float) steps;
	}
}
