package ru.otus.message;

import java.io.Serializable;

public class SaveUserResponse extends Message {
	final private Serializable userId;

	public SaveUserResponse(int id, String from, String to, Serializable userId) {
		super(id, from, to, SaveUserResponse.class);
		this.userId = userId;
	}

	public Serializable getUserId() {
		return userId;
	}
}
