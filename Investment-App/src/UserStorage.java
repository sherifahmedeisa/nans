import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages persistent storage of user data using Java serialization.
 * Handles user creation, updates, and authentication.
 */
public class UserStorage {
    private static final String FILENAME = "users_list.ser";

    /**
     * Saves a new user to persistent storage.
     * @param newUser User object to be saved
     * @throws IOException If file write operation fails
     * @throws ClassNotFoundException If serialization class mismatch occurs
     */
    public static void saveUser(User newUser) throws IOException, ClassNotFoundException {
        List<User> users = getAllUsers();
        users.add(newUser);
        saveAllUsers(users);
    }

    /**
     * Updates existing user data in storage.
     * @param updatedUser Modified user object with new data
     * @throws IOException If file write operation fails
     * @throws ClassNotFoundException If serialization class mismatch occurs
     */
    public static void updateUser(User updatedUser) throws IOException, ClassNotFoundException {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                saveAllUsers(users);
                return;
            }
        }
        throw new IOException("User not found in storage: " + updatedUser.getUsername());
    }

    /**
     * Safely updates user data with error handling.
     * @param user User object to update
     */
    public static void safeUpdateUser(User user) {
        try {
            updateUser(user);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] Failed to update user data: " + e.getMessage());
            System.err.println("Changes may not be saved permanently!");
        }
    }

    /**
     * Retrieves all users from persistent storage.
     * @return List of all registered users
     * @throws IOException If file read operation fails
     * @throws ClassNotFoundException If serialization class mismatch occurs
     */
    public static List<User> getAllUsers() throws IOException, ClassNotFoundException {
        File file = new File(FILENAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (List<User>) ois.readObject();
        }
    }

    /**
     * Validates user credentials against stored data.
     * @param username Username to verify
     * @param password Password to verify
     * @return true if credentials match stored data
     * @throws IOException If file read operation fails
     * @throws ClassNotFoundException If serialization class mismatch occurs
     */
    public static boolean isValidLogin(String username, String password) 
        throws IOException, ClassNotFoundException {
        return getAllUsers().stream()
            .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }

    /**
     * Checks if username is already registered.
     * @param username Username to check
     * @return true if username exists in system
     * @throws IOException If file read operation fails
     * @throws ClassNotFoundException If serialization class mismatch occurs
     */
    public static boolean userExists(String username) throws IOException, ClassNotFoundException {
        return getAllUsers().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    /**
     * Validates password meets security requirements.
     * @param password Password to validate
     * @return true if password meets complexity rules
     */
    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*0-9]).{6,}$");
    }

    /**
     * Finds user by username.
     * @param username Username to search for
     * @return User object or null if not found
     * @throws IOException If file read operation fails
     * @throws ClassNotFoundException If serialization class mismatch occurs
     */
    public static User findUser(String username) throws IOException, ClassNotFoundException {
        return getAllUsers().stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }

    /**
     * Internal method for saving user list to file.
     * @param users List of users to save
     * @throws IOException If file write operation fails
     */
    private static void saveAllUsers(List<User> users) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(users);
        }
    }
}