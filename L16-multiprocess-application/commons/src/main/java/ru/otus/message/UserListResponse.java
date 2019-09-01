package ru.otus.message;

public class UserListResponse extends Message {
	public UserListResponse(int id, String from, String to) {
		super(id, from, to, UserListResponse.class);
	}
}
