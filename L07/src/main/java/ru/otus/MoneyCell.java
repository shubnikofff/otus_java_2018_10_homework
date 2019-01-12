package ru.otus;

import ru.otus.money.Money;

public class MoneyCell {
    private short nominal;
    private int amount;

    public MoneyCell(short nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }

    public void addMoney(Money money) throws IllegalArgumentException {
        if (money.getNominal() != nominal) {
            throw new IllegalArgumentException("Attempt to put " + money.getNominal()
                    + " money to the " + nominal + " cell");
        }
        amount++;
    }

    public void giveMoney(int amount) {

    }
}
