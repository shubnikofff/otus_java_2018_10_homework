package ru.otus.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone")
public class Phone implements Serializable {

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

	public String getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return  number;
	}
}
