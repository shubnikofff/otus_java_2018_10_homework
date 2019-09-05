package ru.otus.dto;

import java.util.List;

public class UserDto {
	private final String name;
	private final int age;
	private final String address;
	private final List<String> phones;

	public UserDto(String name, int age, String address, List<String> phones) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getPhones() {
		return phones;
	}
}
