package ru.otus.message;

import ru.otus.dto.UserDto;

public class SaveUserRequest extends Message {
	private final UserDto userDto;

	public SaveUserRequest(int id, String from, UserDto userDto) {
		super(id, from, null, SaveUserRequest.class);
		this.userDto = userDto;
	}

	public UserDto getUserDto() {
		return userDto;
	}
}
