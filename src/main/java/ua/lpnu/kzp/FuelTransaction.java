package ua.lpnu.kzp;

import java.util.Locale;
import java.util.Objects;

/** Спільний абстрактний тип для транзакцій заправки. */
public abstract class FuelTransaction {
    private final String date;
    private final int column;
    private final FuelKind kind;
    private final double liters;

    /** Створює транзакцію з перевіреними базовими полями. */
    protected FuelTransaction(String date, int column, FuelKind kind, double liters) {
        // Викликаємо статичні методи для перевірки до того, як присвоїти поля
        this.date = validateDate(date);
        this.kind = Objects.requireNonNull(kind, "Категорія пального не може бути null");
        this.column = validateColumn(column);
        this.liters = validateLiters(liters);
    }

    private static String validateDate(String date) {
        Objects.requireNonNull(date, "Дата не може бути null");
        if (date.isBlank()) {
            throw new IllegalArgumentException("Дата не може бути порожньою");
        }
        return date;
    }

    private static int validateColumn(int column) {
        if (column < 0) {
            throw new IllegalArgumentException("Номер колонки має бути не від'ємним");
        }
        return column;
    }

    private static double validateLiters(double liters) {
        if (liters < 0 || !Double.isFinite(liters)) {
            throw new IllegalArgumentException("Літри мають бути не від'ємними та скінченними");
        }
        return liters;
    }

    public final String getDate() { return date; }
    public final int getColumn() { return column; }
    public final FuelKind getKind() { return kind; }
    public final double getLiters() { return liters; }

    /** Поліморфний метод: обчислює вартість пального. */
    public abstract double calculateCost();

    @Override
    public final boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        FuelTransaction that = (FuelTransaction) other;
        return column == that.column &&
               Double.compare(that.liters, liters) == 0 &&
               date.equals(that.date);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), date, column, liters);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s (Колонка %d): %.2f л - %.2f грн", 
                             kind.label(), column, liters, calculateCost());

    }
    /**
     * Заглушка фіналізатора для уникнення попереджень SpotBugs (CT_CONSTRUCTOR_THROW).
     */
    @Override
    @SuppressWarnings("removal")
    protected final void finalize() throws Throwable {
        if (this.date == null) {
            super.finalize();
        }
    }
}
