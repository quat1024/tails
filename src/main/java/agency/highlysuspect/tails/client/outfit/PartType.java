package agency.highlysuspect.tails.client.outfit;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PartType<T extends PartConfig> {
	//Flyweight object; one of these is created for each type of part in the game
	//(it's the "Item" in the item vs. itemstack analogy)
	
	public PartType(Supplier<T> configFactory) {
		this.configFactory = configFactory;
	}
	
	private final Supplier<T> configFactory;
	
	public ConfiguredPart<T> create() {
		return new ConfiguredPart<>(this, configFactory.get());
	}
	
	public ConfiguredPart<T> create(Consumer<T> configurator) {
		T config = configFactory.get();
		configurator.accept(config);
		return new ConfiguredPart<>(this, config);
	}
}
