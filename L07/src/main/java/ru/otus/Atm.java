package ru.otus;

import ru.otus.money.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm {
    private HashMap<Short, MoneyCell> moneyCells;

    public Atm(short[] possibleNominals) {
        moneyCells = new HashMap<>(possibleNominals.length);
        for (short nominal : possibleNominals) {
            moneyCells.put(nominal, new MoneyCell());
        }
    }

    void putMoney(List<Money> money) {
        money.forEach(banknote -> {
            if (moneyCells.containsKey(banknote.getNominal())) {
                moneyCells.get(banknote.getNominal()).putMoney();
                System.out.println("Putted " + banknote);
            } else {
                System.out.println("Nominal " + banknote.getNominal() + " is unsupported");
            }

        });
    }

    int getBalance() {
        int balance = 0;

        for (Map.Entry<Short, MoneyCell> entry : moneyCells.entrySet()) {
            balance += entry.getKey() * entry.getValue().getAmount();
        }

        return balance;
    }
}
