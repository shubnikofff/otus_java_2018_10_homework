package ru.otus;

import ru.otus.operation.GiveBanknoteOperation;
import ru.otus.operation.OperationExecutor;

public enum Banknote {
	FiveEuro(5),
	TenEuro(10),
	TwentyEuro(20),
	FiftyEuro(50),
	OneHundredEuro(100);

	private int nominal;
	private Banknote next;

	Banknote(int nominal) {
		this.nominal = nominal;
	}

	public int getNominal() {
		return nominal;
	}

	public void setNext(Banknote next) {
		this.next = next;
	}

	public void collect(int amount, Atm atm, OperationExecutor operationExecutor) throws ImpossibleCollectAmountException {
		int remainAmount = amount;
		int banknoteAmount = atm.getBanknoteAmount(this);

		while (banknoteAmount > 0 && remainAmount >= nominal) {
			operationExecutor.addOperation(new GiveBanknoteOperation(atm, this));
			banknoteAmount--;
			remainAmount -= nominal;
		}

		if (remainAmount == 0) {
			System.out.println("Amount collected!");
			return;
		}

		if (next != null) {
			next.collect(remainAmount, atm, operationExecutor);
		} else {
			throw new ImpossibleCollectAmountException("Amount can't be collect :(");
		}
	}
}
