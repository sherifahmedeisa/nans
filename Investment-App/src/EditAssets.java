import java.util.List;
import java.util.Scanner;

/**
 * Handles the logic for editing or removing assets in a user's portfolio.
 * Provides an interface for modifying asset values or removing assets entirely.
 */
public class EditAssets {
    private final User user;

    /**
     * Constructs an EditAssets instance for managing a user's portfolio.
     * @param user The currently logged-in user whose assets will be managed
     */
    public EditAssets(User user) {
        this.user = user;
    }

    /**
     * Displays the asset editing interface and handles user interactions.
     * @param scanner Scanner instance for reading user input
     */
    public void showAssetEditor(Scanner scanner) {
        List<Asset> displayedAssets = user.getAssets();

        if (displayedAssets.isEmpty()) {
            System.out.println("No assets to edit or remove.");
            return;
        }

        System.out.println("\n*== Edit/Remove Assets ==*");
        for (int i = 0; i < displayedAssets.size(); i++) {
            Asset asset = displayedAssets.get(i);
            System.out.printf("%d. %s - $%.2f%n", 
                i + 1, asset.getName(), asset.getValue());
        }

        System.out.print("Select an asset by number: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index < 0 || index >= displayedAssets.size()) {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        System.out.println("Choose action:");
        System.out.println("1. Edit Value");
        System.out.println("2. Remove Asset");
        System.out.print("Enter option: ");
        String action = scanner.nextLine();

        System.out.print("Confirm action? (yes/no): ");
        if (!scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Operation cancelled.");
            return;
        }

        handleAssetAction(scanner, index, action);
    }

    /**
     * Executes the selected asset modification action.
     * @param scanner Scanner for additional input
     * @param index Index of selected asset
     * @param action Action to perform (1=edit, 2=remove)
     */
    private void handleAssetAction(Scanner scanner, int index, String action) {
        try {
            switch (action) {
                case "1":
                    handleEditAsset(scanner, index);
                    break;
                case "2":
                    handleRemoveAsset(index);
                    break;
                default:
                    System.out.println("Invalid action selected.");
            }
        } catch (Exception e) {
            System.out.println("Error processing request: " + e.getMessage());
        }
    }

    /**
     * Handles asset value modification.
     * @param scanner Scanner for new value input
     * @param index Index of asset to modify
     */
    private void handleEditAsset(Scanner scanner, int index) {
        System.out.print("Enter new asset value: ");
        try {
            double newValue = Double.parseDouble(scanner.nextLine());
            Asset oldAsset = user.getAssets().get(index);
            user.updateAsset(index, new Asset(oldAsset.getName(), newValue));
            UserStorage.safeUpdateUser(user);
            System.out.println("Asset value updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid value format. Use numbers only.");
        }
    }

    /**
     * Handles asset removal from portfolio.
     * @param index Index of asset to remove
     */
    private void handleRemoveAsset(int index) {
        user.removeAsset(index);
        UserStorage.safeUpdateUser(user);
        System.out.println("Asset removed successfully.");
    }
}