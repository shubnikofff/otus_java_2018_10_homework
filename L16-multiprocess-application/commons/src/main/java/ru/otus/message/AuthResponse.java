package ru.otus.message;

public class AuthResponse extends Message {
	private final boolean authorized;

	public AuthResponse(int id, String from, String to, boolean authorized) {
		super(id, from, to, AuthResponse.class);
		this.authorized = authorized;
	}

	public boolean isAuthorized() {
		return authorized;
	}
}
