package ru.otus;

import java.util.HashMap;
import java.util.Map;

public class Atm {
	private Map<BanknoteNominal, AtmCell> state;

	private Atm(Map<BanknoteNominal, AtmCell> initialState) {
		state = initialState;
	}

	static class Builder {
		private Map<BanknoteNominal, AtmCell> initialState = new HashMap<>();

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
			return new Atm(initialState);
		}
	}

	int getBalance() {
		int balance = 0;

		for (Map.Entry<BanknoteNominal, AtmCell> entry : state.entrySet()) {
			balance += entry.getKey().getNominal() * entry.getValue().getAmount();
		}

		return balance;
	}
}
