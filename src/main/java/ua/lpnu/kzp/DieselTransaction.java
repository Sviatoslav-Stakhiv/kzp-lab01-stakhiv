package ua.lpnu.kzp;

/** Транзакція для дизелю. */
public final class DieselTransaction extends FuelTransaction {
    private final double pricePerLiter;
    private final boolean isWholesale; // Гурт

    public DieselTransaction(String date, int column, double liters, double pricePerLiter, boolean isWholesale) {
        super(date, column, FuelKind.DIESEL, liters);
        if (pricePerLiter < 0 || !Double.isFinite(pricePerLiter)) {
            throw new IllegalArgumentException("Ціна за літр має бути не від'ємною");
        }
        this.pricePerLiter = pricePerLiter;
        this.isWholesale = isWholesale;
    }

    @Override
    public double calculateCost() {
        double cost = getLiters() * pricePerLiter;
        // Знижка 5% для гуртових покупців, якщо більше 100 літрів
        if (isWholesale && getLiters() > 100) {
            return cost * 0.95;
        }
        return cost;
    }
}