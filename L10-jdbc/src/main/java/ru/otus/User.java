package ru.otus;

class User {
	@Id
	private long id;
	private String name;
	private int age;

	public User() {}

	User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	long getId() {
		return id;
	}

	void setName(String name) {
		this.name = name;
	}

	void setAge(int age) {
		this.age = age;
	}
}
