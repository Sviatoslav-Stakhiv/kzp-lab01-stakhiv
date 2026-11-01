package ua.lpnu.kzp;

/** Незмінний набір підсумкових показників[cite: 47]. */
public record FuelSummary(int validCount, double totalLiters, double totalCost, double averagePrice) {
    public FuelSummary {
        if (validCount < 0 || totalLiters < 0 || totalCost < 0 || averagePrice < 0) {
            throw new IllegalArgumentException("Показники не можуть бути від'ємними");
        }
    }
}