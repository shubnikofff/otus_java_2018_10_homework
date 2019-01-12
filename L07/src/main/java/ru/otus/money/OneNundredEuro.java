package ru.otus.money;

public class OneNundredEuro implements Money {
    private static final short NOMINAL = 100;

    @Override
    public short getNominal() {
        return NOMINAL;
    }

    @Override
    public String toString() {
        return "One hundred euro";
    }
}
