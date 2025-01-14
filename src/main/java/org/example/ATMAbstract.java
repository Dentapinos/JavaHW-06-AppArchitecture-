package org.example;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
abstract class ATMAbstract implements IATM {
    protected IATM atm;

    @Override
    public String issuesMoney(int amount) {
        return atm.issuesMoney(amount);
    }

    @Override
    public String showHowMuchMoneyIsInTheATM() {
        return atm.showHowMuchMoneyIsInTheATM();
    }

    @Override
    public String acceptMoney(Map<Integer, Integer> money) {
        return atm.acceptMoney(money);
    }
}
