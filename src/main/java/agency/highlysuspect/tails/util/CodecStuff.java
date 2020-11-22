package agency.highlysuspect.tails.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Lazy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class CodecStuff {
	public static <T> Codec<T[]> arrayOf(Codec<T> codec) {
		//noinspection unchecked
		return codec.listOf().xmap(l -> (T[]) l.toArray(), Arrays::asList);
	}
	
	public static <X> Codec<X> lazyCodec(Supplier<Codec<X>> supplier) {
		Lazy<Codec<X>> lazy = new Lazy<>(supplier);
		
		return new Codec<X>() {
			@Override
			public <T> DataResult<Pair<X, T>> decode(DynamicOps<T> ops, T input) {
				return lazy.get().decode(ops, input);
			}
			
			@Override
			public <T> DataResult<T> encode(X input, DynamicOps<T> ops, T prefix) {
				return lazy.get().encode(input, ops, prefix);
			}
		};
	}
	
	//client only probably... TODO move this out
	public static final Codec<Vector3f> VECTOR3F_CODEC = Codec.FLOAT.listOf().comapFlatMap(list -> {
		if(list.size() != 3) return DataResult.error("Input is not a list of 3 floats");
		else return DataResult.success(new Vector3f(list.get(0), list.get(1), list.get(2)));
	}, vec -> {
		ArrayList<Float> result = new ArrayList<>();
		result.add(vec.getX());
		result.add(vec.getY());
		result.add(vec.getZ());
		return result;
	});
}
