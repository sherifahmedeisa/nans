import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user of the Investment application.
 * Stores username, password, email, and investment assets.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    
    private String username;
    private String password;
    private String email;
    private List<Asset> assets = new ArrayList<>(); // Portfolio assets

    /**
     * Constructs a new User object.
     * @param username the unique username
     * @param password the user's password
     * @param email the user's email address
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // ------------------- Asset Management -------------------
    /**
     * Adds an asset to the user's portfolio.
     * @param asset the investment asset to add
     */
    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    /**
     * Retrieves all assets in the user's portfolio.
     * @return list of assets (immutable)
     */
    public List<Asset> getAssets() {
        return new ArrayList<>(assets); // Defensive copy
    }

    // ------------------- Getters -------------------
    /**
     * @return the user's username
     */
    public String getUsername() { return username; }

    /**
     * @return the user's password
     */
    public String getPassword() { return password; }

    /**
     * @return the user's email address
     */
    public String getEmail() { return email; }

        /**
     * Removes an asset from the user's portfolio by index.
     * @param index the position of the asset to remove
     */
    public void removeAsset(int index) {
        if (index >= 0 && index < assets.size()) {
            assets.remove(index);
        }
    }

    /**
     * Updates an asset's value by replacing it with a new instance.
     * @param index the position of the asset to update
     * @param newAsset the new asset to replace the old one
     */
    public void updateAsset(int index, Asset newAsset) {
        if (index >= 0 && index < assets.size()) {
            assets.set(index, newAsset);
        }
    }

    // ------------------- Utility -------------------
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", assets=" + assets +
                '}';
    }
}