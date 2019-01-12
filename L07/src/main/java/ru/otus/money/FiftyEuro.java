package ru.otus.money;

public class FiftyEuro implements Money {
    private static final short NOMINAL = 50;

    @Override
    public short getNominal() {
        return NOMINAL;
    }

    @Override
    public String toString() {
        return "Fifty euro";
    }
}
