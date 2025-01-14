package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест купюроприемника")
class BillAcceptorImplTest {

    private static ISecurity bills;
    private static BillAcceptorImpl billAcceptor;

    @BeforeAll
    static void setUp() {
        bills = new CheckTheBills();
        billAcceptor = new BillAcceptorImpl(bills);
    }

    @AfterEach
    void tearDown() {
        billAcceptor = new BillAcceptorImpl(bills);
    }

    @DisplayName("Должен вернуть коллекцию похожую на отправленную")
    @Test
    void testAcceptTheBill() {
        Map<Integer, Integer> map1 = Map.of(50, 2, 1000, 5 );
        billAcceptor.acceptTheBill(map1);
        Map<Integer, Integer> map2 = billAcceptor.getSafe();
        assertEquals(map1, map2);
    }

    @DisplayName("После двух внесений средств, должен вернуть НЕ перезаписанные значения а сложенные")
    @Test
    void testAcceptTheBill2() {
        Map<Integer, Integer> map1 = Map.of(50, 2, 1000, 5 );
        billAcceptor.acceptTheBill(map1);
        billAcceptor.acceptTheBill(map1);
        int exceptedNumber = 10;
        int resultNumber = billAcceptor.getSafe().get(1000);
        assertEquals(exceptedNumber, resultNumber);
    }

    @DisplayName("Должен вернуть пустую коллекцию")
    @Test
    void testAcceptTheBill3() {
        Map<Integer, Integer> map1 = Map.of(40, 2, 1000, 5 );
        billAcceptor.acceptTheBill(map1);
        billAcceptor.acceptTheBill(map1);
        var exceptedNumber = new HashMap<Integer, Integer>();
        var resultNumber = billAcceptor.getSafe();
        assertEquals(exceptedNumber, resultNumber);
    }

    @DisplayName("Должен вернуть пустую коллекцию")
    @Test
    void giveTheBill() {
        int sum = 1000;
        var exceptedNumber = new HashMap<Integer, Integer>();
        var resultNumber = billAcceptor.giveTheBill(sum);
        assertEquals(exceptedNumber, resultNumber);
    }

    @DisplayName("Должен вернуть коллекцию с одной купюрой в 1000")
    @Test
    void giveTheBill2() {
        int sum = 1000;
        var map1 = Map.of(50, 2, 1000, 5 );
        billAcceptor.acceptTheBill(map1);
        var exceptedNumber = Map.of(1000, 1);
        var resultNumber = billAcceptor.giveTheBill(sum);
        assertEquals(exceptedNumber, resultNumber);
    }

    @DisplayName("должен вернуть коллекцию с одним ключом 0, значением кратности рекомендуемой суммы равной 200")
    @Test
    void giveTheBill3() {
        int sum = 500;
        var map1 = Map.of( 200, 3 );
        billAcceptor.acceptTheBill(map1);
        var exceptedNumber = 200;
        var resultNumber = billAcceptor.giveTheBill(sum).get(0);
        assertEquals(exceptedNumber, resultNumber);
    }

    @DisplayName("должен вернуть пустую коллекцию а коллекцию сейф оставить без изменений")
    @Test
    void giveTheBill4() {
        int sum = 5000;
        var map1 = Map.of( 200, 3, 1000, 2 );
        billAcceptor.acceptTheBill(map1);

        var exceptedCollection1 = new HashMap<Integer, Integer>();
        var exceptedCollection2 = billAcceptor.getSafe();
        var resultCollection1 = billAcceptor.giveTheBill(sum);
        var resultCollection2 = billAcceptor.getSafe();

        assertEquals(exceptedCollection1, resultCollection1);
        assertEquals(exceptedCollection2, resultCollection2);
    }

    @DisplayName("должен вернуть сумму переданную в коллекции")
    @Test
    void getRemainingMoney() {
        int exceptedSum = 2600;
        var map1 = Map.of( 200, 3, 1000, 2 );
        int resultSum = billAcceptor.getRemainingMoney(map1);
        assertEquals(exceptedSum, resultSum);
    }

    @DisplayName("должен вернуть сумму равную 0")
    @Test
    void getRemainingMoney2() {
        int exceptedSum = 0;
        Map<Integer, Integer> map1 = new HashMap<>();
        int resultSum = billAcceptor.getRemainingMoney(map1);
        assertEquals(exceptedSum, resultSum);
    }
}