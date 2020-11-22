package agency.highlysuspect.tails.client.skele;

import agency.highlysuspect.tails.client.feature.PartRenderContext;
import com.mojang.serialization.Codec;

//TODO
public interface BoneDrawable {
	void draw(Bone b, PartRenderContext ctx); //Probably needs some other stuff too
	
	class Noop implements BoneDrawable {
		@Override
		public void draw(Bone b, PartRenderContext ctx) {
			//No-op
		}
	}
	
	Codec<BoneDrawable> CODEC = Codec.unit(Noop::new); //TODO
}
