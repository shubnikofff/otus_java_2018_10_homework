package ru.otus;

import java.util.HashMap;
import java.util.Map;

public class Balance {
	private Map<String, Integer> balanceMap;

	public Balance(int initialCapacity) {
		balanceMap = new HashMap<>(initialCapacity);
	}

	void put(String atmName, Integer balance) {
		balanceMap.put(atmName, balance);
	}

	int getTotal() {
		int balance = 0;

		for(Map.Entry<String, Integer> entry: balanceMap.entrySet()) {
			balance += entry.getValue();
		}

		return balance;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("Balance:\n=========\n");
		balanceMap.forEach((atmName, balance) -> {
			stringBuilder.append(atmName);
			stringBuilder.append(": ");
			stringBuilder.append(balance);
			stringBuilder.append("\n");
		});
		stringBuilder.append("========\nTotal: ");
		stringBuilder.append(getTotal());
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
}
