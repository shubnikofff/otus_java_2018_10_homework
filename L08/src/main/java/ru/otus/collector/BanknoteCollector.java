package ru.otus.collector;

import ru.otus.Atm;
import ru.otus.Banknote;
import ru.otus.ImpossibleCollectAmountException;
import ru.otus.operation.GiveBanknoteOperation;
import ru.otus.operation.OperationExecutor;

abstract public class BanknoteCollector {
	private BanknoteCollector next;

	public void setNext(BanknoteCollector next) {
		this.next = next;
	}

	public void collect(int amount, Atm atm, OperationExecutor operationExecutor) throws ImpossibleCollectAmountException {
		int remainAmount = amount;
		Banknote banknote = getBanknote();
		int banknoteNominal = banknote.getNominal();
		int banknoteAmount = atm.getBanknoteAmount(banknote);

		while (banknoteAmount > 0 && remainAmount >= banknoteNominal) {
			operationExecutor.addOperation(new GiveBanknoteOperation(atm, banknote));
			banknoteAmount--;
			remainAmount -= banknoteNominal;
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

	abstract protected Banknote getBanknote();
}
