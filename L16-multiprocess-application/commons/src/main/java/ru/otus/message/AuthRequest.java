package ru.otus.message;

public class AuthRequest extends Message {
	private final String username;
	private final String password;

	public AuthRequest(int id, String from, String username, String password) {
		super(id, from, null, AuthRequest.class);
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
