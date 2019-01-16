package ru.otus.collector;

import ru.otus.money.Money;
import ru.otus.operation.GiveMoney;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.Stack;

public class TwentyEuroCollector extends MoneyCollector {
    private HashMap<Short, Stack<Money>> atmCells;

    public TwentyEuroCollector(HashMap<Short, Stack<Money>> atmCells) {
        this.atmCells = atmCells;
    }

    @Override
    int collectMoney(int amount, OperationExecutor operationExecutor) {
        int remainAmount = amount;
        while (remainAmount >= Money.TWENTY_EURO) {
            operationExecutor.addOperation(new GiveMoney(Money.TWENTY_EURO, atmCells));
            remainAmount -= Money.TWENTY_EURO;
        }

        return remainAmount;
    }
}
