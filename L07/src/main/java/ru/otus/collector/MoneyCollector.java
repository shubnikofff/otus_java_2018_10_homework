package ru.otus.collector;

import ru.otus.ImpossibleGiveMoneyException;
import ru.otus.money.Money;
import ru.otus.operation.GiveMoney;
import ru.otus.operation.OperationExecutor;

import java.util.Stack;

abstract public class MoneyCollector {
    private MoneyCollector next;

    private MoneyCollector getNext() {
        return next;
    }

    public void setNext(MoneyCollector next) {
        this.next = next;
    }

    public void process(int amount, OperationExecutor operationExecutor) throws ImpossibleGiveMoneyException {
        int remainAmount = amount;
        Stack<Money> moneyCell = getMoneyCell();
        short cellNominal = getCellNominal();
        int cellSize = moneyCell.size();

        while (cellSize > 0 && remainAmount >= cellNominal) {
            operationExecutor.addOperation(new GiveMoney(moneyCell));
            remainAmount -= cellNominal;
            cellSize--;
        }

        if (remainAmount == 0) {
            System.out.println("Amount has been collected!");
            return;
        }

        if (getNext() != null) {
            getNext().process(remainAmount, operationExecutor);
        } else {
            throw new ImpossibleGiveMoneyException("Required amount can't be collected");
        }
    }

    abstract Stack<Money> getMoneyCell();
    abstract short getCellNominal();
}
