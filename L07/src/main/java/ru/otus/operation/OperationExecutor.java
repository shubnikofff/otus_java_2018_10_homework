package ru.otus.operation;

import java.util.ArrayList;
import java.util.List;

public class OperationExecutor {
    private List<Operation> operations = new ArrayList<>();

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public void executeOperations() {
        operations.forEach(Operation::execute);
    }
}
