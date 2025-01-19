package org.example;

import java.util.List;

public class ATMBaseImpl implements IATM {
    ISafe safe;
    IMechanismsTransfer transferToSafe;

    public ATMBaseImpl(ISafe safe, IMechanismsTransfer transferToSafe) {
        this.safe = safe;
        this.transferToSafe = transferToSafe;
    }

    @Override
    public String showBalance() {
        String message = "Нет сигнала";
        if (safe != null) {
            int moneyInSafe = safe.getTotalAmountOfMoney();
            if (moneyInSafe > 0) {
                message = "В банкомате: " + moneyInSafe + "руб.";
            } else {
                message ="В банкомате нет денег";
            }
        }
        System.out.println(message);
        return message;
    }

    @Override
    public String giveOutMoney(int amount) {
        String message = "Нет сигнала";
        String messageCodeFromSafe = safe.giveOutMoney(amount);
        if (messageCodeFromSafe == null) {
            return message;
        } else if (messageCodeFromSafe.equals("300")){
            message = "Недостаточно средств";
        } else if (messageCodeFromSafe.matches("\\$[0-9]*")){
            message = "Введите сумму кратную " + messageCodeFromSafe.replace("$", "");
        } else if (messageCodeFromSafe.equals("310")){
            message = "Деньги возвращены в банкомат";
        } else {
            message = "Деньги выданы";
        }
        System.out.println(message);
        return message;
    }

    @Override
    public String acceptMoney(List<Integer> banknotes) {  //ок
        List<Integer> unacceptedBanknotes = transferToSafe.transfer(banknotes, safe);
        String message = "Нет сигнала";
        if (unacceptedBanknotes == null) return message;
        if (!unacceptedBanknotes.isEmpty()) {
            message = "Банкноты приняты не все, заберите непринятые банкноты";
        } else {
            message = "Деньги приняты";
        }
        System.out.println(message);
        return message;
    }
}
