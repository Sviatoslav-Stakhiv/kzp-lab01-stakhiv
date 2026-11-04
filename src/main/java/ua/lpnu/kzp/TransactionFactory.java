package ua.lpnu.kzp;

import java.util.Objects;

public final class TransactionFactory {
    private TransactionFactory() {}

    public static FuelTransaction fromCsv(String line) {
        Objects.requireNonNull(line, "Рядок не може бути null");
        String[] fields = line.split(";", -1);
        
        if (fields.length != 6) {
            throw new IllegalArgumentException("Очікується рівно 6 полів: Тип;Літри;Ціна;Дата;Колонка;Бонус(true/false)");
        }
        try {
            String fuelType = fields[0].trim();
            double liters = Double.parseDouble(fields[1].trim());
            double price = Double.parseDouble(fields[2].trim());
            String date = fields[3].trim();
            int column = Integer.parseInt(fields[4].trim());
            boolean bonusFlag = Boolean.parseBoolean(fields[5].trim());

            if (fuelType.equalsIgnoreCase("А-95") || fuelType.equalsIgnoreCase("Бензин")) {
                return new PetrolTransaction(date, column, liters, price, bonusFlag);
            } else if (fuelType.equalsIgnoreCase("Дизель")) {
                return new DieselTransaction(date, column, liters, price, bonusFlag);
            } else {
                throw new IllegalArgumentException("Невідомий тип пального: " + fuelType);
            }
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Числове або логічне поле має помилковий формат", exception);
        }
    }
}