package ru.otus;

import java.util.ArrayList;

public class HibernateDemo {
	public static void main(String[] args) {
		Executor<User> executor = new Executor<>("hibernate.cfg.xml");

		Address address = new Address("Moscow, Kremlin");
		ArrayList<Phone> phones = new ArrayList<>();
		phones.add(new Phone("111-111-111"));
		phones.add(new Phone("222-222-222"));
		User vladimir = new User("Vladimir", 66, address, phones);

		executor.save(vladimir);

		User loadedVladimir = executor.load(vladimir.getId(), User.class);
		System.out.println(loadedVladimir);
	}
}
