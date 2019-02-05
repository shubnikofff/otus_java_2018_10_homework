package ru.otus;

import ru.otus.operation.AcceptBanknoteOperation;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm {
	private Map<Banknote, AtmCell> state;
	private String name;
	private Atm next;

	private Atm(String name, Map<Banknote, AtmCell> initialState) {
		this.name = name;
		state = initialState;
	}

	void setNext(Atm next) {
		this.next = next;
	}

	int getBalance() {
		int balance = 0;

		for (Map.Entry<Banknote, AtmCell> entry : state.entrySet()) {
			balance += entry.getKey().getNominal() * entry.getValue().getAmount();
		}

		return balance;
	}

	public void acceptBanknote(Banknote banknote) {
		if (state.containsKey(banknote)) {
			state.get(banknote).acceptBanknote();
		}
	}

	void acceptMoney(List<Banknote> money) {
		OperationExecutor operationExecutor = new OperationExecutor();
		money.forEach(banknote -> operationExecutor.addOperation(new AcceptBanknoteOperation(this, banknote)));
		operationExecutor.executeOperations();
	}

	void putBalance(Balance balance) {
		balance.put(name, getBalance());
		if (next != null) {
			next.putBalance(balance);
		}
	}

	boolean has(Banknote nominal, int amount) {
		return state.get(nominal).getAmount() >= amount;
	}

	static class Builder {
		private Map<Banknote, AtmCell> initialState = new HashMap<>();

		private String atmName;

		Builder(String atmName) {
			this.atmName = atmName;
		}

		Builder withFiveEuro(int amount) {
			initialState.put(Banknote.FiveEuro, new AtmCell(amount));
			return this;
		}

		Builder withTenEuro(int amount) {
			initialState.put(Banknote.TenEuro, new AtmCell(amount));
			return this;
		}

		Builder withTwentyEuro(int amount) {
			initialState.put(Banknote.TwentyEuro, new AtmCell(amount));
			return this;
		}

		Builder withFiftyEuro(int amount) {
			initialState.put(Banknote.FiftyEuro, new AtmCell(amount));
			return this;
		}

		Builder withOneHundredEuro(int amount) {
			initialState.put(Banknote.OneHundredEuro, new AtmCell(amount));
			return this;
		}

		Atm build() {
			return new Atm(atmName, initialState);
		}
	}
}
