package org.example;

import java.util.List;
import java.util.Scanner;

public class MechanismTransferToClient implements IMechanismsTransfer {

    @Override
    public List<Integer> transfer(List<Integer> listBanknotes, ISafe safe) {
        return emulationHuman(listBanknotes);
    }

    List<Integer> emulationHuman(List<Integer> listBanknotes) {
        boolean isMoneyInTheMechanism = true;

        Scanner scanner = new Scanner(System.in);
        System.out.println("\u001b[37;3mВведите 1 - если деньги забрали , и 2 - если нет\u001b[0m");
        if (scanner.nextLine().contains("1")) {
            isMoneyInTheMechanism = false;
        }

        if (!isMoneyInTheMechanism) {
            listBanknotes.clear();
        }
        return listBanknotes;
    }

}
