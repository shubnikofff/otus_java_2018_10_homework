package ru.otus.operation;

import ru.otus.money.Money;

import java.util.HashMap;
import java.util.Stack;

public class AcceptMoney implements Operation {
    private HashMap<Short, Stack<Money>> atmCells;
    private Money money;

    public AcceptMoney(HashMap<Short, Stack<Money>> atmCells, Money money) {
        this.atmCells = atmCells;
        this.money = money;
    }

    @Override
    public void execute() {
        if (atmCells.containsKey(money.getNominal())) {
            atmCells.get(money.getNominal()).push(money);
            System.out.println("+ Accepted " + money);
        } else {
            System.out.println("! Can't accept " + money + " ATM doesn't work with that");
        }
    }
}
