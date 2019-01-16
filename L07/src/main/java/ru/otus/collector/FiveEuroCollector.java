package ru.otus.collector;

import ru.otus.money.Money;
import ru.otus.operation.GiveMoney;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.Stack;

public class FiveEuroCollector extends MoneyCollector {
    private HashMap<Short, Stack<Money>> atmCells;

    public FiveEuroCollector(HashMap<Short, Stack<Money>> atmCells) {
        this.atmCells = atmCells;
    }

    @Override
    int collectMoney(int amount, OperationExecutor operationExecutor) {
        int remainAmount = amount;
        while (remainAmount >= Money.FIVE_EURO) {
            operationExecutor.addOperation(new GiveMoney(Money.FIVE_EURO, atmCells));
            remainAmount -= Money.FIVE_EURO;
        }

        return remainAmount;
    }
}
