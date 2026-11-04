package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FuelTransactionTest {

    @Test
    void subtypesCalculateCostDifferently() {
        FuelTransaction petrol = new PetrolTransaction("2026-10-15", 1, 150, 50.0, true); // Знижка 2%
        FuelTransaction diesel = new DieselTransaction("2026-10-15", 1, 150, 50.0, true); // Знижка 5%
        
        assertNotEquals(petrol.calculateCost(), diesel.calculateCost());
        assertEquals(7350.0, petrol.calculateCost(), 0.001); // 150 * 50 * 0.98
        assertEquals(7125.0, diesel.calculateCost(), 0.001); // 150 * 50 * 0.95
    }

    @Test
    void equalObjectsHaveOneSetEntry() {
        Set<FuelTransaction> items = new HashSet<>();
        items.add(new PetrolTransaction("2026-10-15", 1, 50, 50.0, false));
        items.add(new PetrolTransaction("2026-10-15", 1, 50, 50.0, false));
        
        assertEquals(1, items.size());
    }

    @Test
    void subtypeRejectsInvalidSpecificValue() {
        assertThrows(IllegalArgumentException.class, 
            () -> new DieselTransaction("2026-10-15", 1, 50, -50.0, false));
    }
    
    @Test
    void factoryRejectsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, 
            () -> TransactionFactory.fromCsv("Газ;50;50;2026-10-15;1;false")); // Невідомий тип
    }
}