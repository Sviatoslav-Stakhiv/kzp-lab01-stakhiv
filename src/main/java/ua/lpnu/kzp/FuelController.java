package ua.lpnu.kzp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FuelController {

    public void run(AppArguments args) {
        Path input = Path.of(args.inputPath());
        Path output = Path.of(args.outputPath());

        List<String> lines;
        try {
            lines = Files.readAllLines(input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.printf(Locale.ROOT, "Помилка читання файлу: %s%n", e.getMessage());
            return;
        }

        List<FuelTransaction> transactions = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        double sumPricePerLiter = 0.0;

        for (int index = 0; index < lines.size(); index++) {
            try {
                // Перевірка і створення об'єкта відбувається ТУТ[cite: 48, 49]
                FuelTransaction transaction = FuelTransaction.fromCsv(lines.get(index));
                transactions.add(transaction);
                sumPricePerLiter += transaction.getPricePerLiter();
            } catch (IllegalArgumentException exception) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: %s", index + 1, exception.getMessage()));
            }
        }

        double totalLiters = transactions.stream().mapToDouble(FuelTransaction::getLiters).sum();
        double totalCost = transactions.stream().mapToDouble(t -> t.getLiters() * t.getPricePerLiter()).sum();
        double avgPrice = transactions.isEmpty() ? 0.0 : sumPricePerLiter / transactions.size();

        FuelSummary summary = new FuelSummary(transactions.size(), totalLiters, totalCost, avgPrice);
        writeReport(summary, errors, output);
    }

    private void writeReport(FuelSummary summary, List<String> errors, Path output) {
        StringBuilder report = new StringBuilder();
        report.append(String.format(Locale.ROOT, "Коректних записів: %d%n", summary.validCount()));
        report.append(String.format(Locale.ROOT, "Загальна кількість літрів: %.2f%n", summary.totalLiters()));
        report.append(String.format(Locale.ROOT, "Загальна вартість: %.2f%n", summary.totalCost()));
        report.append(String.format(Locale.ROOT, "Середня ціна літра: %.2f%n", summary.averagePrice()));
        report.append(String.format(Locale.ROOT, "Помилок: %d%n", errors.size()));

        System.out.print(report);
        errors.forEach(System.out::println);

        try {
            Path parentDir = output.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            Files.writeString(output, report.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.printf(Locale.ROOT, "Помилка запису файлу: %s%n", e.getMessage());
        }
    }
}