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
		amount--;
	}

	void acceptBanknote() {
		amount++;
	}
}
