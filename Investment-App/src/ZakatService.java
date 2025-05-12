public class ZakatService {
    private static final double ZAKAT_RATE = 0.025; // 2.5%
    static final double NISAB_GOLD_GRAMS = 85.0; // Islamic gold standard (approx. 87.48 grams)
    static double goldPricePerGram = 5400.0; // (update this value periodically)

    /**
     * Checks if the portfolio value meets the Nisab threshold.
     * @param totalValue total portfolio value
     * @return true if Zakat is applicable
     */
    public static boolean isZakatApplicable(double totalValue) {
        double nisabThreshold = NISAB_GOLD_GRAMS * goldPricePerGram;
        return totalValue >= nisabThreshold;
    }

    /**
     * Calculates Zakat amount (2.5% of total value if above Nisab).
     * @param totalValue total portfolio value
     * @return Zakat amount (0 if below Nisab)
     */
    public static double calculateZakat(double totalValue) {
        return isZakatApplicable(totalValue) ? totalValue * ZAKAT_RATE : 0;
    }

    // For flexibility, allow updating gold price (e.g., via admin input)
    public static void setGoldPricePerGram(double price) {
        goldPricePerGram = price;
    }
}