package ru.otus.money;

public class TenEuro implements Money {
    private static final short NOMINAL = 10;

    @Override
    public short getNominal() {
        return NOMINAL;
    }

    @Override
    public String toString() {
        return "Ten euro";
    }
}
