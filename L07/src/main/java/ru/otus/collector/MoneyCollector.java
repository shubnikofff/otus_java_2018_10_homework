package ru.otus.collector;

import ru.otus.operation.OperationExecutor;

abstract public class MoneyCollector {
    private MoneyCollector next;

    private MoneyCollector getNext() {
        return next;
    }

    public void setNext(MoneyCollector next) {
        this.next = next;
    }

    public void process(int amount, OperationExecutor operationExecutor) throws Exception {
        int remainAmount = collectMoney(amount, operationExecutor);

        if (remainAmount == 0) {
            System.out.println("Amount collected");
            return;
        }

        printRemainAmount(remainAmount);

        if (getNext() != null) {
            getNext().process(remainAmount, operationExecutor);
        } else {
            throw new Exception("Required amount can't be collected");
        }
    }

    private void printRemainAmount(int amount) {
        System.out.println("It remains to process: " + amount);
    }

    abstract int collectMoney(int amount, OperationExecutor operationExecutor);
}
