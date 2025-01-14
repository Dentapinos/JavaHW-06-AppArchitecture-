package org.example;

import java.util.HashMap;
import java.util.Map;

public class ATMBaseImpl implements IATM {
    private final IBillAcceptor billAcceptor;

    public ATMBaseImpl(IBillAcceptor billAcceptor) {
        this.billAcceptor = billAcceptor;
    }

    @Override
    public String issuesMoney(int amount) {
        var banknotes = billAcceptor.giveTheBill(amount);
        if (banknotes.isEmpty()) return InfoCodeAndMessage.IMPOSSIBLE_ISSUE.getMessage();
        if (banknotes.containsKey(0))
            return InfoCodeAndMessage.RECOMMENDED_AMOUNT.getModifyingMessage(String.valueOf(banknotes.get(0)));
        return InfoCodeAndMessage.ISSUANCE_SUCCESSFUL.getModifyingMessage(String.valueOf(amount)) + "руб.";

    }

    @Override
    public String acceptMoney(Map<Integer, Integer> money) {
        if (billAcceptor.acceptTheBill(money)) return InfoCodeAndMessage.ACCEPT_BANKNOTES.getMessage();
        return InfoCodeAndMessage.ERROR_ACCEPT_BANKNOTES.getMessage();
    }

    @Override
    public String showHowMuchMoneyIsInTheATM() {
        return InfoCodeAndMessage.BALANCE_IN_ATM.getModifyingMessage(String.valueOf(billAcceptor.getRemainingMoney(new HashMap<>()))) + "руб.";
    }
}
