package ru.otus.collector;

import ru.otus.Banknote;

public class FiveEuroCollector extends BanknoteCollector {
	@Override
	protected Banknote getBanknote() {
		return Banknote.FiveEuro;
	}
}
