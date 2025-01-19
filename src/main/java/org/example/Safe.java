package org.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Safe implements ISafe {
    Map<Integer, Integer> cashStorageCell;
    IMechanismsTransfer transferToClient;

    public Safe(IMechanismsTransfer transferToClient) {
        cashStorageCell = new HashMap<>();
        this.transferToClient = transferToClient;
    }

    public Map<Integer, Integer> getCashStorageCell() {
        return new HashMap<>(cashStorageCell);
    }

    @Override
    public int getTotalAmountOfMoney() {
        return cashStorageCell.entrySet().stream().mapToInt(entry -> entry.getKey() * entry.getValue()).sum();
    }

    @Override
    public void acceptMoney(List<Integer> verifiedBanknotes) {
        for (Integer banknote : verifiedBanknotes){
            cashStorageCell.merge(banknote, 1, Integer::sum);
        }
    }

    @Override
    public String giveOutMoney(int amount) {
        if (amount > getTotalAmountOfMoney()) return "300";
        List<Integer> returnedList = collectTheRequiredAmountInTheSafe(amount);
        if (returnedList.isEmpty()) return null;
        if (returnedList.getFirst() == 0) return  "$" + returnedList.getLast();
        List<Integer> transferResult = transferToClient.transfer(returnedList, this);
        if (!transferResult.isEmpty()) {
            acceptMoney(transferResult);
            return "310";
        }
        return "200";
    }

    public int getRecommendedAmount(List<Integer> banknotesInATM) {
        var largestBanknote = banknotesInATM.stream().min(Integer::compareTo);
        return largestBanknote.orElse(0);
    }

    private List<Integer> getDenominationsBanknotes(Map<Integer, Integer> banknotes) {
        return banknotes.entrySet().stream().filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .sorted().toList().reversed();
    }

    private List<Integer> collectTheRequiredAmountInTheSafe(int amount) {
        List<Integer> listNominals = getDenominationsBanknotes(cashStorageCell);
        List<Integer> banknotes = new ArrayList<>();

        for (var key : listNominals) {
            if (amount / key > 0) {
                int a = amount / key;
                if (cashStorageCell.get(key) >= a) {
                    int c = cashStorageCell.get(key) - a;
                    cashStorageCell.put(key, c);
                    for (int i = 0; i < a; i++) banknotes.add(key);
                    amount -= a * key;
                } else {
                    for (int i = 0; i < cashStorageCell.get(key); i++) banknotes.add(key);
                    cashStorageCell.put(key, 0);
                    amount -= banknotes.get(key) * key;
                }
            }
        }

        if (amount != 0) {
            acceptMoney(banknotes);
            banknotes.clear();
            banknotes.add(0);
            banknotes.add(getRecommendedAmount(listNominals));
        }
        return banknotes;
    }


}
