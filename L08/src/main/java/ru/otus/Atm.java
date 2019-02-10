package ru.otus;

import ru.otus.collector.*;
import ru.otus.operation.AcceptBanknoteOperation;
import ru.otus.operation.OperationExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm implements Listener {
	private Atm next;
	private Caretaker caretaker;
	private Map<Banknote, AtmCell> state;
	private String name;

	private Atm(String name, Map<Banknote, AtmCell> initialState, Caretaker caretaker) {
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
		System.out.println("Accepting money...");
		OperationExecutor operationExecutor = new OperationExecutor();
		money.forEach(banknote -> operationExecutor.addOperation(new AcceptBanknoteOperation(this, banknote)));
		operationExecutor.executeOperations();
	}

	void giveMoney(int amount) throws ImpossibleCollectAmountException {
		System.out.println("Try to give " + amount + "...");
		FiveEuroCollector fiveEuroCollector = new FiveEuroCollector();
		TenEuroCollector tenEuroCollector = new TenEuroCollector();
		TwentyEuroCollector twentyEuroCollector = new TwentyEuroCollector();
		FiftyEuroCollector fiftyEuroCollector = new FiftyEuroCollector();
		OneHundredEuroCollector oneHundredEuroCollector = new OneHundredEuroCollector();

		oneHundredEuroCollector.setNext(fiftyEuroCollector);
		fiftyEuroCollector.setNext(twentyEuroCollector);
		twentyEuroCollector.setNext(tenEuroCollector);
		tenEuroCollector.setNext(fiveEuroCollector);

		OperationExecutor operationExecutor = new OperationExecutor();
		oneHundredEuroCollector.collect(amount, this, operationExecutor);
		operationExecutor.executeOperations();
	}

	void putBalance(Balance balance) {
		balance.put(name, getBalance());
		if (next != null) {
			next.putBalance(balance);
		}
	}

	public int getBanknoteAmount(Banknote banknote) {
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
		private Map<Banknote, AtmCell> initialState = new HashMap<>();
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
