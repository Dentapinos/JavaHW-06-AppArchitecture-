package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест Банкомата")
class ATMBaseImplTest {

    private static ISafe safe;
    private static IMechanismsTransfer transferToSafe;
    ATMBaseImpl atmBase;

    @BeforeEach
    public void setUp() {
        safe = Mockito.mock(ISafe.class);
        transferToSafe = Mockito.mock(IMechanismsTransfer.class);
        atmBase = new ATMBaseImpl(safe, transferToSafe);
    }

    @DisplayName("блок тестов для отображения баланса")
    @Nested
    class ShowBalanceTest {
        @DisplayName("должен вернуть сообщение что нет сигнала")
        @Test
        void shouldReturnMessageNoSignal() {
            atmBase = new ATMBaseImpl(null, transferToSafe);

            String exceptedMessage = "Нет сигнала";
            String actualMessage = atmBase.showBalance();

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("возвращает сообщение что нет денег, если в нем меньше 1 рубля")
        @Test
        void shouldReturnMessageNoMoney() {
            BDDMockito.given(safe.getTotalAmountOfMoney()).willReturn(0);

            String exceptedMessage = "В банкомате нет денег";
            String actualMessage = atmBase.showBalance();

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("возвращает сообщение о количестве денег в банкомате")
        @Test
        void shouldReturnMessageMoneyInATM() {
            BDDMockito.given(safe.getTotalAmountOfMoney()).willReturn(1000);

            String exceptedMessage = "В банкомате: 1000руб.";
            String actualMessage = atmBase.showBalance();

            assertEquals(exceptedMessage, actualMessage);
        }
    }

    @DisplayName("блок тестов для отображения состояния выдачи денег")
    @Nested
    class GiveOutMoneyTest {
        @DisplayName("должен вернуть сообщение что нет сигнала")
        @Test
        void shouldReturnMessageNoSignal() {
            BDDMockito.given(safe.giveOutMoney(Mockito.anyInt())).willReturn(null);

            String exceptedMessage = "Нет сигнала";
            String actualMessage = atmBase.giveOutMoney(Mockito.anyInt());

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("должен вернуть сообщение о том, что недостаточно средств")
        @Test
        void mustReturnMessageInsufficientFunds() {
            BDDMockito.given(safe.giveOutMoney(Mockito.anyInt())).willReturn("300");

            String exceptedMessage = "Недостаточно средств";
            String actualMessage = atmBase.giveOutMoney(Mockito.anyInt());

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("должен вернуть сообщение о том, что нужен ввод другой суммы, кратной рекомендуемой")
        @Test
        void mustReturnMessageWithRecommendedAmount() {
            BDDMockito.given(safe.giveOutMoney(Mockito.anyInt())).willReturn("$100");

            String exceptedMessage = "Введите сумму кратную 100";
            String actualMessage = atmBase.giveOutMoney(Mockito.anyInt());

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("должен вернуть сообщение о том, что клиент не забрал деньги и они возвращены обратно в сейф")
        @Test
        void mustReturnMessageAboutReturnOfPartsOfFunds() {
            BDDMockito.given(safe.giveOutMoney(Mockito.anyInt())).willReturn("310");

            String exceptedMessage = "Деньги возвращены в банкомат";
            String actualMessage = atmBase.giveOutMoney(Mockito.anyInt());

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("должен вернуть сообщение о том, что деньги выданы успешно")
        @Test
        void ShouldReturnASuccessOperationMessage() {
            BDDMockito.given(safe.giveOutMoney(Mockito.anyInt())).willReturn("200");

            String exceptedMessage = "Деньги выданы";
            String actualMessage = atmBase.giveOutMoney(Mockito.anyInt());

            assertEquals(exceptedMessage, actualMessage);
        }
    }

    @DisplayName("блок тестов для отображения состояния принятия денег")
    @Nested
    class acceptMoneyTest {
        @DisplayName("возвращает сообщение о том, что не все банкноты приняты")
        @Test
        void mustReturnMessageNotAllBanknotesAreAccepted() {
            List<Integer> banknotes = new ArrayList<>(List.of(4));
            BDDMockito.given(transferToSafe.transfer(banknotes, safe)).willReturn(banknotes);

            String exceptedMessage = "Банкноты приняты не все, заберите непринятые банкноты";
            String actualMessage = atmBase.acceptMoney(banknotes);

            assertEquals(exceptedMessage, actualMessage);
        }

        @DisplayName("возвращает сообщение о успехе операции")
        @Test
        void returnsMessageAboutSuccessOfOperation() {
            List<Integer> banknotes = new ArrayList<>(List.of(50));
            BDDMockito.given(transferToSafe.transfer(banknotes, safe)).willReturn(Collections.emptyList());

            String exceptedMessage = "Деньги приняты";
            String actualMessage = atmBase.acceptMoney(banknotes);

            assertEquals(exceptedMessage, actualMessage);
        }
    }
}