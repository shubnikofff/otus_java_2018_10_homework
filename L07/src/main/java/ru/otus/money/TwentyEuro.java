package ru.otus.money;

public class TwentyEuro implements Money {

    @Override
    public short getNominal() {
        return Money.TWENTY_EURO;
    }

    @Override
    public String toString() {
        return 20 + "\u20ac";
    }
}
