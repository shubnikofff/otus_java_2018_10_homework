package ru.otus.money;

public class OneEuro implements Money {

    @Override
    public short getNominal() {
        return Money.ONE_EURO;
    }

    @Override
    public String toString() {
        return "One euro";
    }
}
