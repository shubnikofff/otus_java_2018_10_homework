package ru.otus.dto;

import java.util.List;

public class UserDto {
	private final String name;
	private final String address;
	private final List<String> phones;

	public UserDto(String name, String address, List<String> phones) {
		this.name = name;
		this.address = address;
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getPhones() {
		return phones;
	}
}
