package ru.otus;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "address")
class Address {

	@Column(name = "street")
	private String street;
}
