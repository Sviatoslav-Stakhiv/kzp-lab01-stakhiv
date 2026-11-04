package ua.lpnu.kzp;

/** Транзакція для бензину. */
public final class PetrolTransaction extends FuelTransaction {
    private final double pricePerLiter;
    private final boolean hasLoyaltyCard;

    public PetrolTransaction(String date, int column, double liters, double pricePerLiter, boolean hasLoyaltyCard) {
        super(date, column, FuelKind.PETROL, liters);
        if (pricePerLiter < 0 || !Double.isFinite(pricePerLiter)) {
            throw new IllegalArgumentException("Ціна за літр має бути не від'ємною");
        }
        this.pricePerLiter = pricePerLiter;
        this.hasLoyaltyCard = hasLoyaltyCard;
    }

    @Override
    public double calculateCost() {
        double cost = getLiters() * pricePerLiter;
        // Знижка 2% при наявності картки лояльності
        return hasLoyaltyCard ? cost * 0.98 : cost;
    }
}