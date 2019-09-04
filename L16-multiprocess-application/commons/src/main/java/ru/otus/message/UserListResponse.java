package ru.otus.message;

import ru.otus.dto.UserDto;

import java.util.List;

public class UserListResponse extends Message {
	private final List<UserDto> userDtoList;

	public UserListResponse(int id, String from, String to, List<UserDto> userDtoList) {
		super(id, from, to, UserListResponse.class);
		this.userDtoList = userDtoList;
	}

	public List<UserDto> getUserDtoList() {
		return userDtoList;
	}
}
