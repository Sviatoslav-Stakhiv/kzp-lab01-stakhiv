package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FuelTransactionTest {

    @Test
    void fromCsvCreatesValidEntity() { // AAA Pattern[cite: 49]
        FuelTransaction ft = FuelTransaction.fromCsv("Дизель; 40.5; 52.00; 2026-10-15; 1");
        
        assertEquals(40.5, ft.getLiters(), 0.0001);
        assertEquals(52.00, ft.getPricePerLiter(), 0.0001);
    }

    @Test
    void constructorRejectsNegativeLiters() {
        assertThrows(IllegalArgumentException.class, 
            () -> new FuelTransaction("Газ", -10.0, 28.00, "2026-10-15", 1));
    }

    @Test
    void fromCsvRejectsInvalidNumber() {
        assertThrows(IllegalArgumentException.class, 
            () -> FuelTransaction.fromCsv("А-95; помилка; 55.50; 2026-10-16; 2"));
    }

    @Test
    void fromCsvRejectsWrongNumberOfFields() {
        assertThrows(IllegalArgumentException.class, 
            () -> FuelTransaction.fromCsv("Газ; 30.0; 28.00; 2026-10-16")); // Лише 4 поля
    }
    
    @Test
    void recordUsesValueEquality() {
        FuelSummary first = new FuelSummary(3, 95.5, 4333.5, 45.17);
        FuelSummary second = new FuelSummary(3, 95.5, 4333.5, 45.17);
        assertEquals(first, second);
    }
}