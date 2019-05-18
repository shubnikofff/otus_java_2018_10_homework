package ru.otus.messageSystem;

public abstract class Message {
	private final int id;
	private final Address from;
	private final Address to;

	public Message(int id, Address from, Address to) {
		this.id = id;
		this.from = from;
		this.to = to;
	}

	public int getId() {
		return id;
	}

	public Address getFrom() {
		return from;
	}

	public Address getTo() {
		return to;
	}

	public abstract void exec(Addressee addressee);
}
