package agency.highlysuspect.tails.client.outfit;

public class ConfiguredPart<T extends PartConfig> {
	public ConfiguredPart(PartType<T> type, T config) {
		this.type = type;
		this.config = config;
	}
	
	public final PartType<T> type;
	public final T config;
	
	@Override
	public int hashCode() {
		return (type.hashCode() * 31) ^ config.hashCode();
	}
}
