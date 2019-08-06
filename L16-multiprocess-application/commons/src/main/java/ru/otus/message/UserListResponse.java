package ru.otus.message;

public class UserListResponse extends Message {
	public UserListResponse(String from, String to) {
		super(from, to, UserListResponse.class);
	}
}
