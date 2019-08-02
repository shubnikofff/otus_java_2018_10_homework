package ru.otus.message;

public class SaveUserRequest extends Message {
	public SaveUserRequest(String from) {
		super(from, null, SaveUserRequest.class);
	}
}
