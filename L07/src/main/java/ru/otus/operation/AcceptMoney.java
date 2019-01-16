package ru.otus.operation;

import ru.otus.AtmCell;
import ru.otus.money.Money;

import java.util.HashMap;

public class AcceptMoney implements Operation {
    private HashMap<Short, AtmCell> atmCells;
    private Money money;

    public AcceptMoney(HashMap<Short, AtmCell> atmCells, Money money) {
        this.atmCells = atmCells;
        this.money = money;
    }

    @Override
    public void execute() {
        if (atmCells.containsKey(money.getNominal())) {
            atmCells.get(money.getNominal()).put();
            System.out.println("+ Accepted " + money);
        } else {
            System.out.println("! Can't accept " + money + " ATM doesn't work with that");
        }
    }
}
