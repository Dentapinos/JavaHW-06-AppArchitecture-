package org.example;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        1
//        MechanismTransferToClient transferToClient = new MechanismTransferToClient();
//        Safe safe = new Safe(transferToClient);
//        MechanismTransferToSafe transferToSafe = new MechanismTransferToSafe();
//        ATMBaseImpl atm1 = new ATMBaseImpl(safe, transferToSafe);

//        2
//        MechanismTransferToClient transferToClient = new MechanismTransferToClient();
//        Safe safe = new Safe(transferToClient);
//        MechanismTransferToSafe transferToSafe = new MechanismTransferToSafe();
//        ATMBaseImpl atm2 = new ATMBuilder()
//                .setSafe(safe)
//                .setTransferToSafe(transferToSafe)
//                .build();

//        3

        ATMBaseImpl atm = new ATMBuilder().build();
        System.out.println("\u001b[33mТест1\u001b[0m");
        //выдаем сумму большую чем в банке
        atm.showBalance();
        atm.giveOutMoney(1000);

        System.out.println("\u001b[33mТест2\u001b[0m");
        //выдаем сумму, которая есть в банкомате, но невозможно выдать
        atm.showBalance();
        atm.acceptMoney(List.of(100, 200, 100));
        atm.showBalance();
        atm.giveOutMoney(150);

        System.out.println("\u001b[33mТест3\u001b[0m");
        //выдаем сумму, которая есть в банкомате, и он должен выдать сумму наибольшими купюрами
        atm.giveOutMoney(200);
        atm.showBalance();

        System.out.println("\u001b[33mТест4\u001b[0m");
        //запрос на выдачу отрицательной суммы
        atm.giveOutMoney(-100);
        atm.showBalance();

        System.out.println("\u001b[33mТест5\u001b[0m");
        //пополнение фальшивками
        atm.showBalance();
        atm.acceptMoney(List.of(345, -4, 57, 55, 10_000_000));
        atm.showBalance();

        System.out.println("\u001b[33mТест6\u001b[0m");
        //пополнение и фальшивками и настоящими
        atm.showBalance();
        atm.acceptMoney(List.of(345, -4, 500, 50, 1000, 5000, 2000, 10_000, 57, 55, 10_000_000));
        atm.showBalance();

        System.out.println("\u001b[33mТест7\u001b[0m");
        //пополнение без денег
        atm.showBalance();
        atm.acceptMoney(Collections.emptyList());
        atm.showBalance();
    }
}