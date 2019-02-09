package ru.otus;

import java.util.ArrayList;
import java.util.List;

class Resetter {
	List<Listener> listeners = new ArrayList<>();

	void addListener(Listener listener) {
		listeners.add(listener);
	}

	void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	void apply() {
		listeners.forEach(Listener::onReset);
	}
}
