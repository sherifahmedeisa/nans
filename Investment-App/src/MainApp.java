import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n*== Welcome to Our InvestWise App ==*");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    signUp(scanner);
                    break;
                case "2":
                    login(scanner);
                    break;
                case "3":
                    System.out.println("Thank you for using VestEdge!");
                    return;
                default:
                    System.out.println("Invalid option! Try again");
            }
        }
    }

    private static void signUp(Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            if (UserStorage.userExists(username)) {
                System.out.println("Username already exists.");
                return;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (!UserStorage.isValidPassword(password)) {
                System.out.println("Password must contain at least one uppercase letter, one number or symbol, and be 6+ characters!");
                return;
            }

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            User newUser = new User(username, password, email);
            UserStorage.saveUser(newUser);
            System.out.println("User registered successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            if (SessionManager.isLocked(username)) {
                System.out.println("Your account is temporarily locked due to multiple failed attempts!");
                return;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (UserStorage.isValidLogin(username, password)) {
                SessionManager.resetAttempts(username);
                User user = UserStorage.findUser(username);
                SessionManager.setCurrentUser(user);
                System.out.println("Login successful. Welcome " + username + "!");
                showDashboard(scanner);
            } else {
                SessionManager.recordFailure(username);
                System.out.println("Invalid credentials!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addAsset(Scanner scanner, User user) {
        try {
            System.out.print("Enter asset name: ");
            String assetName = scanner.nextLine();
            System.out.print("Enter asset value: ");
            double assetValue = Double.parseDouble(scanner.nextLine());
            
            // Add asset to user's portfolio
            user.addAsset(new Asset(assetName, assetValue));
            
            // Persist changes to users_list.ser
            UserStorage.updateUser(user);
            System.out.println("Asset added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid value! Must be a number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void calculateZakat(User user) {
        double totalValue = PortfolioService.getTotalPortfolioValue(user);
        double zakat = ZakatService.calculateZakat(totalValue);
        double nisabThreshold = ZakatService.NISAB_GOLD_GRAMS * ZakatService.goldPricePerGram ;
    
        System.out.println("\n*== Zakat Calculation ==*");
        System.out.printf("Total Portfolio Value: $%,.2f%n", totalValue);
        System.out.printf("Nisab Threshold: $%,.2f%n", nisabThreshold);
    
        if (ZakatService.isZakatApplicable(totalValue)) {
            System.out.printf("Zakat Due (2.5%%): $%,.2f%n", zakat);
        } else {
            System.out.println("Zakat is not applicable (portfolio below Nisab).");
        }
    }

    private static void showDashboard(Scanner scanner) {
        User user = SessionManager.getCurrentUser();
        if (user == null) {
            System.out.println("No active session!");
            return;
        }

        while (true) {
            System.out.println("\n*== Dashboard ==*");
            System.out.println("Logged in as: " + user.getUsername());
            System.out.println("1. View Profile");
            System.out.println("2. Add Assets");  
            System.out.println("3. View Portfolio"); 
            System.out.println("4. Edit Assets");
            System.out.println("5. Calculate Zakat"); // Placeholder for future feature
            System.out.println("6. Add Bank");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n*== User Profile ==*");
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Email: " + user.getEmail());
                    break;
                case "2":
                    try {
                        System.out.print("Enter asset name: ");
                        String assetName = scanner.nextLine();
                        System.out.print("Enter asset value: ");
                        double assetValue = Double.parseDouble(scanner.nextLine());
                        user.addAsset(new Asset(assetName, assetValue));
                        System.out.println("Asset added successfully.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid value! Must be a number.");
                    }
                    break;
                case "3":
                    System.out.println("\n*== Portfolio ==*");
                    for (Asset asset : user.getAssets()) {
                        System.out.println("Asset: " + asset.getName() + ", Value: " + asset.getValue());
                    }
                    break;


                case "4":
                        new EditAssets(user).showAssetEditor(scanner);
                        break;
                case "5":
                    calculateZakat(user);
                    break;

                case "6":
                    BankIntegration.connectBankAccount(scanner);
                case "7":
                    SessionManager.logout();
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}