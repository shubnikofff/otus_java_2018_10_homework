package ru.otus;

class Memento {
	final private int state;

	Memento(int state) {
		this.state = state;
	}

	int getState() {
		return state;
	}
}
