package ru.otus;

import java.util.ArrayList;
import java.util.List;

class Department {
	private List<Atm> atmList;
	private Resetter resetter = new Resetter();

	Department(int initialCapacity) {
		atmList = new ArrayList<>(initialCapacity);
	}

	void addAtm(Atm atm) {
		if (!atmList.isEmpty()) {
			atmList.get(atmList.size() - 1).setNext(atm);
		}
		atmList.add(atm);
		resetter.addHandler(atm);
	}

	void resetAtm() {
		System.out.println("\nReset all atm to initial state...");
		resetter.apply();
	}

	Balance getBalance() {
		Balance balance = new Balance(atmList.size());
		atmList.get(0).putBalance(balance);
		return balance;
	}
}
