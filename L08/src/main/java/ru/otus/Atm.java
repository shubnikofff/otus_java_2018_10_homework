package ru.otus;

import java.util.Map;

public class Atm {
    private Map<BanknoteNominal, Integer> state;

    public Atm(Map<BanknoteNominal, Integer> initialState) {
        state = initialState;
    }


}
