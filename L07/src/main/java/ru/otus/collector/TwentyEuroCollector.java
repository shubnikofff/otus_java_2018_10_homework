package ru.otus.collector;

import ru.otus.money.Money;

import java.util.HashMap;
import java.util.Stack;

public class TwentyEuroCollector extends MoneyCollector {
    private Stack<Money> atmCell;

    public TwentyEuroCollector(HashMap<Short, Stack<Money>> atmCells) {
        atmCell = atmCells.get(Money.TWENTY_EURO);
    }

    @Override
    Stack<Money> getMoneyCell() {
        return atmCell;
    }

    @Override
    short getCellNominal() {
        return Money.TWENTY_EURO;
    }
}
