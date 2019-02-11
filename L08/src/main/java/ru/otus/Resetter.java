package ru.otus;

import java.util.ArrayList;
import java.util.List;

class Resetter {
	List<ResetHandler> resetHandlers = new ArrayList<>();

	void addListener(ResetHandler resetHandler) {
		resetHandlers.add(resetHandler);
	}

	void removeListener(ResetHandler resetHandler) {
		resetHandlers.remove(resetHandler);
	}

	void apply() {
		resetHandlers.forEach(ResetHandler::onReset);
	}
}
