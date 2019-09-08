package ru.otus.dto;

import java.util.List;

public class UserDto {
	private final long id;
	private final String name;
	private final int age;
	private final String address;
	private final List<String> phones;

	public UserDto(long id, String name, int age, String address, List<String> phones) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.phones = phones;
	}

	public long getId() {
		return id;
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
