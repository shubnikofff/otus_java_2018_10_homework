package ru.otus;

import javax.persistence.*;

@Entity
@Table(name = "phone")
class Phone {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	Phone(String number) {
		this.number = number;
	}

	Phone() {
		super();
	}

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "number")
	private String number;
}
