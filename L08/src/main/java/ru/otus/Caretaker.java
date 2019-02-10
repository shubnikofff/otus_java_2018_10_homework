package ru.otus;

import java.util.HashMap;
import java.util.Map;

class Caretaker {
	private Map<AtmCell, Memento> mementoMap = new HashMap<>();

	Memento getMemento(AtmCell atmCell) {
		return mementoMap.get(atmCell);
	}

	void putMemento(AtmCell atmCell, Memento memento) {
		mementoMap.put(atmCell, memento);
	}
}
