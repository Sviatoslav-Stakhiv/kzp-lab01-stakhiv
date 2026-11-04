package ua.lpnu.kzp;

/** Категорія типу пального. */
public enum FuelKind {
    PETROL("Бензин"),
    DIESEL("Дизель");

    private final String label;

    FuelKind(String label) {
        this.label = label;
    }

    /** Повертає текстове подання категорії. */
    public String label() {
        return label;
    }
}