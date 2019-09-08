package ru.otus.message;

public class OperationSuccess extends Message {
	public OperationSuccess(int id, String from, String to) {
		super(id, from, to, OperationSuccess.class);
	}
}
