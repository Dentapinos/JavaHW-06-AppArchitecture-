package org.example;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BillAcceptorImpl implements IBillAcceptor {
    private final Map<Integer, Integer> safe = new HashMap<>();
    private final ISecurity scanner;

    public BillAcceptorImpl(ISecurity scanner) {
        this.scanner = scanner;
    }

    public Map<Integer, Integer> getSafe() {
        return new HashMap<>(safe);
    }

    @Override
    public boolean acceptTheBill(Map<Integer, Integer> banknoteNominals) {
        for (var entry : banknoteNominals.entrySet()) {
            if (!scanner.isDenominationCompliant(entry.getKey())) return false;
        }

        for (var entry : banknoteNominals.entrySet()) {
            if (safe.containsKey(entry.getKey())) safe.put(entry.getKey(), safe.get(entry.getKey()) + entry.getValue());
            else safe.put(entry.getKey(), entry.getValue());
        }
        return true;
    }

    @Override
    public Map<Integer, Integer> giveTheBill(int amount) {

        if (safe.isEmpty() || amount > getRemainingMoney(getSafe())) return new HashMap<>();

        return getAmountOfMoneyInLargestBills(amount);
    }

    @Override
    public int getRemainingMoney(Map<Integer, Integer> moneys) {
        var balance = 0;
        var moneysActual = safe;
        if (!moneys.isEmpty()) moneysActual = moneys;
        if (moneysActual.isEmpty()) return 0;

        for (var entry : moneysActual.entrySet()) {
            if (entry.getValue() > 0) {
                balance += entry.getKey() * entry.getValue();
            }
        }
        return balance;
    }

    private Map<Integer, Integer> getAmountOfMoneyInLargestBills(int amount) {
        Map<Integer, Integer> banknotes = new HashMap<>();

        var keys = safe.entrySet().stream().filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .sorted().toList().reversed();

        for (var key : keys) {
            if (amount / key > 0) {
                int a = amount / key;
                if (safe.get(key) >= a) {
                    int c = safe.get(key) - a;
                    safe.put(key, c);
                    banknotes.put(key, a);
                    amount -= a * key;
                } else {
                    banknotes.put(key, safe.get(key));
                    safe.put(key, 0);
                    amount -= banknotes.get(key) * key;
                }
            }
        }

        if (amount != 0) {
            acceptTheBill(banknotes);
            return getRecommendedAmount(keys);
        }

        return banknotes;
    }

    private Map<Integer, Integer> getRecommendedAmount(List<Integer> banknotesInATM) {
        var largestBanknote = banknotesInATM.stream().min(Integer::compareTo).orElse(0).describeConstable();
        return Map.of(0, largestBanknote.get());
    }
}
