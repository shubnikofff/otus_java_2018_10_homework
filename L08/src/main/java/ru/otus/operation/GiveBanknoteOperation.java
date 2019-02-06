package ru.otus.operation;

import ru.otus.Atm;
import ru.otus.Banknote;

public class GiveBanknoteOperation implements Operation {
	private Atm atm;
	private Banknote banknote;

	public GiveBanknoteOperation(Atm atm, Banknote banknote) {
		this.atm = atm;
		this.banknote = banknote;
	}

	@Override
	public void execute() {
		atm.giveBanknote(banknote);
	}
}
