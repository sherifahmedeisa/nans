import java.io.Serializable;

/**
 * Represents an investment asset with a name and monetary value.
 * This class is serializable for persistent storage.
 */
public class Asset implements Serializable {
    /**
     * Serialization version UID for compatibility between serialized instances
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Name of the asset (e.g., "Gold", "AAPL Stock")
     */
    private final String name;
    
    /**
     * Current value of the asset in USD
     */
    private final double value;

    /**
     * Constructs a new Asset instance
     * @param name The name/identifier of the asset
     * @param value The monetary value of the asset
     */
    public Asset(String name, double value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return The name of the asset
     */
    public String getName() { 
        return name; 
    }

    /**
     * @return The current value of the asset
     */
    public double getValue() { 
        return value; 
    }

    /**
     * Provides a string representation of the asset
     * @return Format: "Asset: [name], Value: [value]"
     */
    @Override
    public String toString() {
        return String.format("Asset: %s, Value: $%,.2f", name, value);
    }
}