package agency.highlysuspect.tails.client.skele;

import agency.highlysuspect.tails.client.feature.PartRenderContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Skeleton {
	public Skeleton(Bone rootBone) {
		this.root = rootBone;
	}
	
	public final Bone root;
	
	public void draw(Pose pose, PartRenderContext ctx) {
		root.draw(pose, ctx);
	}
	
	//TODO some notion of "membrane points" ?
	// (points that get transformed along the skeleton only numerically, and are drawn in untransformed space at the end)
	// there also needs to be some way of constructing points based off of transformations of other points (by weighted average)
	// you can then build up triangles (here in the Skeleton) out of these points and draw them
	// the idea is so that you can draw things that go in *between* two bones
	// name gives away that one of the most important use cases is drawing the membrane between two wingfingers
	
	public static final Codec<Skeleton> CODEC = RecordCodecBuilder.create(i -> i.group(
		Bone.CODEC.fieldOf("rootBone").forGetter(skeleton -> skeleton.root)
	).apply(i, Skeleton::new));
}
