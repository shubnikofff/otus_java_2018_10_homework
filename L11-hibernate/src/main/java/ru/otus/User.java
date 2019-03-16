package ru.otus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
class User {

	User(String name, int age, Address address, List<Phone> phones) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.phones = phones;
	}

	User() {
		super();
	}

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private List<Phone> phones;

	long getId() {
		return id;
	}
}
