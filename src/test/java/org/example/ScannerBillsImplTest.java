package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест Сканера фальшивых денег")
class ScannerBillsImplTest {

    ISecurity scannerBills = new CheckTheBills();

    @DisplayName("Проверяем принимает ли банкомат купюры следующими номиналом")
    @ParameterizedTest
    @CsvSource({
            "50", "100", "200", "500", "1000", "2000", "5000", "10_000"
    })
    void checkTheBillsSuccessful(int count) {
        assertTrue(scannerBills.isDenominationCompliant(count));
    }

    @DisplayName("Проверяем НЕ принимает ли банкомат купюры следующими номиналом")
    @ParameterizedTest
    @CsvSource({
            "0", "-1", "3", "40", "300", "600", "5001", "10_005"
    })
    void checkTheBillsExcepted(int count) {
        assertFalse(scannerBills.isDenominationCompliant(count));
    }
}