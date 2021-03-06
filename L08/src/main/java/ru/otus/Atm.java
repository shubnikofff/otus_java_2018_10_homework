package ru.otus;

import ru.otus.operation.AcceptBanknoteOperation;
import ru.otus.operation.OperationExecutor;

import java.util.*;

public class Atm implements ResetListener {
	private Atm next;
	private Caretaker caretaker;
	private TreeMap<Banknote, AtmCell> state;
	private String name;

	private Atm(String name, TreeMap<Banknote, AtmCell> initialState, Caretaker caretaker) {
		this.name = name;
		state = initialState;
		this.caretaker = caretaker;
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

	public void giveBanknote(Banknote banknote) {
		AtmCell atmCell = state.get(banknote);
		if (atmCell != null && atmCell.getAmount() > 0) {
			atmCell.giveBanknote();
		}
	}

	void acceptMoney(List<Banknote> money) {
		System.out.println(name + ": accepting money...");
		OperationExecutor operationExecutor = new OperationExecutor();
		money.forEach(banknote -> operationExecutor.addOperation(new AcceptBanknoteOperation(this, banknote)));
		operationExecutor.executeOperations();
	}

	void giveMoney(int amount) throws ImpossibleCollectAmountException {
		System.out.println(name + ": try to give " + amount + "...");

		Iterator<Map.Entry<Banknote, AtmCell>> iterator = state.entrySet().iterator();
		Banknote banknote = state.firstKey();

		while (iterator.hasNext()) {
			Banknote next = iterator.next().getKey();
			banknote.setNext(next);
			banknote = next;
		}

		OperationExecutor operationExecutor = new OperationExecutor();
		state.firstKey().collect(amount, this, operationExecutor);
		operationExecutor.executeOperations();
	}

	void putBalance(Balance balance) {
		balance.put(name, getBalance());
		if (next != null) {
			next.putBalance(balance);
		}
	}

	int getBanknoteAmount(Banknote banknote) {
		if (!state.containsKey(banknote)) {
			return 0;
		}
		return state.get(banknote).getAmount();
	}

	void printBalance() {
		System.out.println(name + ": " + getBalance());
	}

	@Override
	public void onReset() {
		state.forEach((banknote, atmCell) -> atmCell.restoreState(caretaker.getMemento(atmCell)));
	}

	static class Builder {
		private TreeMap<Banknote, AtmCell> initialState = new TreeMap<>((a, b) -> b.getNominal() - a.getNominal());
		private Caretaker caretaker = new Caretaker();

		private String atmName;

		Builder(String atmName) {
			this.atmName = atmName;
		}

		Builder withFiveEuro(int amount) {
			AtmCell atmCell = new AtmCell(amount);
			initialState.put(Banknote.FiveEuro, atmCell);
			caretaker.putMemento(atmCell, atmCell.saveState());
			return this;
		}

		Builder withTenEuro(int amount) {
			AtmCell atmCell = new AtmCell(amount);
			initialState.put(Banknote.TenEuro, atmCell);
			caretaker.putMemento(atmCell, atmCell.saveState());
			return this;
		}

		Builder withTwentyEuro(int amount) {
			AtmCell atmCell = new AtmCell(amount);
			initialState.put(Banknote.TwentyEuro, atmCell);
			caretaker.putMemento(atmCell, atmCell.saveState());
			return this;
		}

		Builder withFiftyEuro(int amount) {
			AtmCell atmCell = new AtmCell(amount);
			initialState.put(Banknote.FiftyEuro, atmCell);
			caretaker.putMemento(atmCell, atmCell.saveState());
			return this;
		}

		Builder withOneHundredEuro(int amount) {
			AtmCell atmCell = new AtmCell(amount);
			initialState.put(Banknote.OneHundredEuro, atmCell);
			caretaker.putMemento(atmCell, atmCell.saveState());
			return this;
		}

		Atm build() {
			return new Atm(atmName, initialState, caretaker);
		}
	}
}
