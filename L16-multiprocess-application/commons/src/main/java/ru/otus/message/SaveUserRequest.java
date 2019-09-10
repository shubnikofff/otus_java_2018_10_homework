package ru.otus.message;

import ru.otus.dto.CreateUserDto;

public class SaveUserRequest extends Message {
	private final CreateUserDto dto;

	public SaveUserRequest(int id, String from, CreateUserDto dto) {
		super(id, from, null, SaveUserRequest.class);
		this.dto = dto;
	}

	public CreateUserDto getDto() {
		return dto;
	}
}
