package ru.otus.operation;

import ru.otus.money.Money;

import java.util.HashMap;
import java.util.Stack;

public class GiveMoney implements Operation {
    private Short nominal;
    private HashMap<Short, Stack<Money>> atmCells;

    public GiveMoney(Short nominal, HashMap<Short, Stack<Money>> atmCells) {
        this.nominal = nominal;
        this.atmCells = atmCells;
    }

    @Override
    public void execute() {
        if (atmCells.containsKey(nominal)) {
            Money money = atmCells.get(nominal).pop();
            System.out.println("- Give out " + money);
        } else {
            System.out.println("There is no cell with nominal " + nominal);
        }
    }
}
