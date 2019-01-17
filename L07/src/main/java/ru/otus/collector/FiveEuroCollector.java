package ru.otus.collector;

import ru.otus.money.Money;

import java.util.HashMap;
import java.util.Stack;

public class FiveEuroCollector extends MoneyCollector {
    private Stack<Money> atmCell;

    public FiveEuroCollector(HashMap<Short, Stack<Money>> atmCells) {
        atmCell = atmCells.get(Money.FIVE_EURO);
    }

    @Override
    Stack<Money> getMoneyCell() {
        return atmCell;
    }

    @Override
    short getCellNominal() {
        return Money.FIVE_EURO;
    }
}
