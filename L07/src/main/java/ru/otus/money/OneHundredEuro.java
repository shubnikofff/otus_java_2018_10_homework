package ru.otus.money;

public class OneHundredEuro implements Money {

    @Override
    public short getNominal() {
        return Money.ONE_HUNDRED_EURO;
    }

    @Override
    public String toString() {
        return "One hundred euro";
    }
}
