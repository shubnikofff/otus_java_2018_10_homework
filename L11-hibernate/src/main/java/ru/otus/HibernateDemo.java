package ru.otus;

import com.google.gson.Gson;
import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.repository.HibernateConfigurationBuilder;
import ru.otus.repository.Repository;
import ru.otus.repository.RepositoryConfiguration;

import java.util.ArrayList;

public class HibernateDemo {
	public static void main(String[] args) {
		RepositoryConfiguration configuration = HibernateConfigurationBuilder
				.builder()
				.setXmlConfig("hibernate.cfg.xml")
				.addAnnotatedClasses(User.class, Address.class, Phone.class);
		Gson gson = new Gson();

		try (Repository<User> repository = new Repository<>(configuration)) {
			Address address = new Address("Moscow, Kremlin");
			ArrayList<Phone> phones = new ArrayList<>();
			phones.add(new Phone("111-111-111"));
			phones.add(new Phone("222-222-222"));
			User vladimir = new User("Vladimir", 66, address, phones);

			System.out.println("\nBefore save: " + gson.toJson(vladimir) + "\n");
			repository.save(vladimir);
			System.out.println("\nAfter save: " + gson.toJson(vladimir) + "\n");

			User loadedVladimir = repository.load(vladimir.getId(), User.class);
			System.out.println("\nAfter load: " + gson.toJson(loadedVladimir) + "\n");
			loadedVladimir.getPhones().add(new Phone("333-333-333"));
			loadedVladimir.getPhones().remove(0);
			loadedVladimir.setAddress(new Address("St. Petersburg"));
			repository.save(loadedVladimir);
			System.out.println("\nAfter update: " + gson.toJson(loadedVladimir) + "\n");
		}
	}
}
