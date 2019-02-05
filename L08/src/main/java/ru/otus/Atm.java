package ru.otus;

import java.util.HashMap;
import java.util.Map;

public class Atm {
	private Map<BanknoteNominal, AtmCell> state;
	private String name;
	private Atm next;

	private Atm(String name, Map<BanknoteNominal, AtmCell> initialState) {
		this.name = name;
		state = initialState;
	}

	void putBalance(Balance balance) {
		balance.put(name, getBalance());
		if (next != null) {
			next.putBalance(balance);
		}
	}

	public void setNext(Atm next) {
		this.next = next;
	}

	int getBalance() {
		int balance = 0;

		for (Map.Entry<BanknoteNominal, AtmCell> entry : state.entrySet()) {
			balance += entry.getKey().getNominal() * entry.getValue().getAmount();
		}

		return balance;
	}

	static class Builder {
		private Map<BanknoteNominal, AtmCell> initialState = new HashMap<>();

		private String atmName;

		public Builder(String atmName) {
			this.atmName = atmName;
		}

		public Builder withFiveEuro(int amount) {
			initialState.put(BanknoteNominal.FiveEuro, new AtmCell(amount));
			return this;
		}

		public Builder withTenEuro(int amount) {
			initialState.put(BanknoteNominal.TenEuro, new AtmCell(amount));
			return this;
		}

		public Builder withTwentyEuro(int amount) {
			initialState.put(BanknoteNominal.TwentyEuro, new AtmCell(amount));
			return this;
		}

		public Builder withFiftyEuro(int amount) {
			initialState.put(BanknoteNominal.FiftyEuro, new AtmCell(amount));
			return this;
		}

		public Builder withOneHundredEuro(int amount) {
			initialState.put(BanknoteNominal.OneHundredEuro, new AtmCell(amount));
			return this;
		}

		public Atm build() {
			return new Atm(atmName, initialState);
		}
	}
}
