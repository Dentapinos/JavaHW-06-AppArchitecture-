package org.example;

import java.util.List;

public class CheckTheBills implements ISecurity {
    @Override
    public boolean isDenominationCompliant(int banknoteNominal) {
        List<Integer> nominals = List.of(50, 100, 200, 500, 1000, 2000, 5000, 10000);
        return nominals.contains(banknoteNominal);
    }
}
