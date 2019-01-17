package ru.otus.operation;

import ru.otus.money.Money;

import java.util.Stack;

public class GiveMoney implements Operation {
    private Stack<Money> moneyCell;

    public GiveMoney(Stack<Money> moneyCell) {
        this.moneyCell = moneyCell;
    }

    @Override
    public void execute() {
        if (moneyCell.empty()) {
            System.out.println("Can't give money because cell is empty");
            return;
        }

        Money money = moneyCell.pop();
        System.out.println("- Give out " + money);
    }
}
