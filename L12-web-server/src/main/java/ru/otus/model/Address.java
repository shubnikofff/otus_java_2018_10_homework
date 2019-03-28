package ru.otus.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

	Address() {
		super();
	}

	public Address(String street) {
		this.street = street;
	}

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "street")
	private String street;
}
