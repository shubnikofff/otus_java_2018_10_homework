package ru.otus.collector;

import ru.otus.Banknote;

public class OneHundredEuroCollector extends BanknoteCollector{
	@Override
	protected Banknote getBanknote() {
		return Banknote.OneHundredEuro;
	}
}
