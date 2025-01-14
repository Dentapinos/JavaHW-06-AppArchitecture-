package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест Банкомата")
class ATMImplTest {
    private static BillAcceptorImpl mockBillAcceptor;
    private static ATMBaseImpl atm;

    @BeforeEach
    public void setUp() {
        mockBillAcceptor = Mockito.mock(BillAcceptorImpl.class);
        atm = new ATMBaseImpl(mockBillAcceptor);
    }

    @DisplayName("Должен вернуть сообщение \"Банкноты приняты\"")
    @Test
    void testAcceptBanknotesSuccessful() {
        BDDMockito.given(mockBillAcceptor.acceptTheBill(Mockito.anyMap())).willReturn(true);

        String exceptedMessage = InfoCodeAndMessage.ACCEPT_BANKNOTES.getMessage();
        String actualMessage = atm.acceptMoney(new HashMap<>());

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("Должен вернуть сообщение \"Ошибка принятия банкнот\"")
    @Test
    void testAcceptBanknotesExcepted() {
        BDDMockito.given(mockBillAcceptor.acceptTheBill(Mockito.anyMap())).willReturn(false);

        String exceptedMessage = InfoCodeAndMessage.ERROR_ACCEPT_BANKNOTES.getMessage();
        String actualMessage = atm.acceptMoney(new HashMap<>());

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("Должен вернуть сообщение о том что в банкомате недостаточно средств")
    @Test
    void testIssueBanknotes1() {
        Map<Integer, Integer> banknotes = new HashMap<>();
        BDDMockito.given(mockBillAcceptor.giveTheBill(Mockito.anyInt())).willReturn(banknotes);

        String exceptedMessage = InfoCodeAndMessage.IMPOSSIBLE_ISSUE.getMessage();
        String actualMessage = atm.issuesMoney(Mockito.anyInt());

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("Должен вернуть сообщение что банкомат может выдать сумму кратную 200")
    @Test
    void testIssueBanknotes2() {
        Map<Integer, Integer> banknotes = Map.of(0, 200);
        BDDMockito.given(mockBillAcceptor.giveTheBill(Mockito.anyInt())).willReturn(banknotes);

        String exceptedMessage = InfoCodeAndMessage.RECOMMENDED_AMOUNT.getMessage() + 200;
        String actualMessage = atm.issuesMoney(Mockito.anyInt());

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("Должен вернуть сообщение о успешной выдаче средств")
    @Test
    void testIssueBanknotes3() {
        Map<Integer, Integer> banknotes = Map.of(50, 2, 500, 3);
        BDDMockito.given(mockBillAcceptor.giveTheBill(Mockito.anyInt())).willReturn(banknotes);

        String exceptedMessage = InfoCodeAndMessage.ISSUANCE_SUCCESSFUL.getMessage() + 600 + "руб.";
        String actualMessage = atm.issuesMoney(600);

        assertEquals(exceptedMessage, actualMessage);
    }

    @DisplayName("Должен вернуть сообщение о количестве денег на счету")
    @Test
    void testAtmMoneyBalance() {
        BDDMockito.given(mockBillAcceptor.getRemainingMoney(Mockito.anyMap())).willReturn(200);

        String exceptedMessage = InfoCodeAndMessage.BALANCE_IN_ATM.getMessage() + 200 + "руб.";
        String actualMessage = atm.showHowMuchMoneyIsInTheATM();

        assertEquals(exceptedMessage, actualMessage);
    }
}