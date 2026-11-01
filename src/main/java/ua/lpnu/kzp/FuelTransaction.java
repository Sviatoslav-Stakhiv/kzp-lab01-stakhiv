package ua.lpnu.kzp;

import java.util.Locale;
import java.util.Objects;

/** Описує коректний транзакційний запис на заправці. */
public final class FuelTransaction {
    private final String fuel;
    private final double liters;
    private final double pricePerLiter;
    private final String date;
    private final int column;

    /**
     * Конструктор з перевіркою інваріантів[cite: 46].
     */
    public FuelTransaction(String fuel, double liters, double pricePerLiter, String date, int column) {
        this.fuel = Objects.requireNonNull(fuel, "Тип пального не може бути null");
        this.date = Objects.requireNonNull(date, "Дата не може бути null");

        if (fuel.isBlank() || date.isBlank()) {
            throw new IllegalArgumentException("Тип пального або дата не можуть бути порожніми");
        }
        if (liters < 0 || pricePerLiter < 0 || column < 0) {
            throw new IllegalArgumentException("Літри, ціна та номер колонки мають бути не від'ємними");
        }
        if (!Double.isFinite(liters) || !Double.isFinite(pricePerLiter)) {
            throw new IllegalArgumentException("Числові значення мають бути скінченними");
        }

        this.liters = liters;
    this.pricePerLiter = pricePerLiter;
    this.column = column;
    }

    /**
     * Статичний фабричний метод для розбору CSV-рядка[cite: 47, 48].
     */
    public static FuelTransaction fromCsv(String line) {
        Objects.requireNonNull(line, "Рядок не може бути null");
        String[] fields = line.split(";", -1);
        
        if (fields.length != 5) {
            throw new IllegalArgumentException("Очікується рівно 5 полів");
        }
        try {
            return new FuelTransaction(
                fields[0].trim(),
                Double.parseDouble(fields[1].trim()),
                Double.parseDouble(fields[2].trim()),
                fields[3].trim(),
                Integer.parseInt(fields[4].trim())
            );
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Числове поле має помилковий формат", exception);
        }
    }

    public double getLiters() { return liters; }
    public double getPricePerLiter() { return pricePerLiter; }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s: %.2f л по %.2f грн, колонка %d (%s)", 
                             fuel, liters, pricePerLiter, column, date);
    }
}