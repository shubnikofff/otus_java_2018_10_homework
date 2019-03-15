package ru.otus;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "phone")
class Phone {

	@Column(name = "number")
	private String number;
}
