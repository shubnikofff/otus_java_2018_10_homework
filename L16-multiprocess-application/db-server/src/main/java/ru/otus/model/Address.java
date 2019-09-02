package ru.otus.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {

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

	@Override
	public String toString() {
		return street;
	}
}
