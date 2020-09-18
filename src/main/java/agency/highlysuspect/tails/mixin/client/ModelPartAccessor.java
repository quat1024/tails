package agency.highlysuspect.tails.mixin.client;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPart.class)
public interface ModelPartAccessor {
	@Accessor("cuboids") ObjectList<ModelPart.Cuboid> tails$getCuboids();
}
