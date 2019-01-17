package ru.otus;

import ru.otus.collector.FiveEuroCollector;
import ru.otus.collector.OneHundredCollector;
import ru.otus.collector.TwentyEuroCollector;
import ru.otus.money.Money;
import ru.otus.operation.AcceptMoney;
import ru.otus.operation.GiveMoney;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class Atm {
    private HashMap<Short, Stack<Money>> cells;

    Atm(short[] possibleNominals) {
        System.out.println("\nCreate ATM...");
        cells = new HashMap<>(possibleNominals.length);
        for (short nominal : possibleNominals) {
            cells.put(nominal, new Stack<>());
        }
    }

    void putMoney(List<Money> money) {
        System.out.println("\nPut money...");
        OperationExecutor operationExecutor = new OperationExecutor();
        money.forEach(banknote -> {
            AcceptMoney acceptMoney = new AcceptMoney(cells, banknote);
            operationExecutor.addOperation(acceptMoney);
        });
        operationExecutor.executeOperations();
    }

    void giveMoney(int amount) throws ImpossibleGiveMoneyException {
        System.out.println("\nTry to give amount: " + amount + "...");

        var operationExecutor = new OperationExecutor();

        var fiveEuroCollector = new FiveEuroCollector(cells);
        var twentyEuroCollector = new TwentyEuroCollector(cells);
        var oneHundredCollector = new OneHundredCollector(cells);

        oneHundredCollector.setNext(twentyEuroCollector);
        twentyEuroCollector.setNext(fiveEuroCollector);
        oneHundredCollector.process(amount, operationExecutor);

        operationExecutor.executeOperations();
    }

    void giveAllMoney() {
        System.out.println("\nGive all money...");
        var operationExecutor = new OperationExecutor();

        cells.values().forEach(cell -> {
            for (int i = 0; i < cell.size(); i++) {
                operationExecutor.addOperation(new GiveMoney(cell));
            }
        });

        operationExecutor.executeOperations();
    }

    void printBalance() {
        int balance = 0;

        for (Map.Entry<Short, Stack<Money>> entry : cells.entrySet()) {
            balance += entry.getKey() * entry.getValue().size();
        }

        System.out.println("Balance: " + balance + "\u20ac");
    }
}
