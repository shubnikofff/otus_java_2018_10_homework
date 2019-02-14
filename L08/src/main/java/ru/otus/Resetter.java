package ru.otus;

import java.util.ArrayList;
import java.util.List;

class Resetter {
	List<ResetListener> listeners = new ArrayList<>();

	void addListener(ResetListener resetListener) {
		listeners.add(resetListener);
	}

	void removeListener(ResetListener resetListener) {
		listeners.remove(resetListener);
	}

	void apply() {
		listeners.forEach(ResetListener::onReset);
	}
}
