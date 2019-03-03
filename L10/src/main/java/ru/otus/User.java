package ru.otus;

class User {
	@Id
	private long id;
	private String name;
	private int age;

	User(long id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
