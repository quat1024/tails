package agency.highlysuspect.tails.client.skele;

import agency.highlysuspect.tails.client.feature.PartRenderContext;
import agency.highlysuspect.tails.util.CodecStuff;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.util.math.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bone {
	public Bone(String name, Vector3f translation, List<BoneDrawable> drawables, List<Bone> children) {
		this.name = name;
		this.translation = translation;
		this.drawables = drawables;
		this.children = children;
	}
	
	public final String name;
	public final Vector3f translation;
	public final List<BoneDrawable> drawables;
	public final List<Bone> children;
	
	public void draw(Pose pose, PartRenderContext ctx) {
		ctx.matrices.push();
		
		ctx.matrices.translate(translation.getX(), translation.getY(), translation.getZ());
		pose.applyTransformation(this, ctx.matrices);
		
		for(BoneDrawable drawable : drawables) {
			drawable.draw(this, ctx);
		}
		
		for(Bone child : children) {
			child.draw(pose, ctx);
		}
		
		ctx.matrices.pop();
	}
	
	private static Codec<Bone> getCodec() {
		return CODEC;
	}
	
	public static final Codec<Bone> CODEC = RecordCodecBuilder.create(i -> i.group(
		Codec.STRING
			.fieldOf("name")
			.forGetter(bone -> bone.name),
		CodecStuff.VECTOR3F_CODEC
			.fieldOf("translation")
			.forGetter(bone -> bone.translation),
		BoneDrawable.CODEC.listOf()
			.fieldOf("drawables")
			.forGetter(bone -> bone.drawables),
		CodecStuff.lazyCodec(Bone::getCodec)
			.listOf()
			.fieldOf("children")
			.forGetter(bone -> bone.children)
	).apply(i, Bone::new));
	
	public static class Builder {
		public Builder(String name, Vector3f translation) {
			this.name = name;
			this.translation = translation;
		}
		
		public final String name;
		public final Vector3f translation;
		public List<BoneDrawable> drawables = new ArrayList<>();
		public List<Bone.Builder> children = new ArrayList<>();
		
		public Bone.Builder addDrawable(BoneDrawable drawable) {
			drawables.add(drawable);
			return this;
		}
		
		public Bone.Builder addBone(Bone.Builder other) {
			children.add(other);
			return this;
		}
		
		public Bone build() {
			return new Bone(
				name,
				translation,
				drawables,
				children.stream().map(Builder::build).collect(Collectors.toList())
			);
		}
	}
}
