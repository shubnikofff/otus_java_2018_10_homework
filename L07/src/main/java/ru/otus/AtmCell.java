package ru.otus;

public class AtmCell {
    private int amount = 0;

    public void put() {
        amount++;
    }

    public void pop() throws Exception {
        if (amount == 0) {
            throw new Exception("Cell is empty");
        }
        amount--;
    }

    int getAmount() {
        return amount;
    }
}
