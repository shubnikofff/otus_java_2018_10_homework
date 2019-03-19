package ru.otus.model;

import ru.otus.model.User;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Phone(String number) {
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
