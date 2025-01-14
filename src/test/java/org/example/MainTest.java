package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    private static IATM atm;

    @BeforeAll
    static void setUp() {
        ISecurity bills = new CheckTheBills();
        BillAcceptorImpl acceptor = new BillAcceptorImpl(bills);
        atm = new ATMBaseImpl(acceptor);
    }

    @DisplayName("Проверяем программу по базовому сценарию")
    @Test
    void testBaseScenariosForInteractionWithAnATM() {
        var banknotes1200 = Map.of(100, 2, 500, 2);

        //1. Просим денег у пустого автомата(сообщает что нет денег)
        String msg1 = atm.issuesMoney(600);
        assertEquals(msg1, "Невозможно выдать сумму");

        //2. Заполняем банкомат на 1200
        atm.acceptMoney(banknotes1200);

        //3. Снимаем все средства которые есть в нем(выдает деньги)
        String msg2 = atm.issuesMoney(1200);
        assertEquals(msg2, "Получите ваши деньги:1200руб.");

        //4. Сравниваем остаток денег с ожидаемым
        assertEquals(atm.showHowMuchMoneyIsInTheATM(), "Остаток денег в банкомате:0руб.");

        //5. Дважды вносим средства в банкомат(ожидаем что на счету будет 2400)
        atm.acceptMoney(banknotes1200);
        atm.acceptMoney(banknotes1200);
        assertEquals(atm.showHowMuchMoneyIsInTheATM(), "Остаток денег в банкомате:2400руб.");

        //6. Заведомо запрашиваем большую сумму чем в банкомате(должен сообщить что денег нет)
        String msg3 = atm.issuesMoney(2500);
        assertEquals(msg3, "Невозможно выдать сумму");

        //7. Запрашиваем такую сумму, что бы в банкомате она была,
        // но выдать было не возможно из-за номинала купюр(будет предложено ввести другую сумму кратную 100)
        String msg4 = atm.issuesMoney(50);
        assertEquals(msg4, "Невозможно выдать указанную сумму, введите сумму кратную:100");

        //8. Теперь снимаем сумму кратную предложенной
        String msg5 = atm.issuesMoney(400);
        assertEquals(msg5, "Получите ваши деньги:400руб.");

        //9. Сравниваем остаток на счету после всех операций
        assertEquals(atm.showHowMuchMoneyIsInTheATM(), "Остаток денег в банкомате:2000руб.");
    }
}