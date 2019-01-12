package ru.otus.money;

public class TwentyEuro implements Money {
    private static final short NOMINAL = 20;

    @Override
    public short getNominal() {
        return NOMINAL;
    }

    @Override
    public String toString() {
        return "Twenty euro";
    }
}
