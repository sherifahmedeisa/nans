/**
 * Manages portfolio operations directly via User objects in users_list.ser
 */
public class PortfolioService {
    
    /**
     * Calculates total portfolio value from the logged-in user's assets.
     * @param user the logged-in User object
     * @return total portfolio value
     */
    public static double getTotalPortfolioValue(User user) {
        return user.getAssets().stream()
                .mapToDouble(Asset::getValue)
                .sum();
    }
}  