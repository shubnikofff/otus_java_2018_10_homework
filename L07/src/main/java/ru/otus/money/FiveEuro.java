package ru.otus.money;

public class FiveEuro implements Money {
    private static final short NOMINAL = 5;

    @Override
    public short getNominal() {
        return NOMINAL;
    }

    @Override
    public String toString() {
        return "Five euro";
    }
}
