package ru.otus.message;

public class SaveUserRequest extends Message {
	public SaveUserRequest(int id, String from) {
		super(id, from, null, SaveUserRequest.class);
	}
}
