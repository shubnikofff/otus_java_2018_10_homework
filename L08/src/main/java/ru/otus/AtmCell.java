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

	Memento saveState() {
		return new Memento(amount);
	}

	void restoreState(Memento memento) {
		amount = memento.getState();
	}
}
