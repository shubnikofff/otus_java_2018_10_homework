package ru.otus;

public class AtmCell {
    private int amount;

    public AtmCell(int initialAmount) {
        amount = initialAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
