package ru.otus;

import ru.otus.money.Money;
import ru.otus.operation.AcceptMoney;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Atm {
    private HashMap<Short, AtmCell> cells;

    Atm(short[] possibleNominals) {
        System.out.println("Create ATM...");
        cells = new HashMap<>(possibleNominals.length);
        for (short nominal : possibleNominals) {
            cells.put(nominal, new AtmCell());
        }
    }

    void putMoney(List<Money> money) {
        OperationExecutor operationExecutor = new OperationExecutor();
        money.forEach(banknote -> {
            AcceptMoney acceptMoney = new AcceptMoney(cells, banknote);
            operationExecutor.addOperation(acceptMoney);
        });
        operationExecutor.executeOperations();
    }

    int getBalance() {
        int balance = 0;

        for (Map.Entry<Short, AtmCell> entry : cells.entrySet()) {
            balance += entry.getKey() * entry.getValue().getAmount();
        }

        return balance;
    }
}
