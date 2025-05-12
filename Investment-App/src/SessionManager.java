import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages login attempts, tracks the current logged-in user, and persists user data on logout.
 */
public class SessionManager {
    private static Map<String, Integer> failedAttempts = new HashMap<>();
    private static final int MAX_ATTEMPTS = 3;
    private static User currentUser = null;

    /**
     * Checks if the account is locked due to failed attempts.
     * @param username the username to check
     * @return true if locked
     */
    public static boolean isLocked(String username) {
        return failedAttempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }

    /**
     * Records a failed login attempt for the given username.
     * @param username the username to track
     */
    public static void recordFailure(String username) {
        failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
    }

    /**
     * Resets failed login attempts for a user.
     * @param username the username to reset
     */
    public static void resetAttempts(String username) {
        failedAttempts.remove(username);
    }

    /**
     * Sets the currently logged-in user and updates the session.
     * @param user the active user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * @return the current logged-in user, or null if no session is active
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs out the current user and persists their data (e.g., assets).
     * Automatically saves changes to the user's profile via UserStorage.
     */
    public static void logout() {
        if (currentUser != null) {
            try {
                UserStorage.updateUser(currentUser); // Persist user data (including assets)
                System.out.println("User data saved successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error saving user data: " + e.getMessage());
            }
            currentUser = null;
        }
    }
}