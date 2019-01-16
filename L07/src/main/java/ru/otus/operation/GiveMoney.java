package ru.otus.operation;

import ru.otus.AtmCell;

import java.util.HashMap;

public class GiveMoney implements Operation {
    private short nominal;
    private HashMap<Short, AtmCell> atmCells;

    public GiveMoney(short nominal, HashMap<Short, AtmCell> atmCells) {
        this.nominal = nominal;
        this.atmCells = atmCells;
    }

    @Override
    public void execute() {
        try {
            if (atmCells.containsKey(nominal)) {
                atmCells.get(nominal).pop();
                System.out.println("- Give out " + nominal);
            } else {
                System.out.println("There is no cell with nominal " + nominal);
            }
        } catch (Exception e) {
            System.out.println(nominal + ": "  + e.getMessage());
        }
    }
}
