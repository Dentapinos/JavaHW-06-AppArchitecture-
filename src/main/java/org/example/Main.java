package org.example;

import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //пример взаимодействия с банкоматом
        var atm = new ATMBaseImpl(new BillAcceptorImpl(new CheckTheBills()));
        HashMap<Integer, Integer> banknotes = new HashMap<>();
        banknotes.put(50, 0);
        banknotes.put(200, 1);
        banknotes.put(1000, 1);
        System.out.println(atm.acceptMoney(banknotes));
        System.out.println(atm.showHowMuchMoneyIsInTheATM());
        System.out.println(atm.issuesMoney(200));
        System.out.println(atm.showHowMuchMoneyIsInTheATM());
    }
}