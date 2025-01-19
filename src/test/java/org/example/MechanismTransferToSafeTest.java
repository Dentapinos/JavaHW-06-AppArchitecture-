package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Механизм передачи денег от клиента к сейфу")
class MechanismTransferToSafeTest {
    MechanismTransferToSafe mechanismTransferToSafe;
    Safe safe;

    @BeforeEach
    void setUp() {
        mechanismTransferToSafe = new MechanismTransferToSafe();
        safe = new Safe(mechanismTransferToSafe);
    }

    @DisplayName("должен вернуть пустой список если все купюры приняты")
    @Test
    void shouldReturnEmptyListWhen() {
        List<Integer> exceptedMessage = Collections.emptyList();
        List<Integer> actualMessage = mechanismTransferToSafe.transfer(List.of(50, 100, 200, 500, 1000, 2000, 5000, 10_00), safe);
        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("должен вернуть список с не принимаемыми купюрами")
    @Test
    void mustReturnTheListWithUnacceptedBanknotes() {
        List<Integer> exceptedMessage = List.of( -1, 48, 3000, 0);
        List<Integer> actualMessage = mechanismTransferToSafe.transfer(List.of(50, 100, 200, 500, 1000, 2000, 5000, 10_00, -1, 48, 3000, 0), safe);
        assertEquals(exceptedMessage, actualMessage);
    }

}