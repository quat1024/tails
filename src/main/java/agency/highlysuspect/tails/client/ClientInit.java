package agency.highlysuspect.tails.client;

import agency.highlysuspect.tails.Init;
import agency.highlysuspect.tails.client.feature.renderer.PartRenderer;
import agency.highlysuspect.tails.client.feature.TailsFeatureRenderer;
import agency.highlysuspect.tails.client.feature.renderer.TestRenderer;
import agency.highlysuspect.tails.client.outfit.PartConfig;
import agency.highlysuspect.tails.client.outfit.PartType;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.function.Function;

public class ClientInit implements ClientModInitializer {
	//TODO: If Mojang bungled Registries hard enough to make this a bad idea, double back on it.
	public static final RegistryKey<Registry<PartType<?>>> PART_TYPE_REGISTRY_KEY = RegistryKey.ofRegistry(id("part_type"));
	public static final Registry<PartType<?>> PART_TYPE_REGISTRY = new SimpleRegistry<>(PART_TYPE_REGISTRY_KEY, Lifecycle.experimental());
	
	public static final RegistryKey<Registry<PartRenderer.Factory<?>>> PART_RENDERER_REGISTRY_KEY = RegistryKey.ofRegistry(id("part_renderer"));
	public static final Registry<PartRenderer.Factory<?>> PART_RENDERER_REGISTRY = new SimpleRegistry<>(PART_RENDERER_REGISTRY_KEY, Lifecycle.experimental());
	
	public static PartType<PartConfig.AltSwitch> TEST;
	
	@Override
	public void onInitializeClient() {
		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((type, renderer, reg) -> {
			if(renderer instanceof PlayerEntityRenderer) {
				reg.register(new TailsFeatureRenderer((PlayerEntityRenderer) renderer));
			}
		});
		
		//TODO: reconsider the amount of "flattening" done here
		
		//Ears
		Registry.register(PART_TYPE_REGISTRY, id("fox_ears"), new PartType<>(PartConfig.AltSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("cat_ears"), new PartType<>(PartConfig.SmallSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("panda_ears"), new PartType<>(PartConfig.Blank::new));
		
		//Wings
		Registry.register(PART_TYPE_REGISTRY, id("wings"), new PartType<>(PartConfig.SmallSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("metal_wings"), new PartType<>(PartConfig.SmallSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("dragon_wings"), new PartType<>(PartConfig.SmallSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("dragon_boneless_wings"), new PartType<>(PartConfig.SmallSwitch::new));
		
		//Tails
		Registry.register(PART_TYPE_REGISTRY, id("fox_tail"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("twin_tails"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("ninetails"), new PartType<>(PartConfig.Blank::new));
		TEST = Registry.register(PART_TYPE_REGISTRY, id("dragon_tail"), new PartType<>(PartConfig.AltSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("dragon_tail_striped"), new PartType<>(PartConfig.AltSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("devil_tail"), new PartType<>(PartConfig.TippedSwitch::new));
		Registry.register(PART_TYPE_REGISTRY, id("raccoon_tail"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("cat_tail"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("tiger_tail"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("bird_tail"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("shark_tail"), new PartType<>(PartConfig.Blank::new));
		Registry.register(PART_TYPE_REGISTRY, id("bunny_tail"), new PartType<>(PartConfig.Blank::new));
		
		//Muzzle
		Registry.register(PART_TYPE_REGISTRY, id("muzzle"), new PartType<>(PartConfig.Muzzle::new));
		
		////Renderers
		
		Registry.register(PART_RENDERER_REGISTRY, id("dragon_tail"), (PartRenderer.Factory<PartConfig.AltSwitch>) TestRenderer::new);
	}
	
	private static Identifier id(String path) {
		return new Identifier(Init.MODID, path);
	}
}
