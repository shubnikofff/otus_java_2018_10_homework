package ru.otus.money;

public class TenEuro implements Money {

    @Override
    public short getNominal() {
        return Money.TEN_EURO;
    }

    @Override
    public String toString() {
        return "Ten euro";
    }
}
