package ru.otus.money;

public class FiveEuro implements Money {

    @Override
    public short getNominal() {
        return Money.FIVE_EURO;
    }

    @Override
    public String toString() {
        return 5 + "\u20ac";
    }
}
