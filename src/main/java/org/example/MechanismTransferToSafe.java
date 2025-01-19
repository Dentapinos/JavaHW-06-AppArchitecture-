package org.example;

import java.util.ArrayList;
import java.util.List;

public class MechanismTransferToSafe implements IMechanismsTransfer {
    List<Integer> allowedDenominations = new ArrayList<>(List.of(50, 100, 200, 500, 1000, 2000, 5000, 10_000));

    @Override
    public List<Integer> transfer(List<Integer> listBanknotes, ISafe safe) {
        List<Integer> goodBanknotes = new ArrayList<>();
        List<Integer> badBanknotes = new ArrayList<>();

        for (Integer banknote : listBanknotes) {
            if (allowedDenominations.contains(banknote)) goodBanknotes.add(banknote);
            else badBanknotes.add(banknote);
        }

        if (!goodBanknotes.isEmpty()) {
            safe.acceptMoney(goodBanknotes);
        }

        return badBanknotes;
    }
}
