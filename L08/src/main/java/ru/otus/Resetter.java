package ru.otus;

import java.util.ArrayList;
import java.util.List;

class Resetter {
	List<ResetHandler> resetHandlers = new ArrayList<>();

	void addHandler(ResetHandler resetHandler) {
		resetHandlers.add(resetHandler);
	}

	void removeHandler(ResetHandler resetHandler) {
		resetHandlers.remove(resetHandler);
	}

	void apply() {
		resetHandlers.forEach(ResetHandler::onReset);
	}
}
