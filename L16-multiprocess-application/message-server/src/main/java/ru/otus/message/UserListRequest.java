package ru.otus.message;

public class UserListRequest extends Message {
	public UserListRequest(String from) {
		super(from, null, UserListRequest.class);
	}
}
