package ru.otus.money;

public class FiftyEuro implements Money {

    @Override
    public short getNominal() {
        return Money.FIFTY_EURO;
    }

    @Override
    public String toString() {
        return 50 + "\u20ac";
    }
}
