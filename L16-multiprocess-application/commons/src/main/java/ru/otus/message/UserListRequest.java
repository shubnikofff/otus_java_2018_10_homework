package ru.otus.message;

public class UserListRequest extends Message {
	public UserListRequest(int id, String from) {
		super(id, from, null, UserListRequest.class);
	}
}
