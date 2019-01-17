package ru.otus.collector;

import ru.otus.money.Money;

import java.util.HashMap;
import java.util.Stack;

public class OneHundredCollector extends MoneyCollector {
    private Stack<Money> atmCell;

    public OneHundredCollector(HashMap<Short, Stack<Money>> atmCells) {
        atmCell = atmCells.get(Money.ONE_HUNDRED_EURO);
    }

    @Override
    Stack<Money> getMoneyCell() {
        return atmCell;
    }

    @Override
    short getCellNominal() {
        return Money.ONE_HUNDRED_EURO;
    }
}
