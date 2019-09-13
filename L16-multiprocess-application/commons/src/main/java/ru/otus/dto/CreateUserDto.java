package ru.otus.dto;

import java.util.List;
import java.util.Optional;

public class CreateUserDto {
	private final String name;
	private final int age;
	private final String address;
	private final List<String> phones;

	public CreateUserDto(String name, Integer age, String address, List<String> phones) {
		this.name = name;
		this.age = Optional.ofNullable(age).orElse(0);
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
