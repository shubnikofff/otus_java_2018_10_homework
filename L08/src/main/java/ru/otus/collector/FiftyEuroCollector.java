package ru.otus.collector;

import ru.otus.Banknote;

public class FiftyEuroCollector extends BanknoteCollector{
	@Override
	protected Banknote getBanknote() {
		return Banknote.FiftyEuro;
	}
}
