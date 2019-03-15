package ru.otus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "phones", nullable = false)
	private List<Phone> phones;
}
