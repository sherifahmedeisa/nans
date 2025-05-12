

import java.util.List;
import java.util.Scanner;

/**
 * Provides the functionality for linking a user's bank account.
 * Includes bank name validation, card details checking, and OTP simulation.
 */
public class BankIntegration {

    /**
     * Interacts with the user via console input to collect and validate bank account data.
     * Simulates OTP sending and checks validity of user input.
     *
     * @param scanner the Scanner object used to read user input from the console
     */
    public static void connectBankAccount(Scanner scanner) {
        System.out.println("\n*== Connect Bank Account ==*");

        System.out.print("Enter bank name: ");
        String bankName = scanner.nextLine();

        System.out.print("Enter card number (16 digits): ");
        String cardNumber = scanner.nextLine();

        System.out.print("Enter card expiry (MM/YY): ");
        String expiry = scanner.nextLine();

        String generatedOtp = "123456"; // Simulated OTP
        System.out.println("OTP sent to your registered mobile number: " + generatedOtp);

        System.out.print("Enter OTP: ");
        String enteredOtp = scanner.nextLine();

        if (!List.of("Bank A", "Bank B").contains(bankName)) {
            System.out.println("Bank not supported.");
        } else if (!cardNumber.matches("\\d{16}") || !expiry.matches("\\d{2}/\\d{2}")) {
            System.out.println("Invalid card details.");
        } else if (!enteredOtp.equals(generatedOtp)) {
            System.out.println("Incorrect OTP. Bank linking failed.");
        } else {
            System.out.println("Bank account successfully linked!");
        }
    }
}
