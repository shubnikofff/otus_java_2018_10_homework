package ru.otus;

class AtmCell {
    private int amount;

    AtmCell(int initialAmount) {
        amount = initialAmount;
    }

    int getAmount() {
        return amount;
    }

    void giveBanknote() {
        if(amount > 0) {
            amount--;
        }
    }

    void acceptBanknote() {
        amount++;
    }
}
