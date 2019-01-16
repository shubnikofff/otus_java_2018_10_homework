package ru.otus.money;

public interface Money {
    short FIVE_EURO = 5;
    short TEN_EURO = 10;
    short TWENTY_EURO = 20;
    short FIFTY_EURO = 50;
    short ONE_HUNDRED_EURO = 100;

    short getNominal();
}
