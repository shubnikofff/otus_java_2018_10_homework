package ru.otus;

class MoneyCell {
    private int amount = 0;

    void putMoney() {
        amount++;
    }

    void giveMoney(int amount) throws Exception {
        if (this.amount == 0) {
            throw new Exception("Cell is empty");
        }
        this.amount -= amount;
    }

    int getAmount() {
        return amount;
    }
}
