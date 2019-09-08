package ru.otus.web.form;

import java.util.List;

public class CreateUserForm {
	private final String name;
	private final String age;
	private final String address;
	private final List<String> phones;

	public CreateUserForm(String name, String age, String address, List<String> phones) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getPhones() {
		return phones;
	}
}
