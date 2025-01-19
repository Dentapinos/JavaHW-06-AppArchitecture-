package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест Сейфа")
class SafeTest {
    MechanismTransferToClient transferToClient;
    Safe safe;

    @BeforeEach
    public void setUp() {
        transferToClient = Mockito.mock(MechanismTransferToClient.class);
        safe = new Safe(transferToClient);
    }

    @DisplayName("считает сумму в пустом сейфе")
    @Test
    void countsAmountOfMoneyInAnEmptySafe() {
        int exceptedMessage = 0;
        int actualMessage = safe.getTotalAmountOfMoney();
        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("добавляет деньги в сейф")
    @Test
    void addsMoneyToSafe() {
        var banknotes = List.of(100, 500, 100, 500, 500);
        safe.acceptMoney(banknotes);

        int exceptedMessageBanknotes100 = 2;
        int exceptedMessageBanknotes500 = 3;

        var banknotesInSafe = safe.getCashStorageCell();

        int actualMessage100 = banknotesInSafe.get(100);
        int actualMessage500 = banknotesInSafe.get(500);

        assertEquals(exceptedMessageBanknotes100, actualMessage100);
        assertEquals(exceptedMessageBanknotes500, actualMessage500);
    }

    @DisplayName("считает сумму денег в сейфе с деньгами")
    @Test
    void countsAmountOfMoneyInSafeWithMoney() {
        addsMoneyToSafe();
        int exceptedMessage = 1700;
        int actualMessage = safe.getTotalAmountOfMoney();

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("возвращает код 300 в строке когда сумма запроса больше суммы на счету")
    @Test
    void mustReturnCode300IfAmountExceedsAmountATM() {
        addsMoneyToSafe();
        String exceptedMessage = "300";
        String actualMessage = safe.giveOutMoney(2000);

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("возвращает рекомендуемую сумму в формате $ и сумма")
    @Test
    void shouldReturnAmountInFormat() {
        addsMoneyToSafe();

        String exceptedMessage = "$100";
        String actualMessage = safe.giveOutMoney(250);

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("возвращает код 310 в строке когда клиент не забрал деньги из ячейки")
    @Test
    void mustReturnCode310WhenClientDidNotTakeMoney() {
        addsMoneyToSafe();
        BDDMockito.given(transferToClient.transfer(Mockito.anyList(), Mockito.any())).willReturn(List.of(100));

        String result = safe.giveOutMoney(200);

        assertEquals("310", result);
        BDDMockito.then(transferToClient).should(Mockito.times(1)).transfer(Mockito.anyList(), Mockito.any());
    }

    @DisplayName("возвращает код 200 когда операция выполнилась успешно")
    @Test
    void shouldReturnCode200WhenOperationCompletedSuccessfully() {
        addsMoneyToSafe();

        BDDMockito.given(transferToClient.transfer(Mockito.anyList(), Mockito.any())).willReturn(Collections.emptyList());

        String result = safe.giveOutMoney(200);

        assertEquals("200", result);
        BDDMockito.then(transferToClient).should(Mockito.times(1)).transfer(Mockito.anyList(), Mockito.any());
    }
}