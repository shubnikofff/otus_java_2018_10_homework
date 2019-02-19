package ru.otus;

public class Person {
	private String name;
	private int age;
	private String address;
	private boolean isMarried;

	Person(String name, int age, String address, boolean isMarried) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.isMarried = isMarried;
	}

	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;

		Person person = (Person) object;
		return name.equals(person.name)
				&& age == person.age
				&& address.equals(person.address)
				&& isMarried == person.isMarried;
	}
}
