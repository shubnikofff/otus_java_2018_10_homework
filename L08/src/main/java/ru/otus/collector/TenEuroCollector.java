package ru.otus.collector;

import ru.otus.Banknote;

public class TenEuroCollector extends BanknoteCollector{
	@Override
	protected Banknote getBanknote() {
		return Banknote.TenEuro;
	}
}
