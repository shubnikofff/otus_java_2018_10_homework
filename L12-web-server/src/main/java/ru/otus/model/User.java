package ru.otus.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

	public User(String name, int age, Address address, List<Phone> phones) {
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
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true
	)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "user",
			orphanRemoval = true
	)
	private List<Phone> phones = new ArrayList<>();

	public long getId() {
		return id;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
