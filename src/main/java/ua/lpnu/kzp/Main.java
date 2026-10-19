package ua.lpnu.kzp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Головний клас програми для обробки даних заправки (Варіант 24).
 */
public final class Main {
    
    /* Забороняє створення екземплярів службового класу. */
    private Main() {
    }

    /**
     * Точка входу до програми.
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        // Шляхи будують через Path для кросплатформності[cite: 10, 11]
        Path input = Path.of("data", "input.csv");
        Path output = Path.of("out", "report.txt");

        List<String> lines;
        try {
            // Явне кодування UTF-8[cite: 11]
            lines = Files.readAllLines(input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.printf(Locale.ROOT, "Помилка читання файлу: %s%n", e.getMessage());
            return;
        }

        List<String> errors = new ArrayList<>();
        int validCount = 0;
        double totalLiters = 0.0;
        double totalCost = 0.0;
        double sumPricePerLiter = 0.0;

        for (int index = 0; index < lines.size(); index++) {
            // Розділяємо рядок на поля, зберігаючи можливе порожнє останнє поле[cite: 9]
            String[] fields = lines.get(index).split(";", -1);

            if (fields.length != 5) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: очікується 5 полів", index + 1));
                continue;
            }

            if (fields[0].isBlank() || fields[3].isBlank()) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: порожнє текстове поле (тип або дата)", index + 1));
                continue;
            }

            try {
                // Перетворюємо текстові поля в числа[cite: 9]
                double liters = Double.parseDouble(fields[1]);
                double pricePerLiter = Double.parseDouble(fields[2]);
                int column = Integer.parseInt(fields[4]);

                if (liters < 0 || pricePerLiter < 0 || column < 0) {
                    errors.add(String.format(Locale.ROOT, "Рядок %d: від'ємне числове значення", index + 1));
                    continue;
                }

                // До статистики потрапляють лише коректні записи[cite: 21]
                validCount++;
                totalLiters += liters;
                totalCost += (liters * pricePerLiter);
                sumPricePerLiter += pricePerLiter;

            } catch (NumberFormatException e) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: числове поле має помилковий формат", index + 1));
            }
        }

        double averagePrice = validCount == 0 ? 0.0 : sumPricePerLiter / validCount;

        // Формування звіту[cite: 10]
        StringBuilder report = new StringBuilder();
        report.append(String.format(Locale.ROOT, "Коректних записів: %d%n", validCount));
        report.append(String.format(Locale.ROOT, "Загальна кількість літрів: %.2f%n", totalLiters));
        report.append(String.format(Locale.ROOT, "Загальна вартість: %.2f%n", totalCost));
        report.append(String.format(Locale.ROOT, "Середня ціна літра: %.2f%n", averagePrice));
        report.append(String.format(Locale.ROOT, "Помилок: %d%n", errors.size()));
        
        // Вивід у консоль
        System.out.print(report);
        errors.forEach(System.out::println);

        // Запис у файл out/report.txt[cite: 11]
        try {
            if (output.getParent() != null) {
                Files.createDirectories(output.getParent());
            }
            Files.writeString(output, report.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.printf(Locale.ROOT, "Помилка запису файлу: %s%n", e.getMessage());
        }
    }
}