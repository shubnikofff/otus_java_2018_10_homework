package ru.otus.message;

public class SaveUserResponse extends Message {
	private final long userId;

	public SaveUserResponse(int id, String from, String to, long userId) {
		super(id, from, to, SaveUserResponse.class);
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}
}
