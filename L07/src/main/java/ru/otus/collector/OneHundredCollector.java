package ru.otus.collector;

import ru.otus.money.Money;
import ru.otus.operation.GiveMoney;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.Stack;

public class OneHundredCollector extends MoneyCollector {
    private HashMap<Short, Stack<Money>> atmCells;

    public OneHundredCollector(HashMap<Short, Stack<Money>> atmCells) {
        this.atmCells = atmCells;
    }

    @Override
    int collectMoney(int amount, OperationExecutor operationExecutor) {
        int remainAmount = amount;
        while (remainAmount >= Money.ONE_HUNDRED_EURO) {
            operationExecutor.addOperation(new GiveMoney(Money.ONE_HUNDRED_EURO, atmCells));
            remainAmount -= Money.ONE_HUNDRED_EURO;
        }

        return remainAmount;
    }
}
