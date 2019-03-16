package ru.otus;

import javax.persistence.*;

@Entity
@Table(name = "address")
class Address {

	Address() {
		super();
	}

	Address(String street) {
		this.street = street;
	}

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "street")
	private String street;
}
