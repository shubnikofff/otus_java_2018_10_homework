package ru.otus;

import javax.persistence.*;

@Entity
@Table(name = "phone")
class Phone {

	Phone(String number) {
		this.number = number;
	}

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "number")
	private String number;
}
