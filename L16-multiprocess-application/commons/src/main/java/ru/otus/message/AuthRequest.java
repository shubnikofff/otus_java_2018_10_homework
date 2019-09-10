package ru.otus.message;

import ru.otus.dto.LoginDto;

public class AuthRequest extends Message {
	private final LoginDto dto;

	public AuthRequest(int id, String from, LoginDto dto) {
		super(id, from, null, AuthRequest.class);
		this.dto = dto;
	}

	public LoginDto getDto() {
		return dto;
	}
}
