package ru.otus.collector;

import ru.otus.Banknote;

public class TwentyEuroCollector extends BanknoteCollector {
	@Override
	protected Banknote getBanknote() {
		return Banknote.TwentyEuro;
	}
}
