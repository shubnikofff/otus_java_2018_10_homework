package ru.otus;

import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class HibernateDemo {
	private final String HIBERNATE_CONFIGURATION = "hibernate.cfg.xml";

	public static void main(String[] args) {
		try(Session session = new HibernateDemo().getSessionFactory().openSession()) {
			Gson gson = new Gson();
			Executor<User> executor = new Executor<>(session);

			Address address = new Address("Moscow, Kremlin");
			ArrayList<Phone> phones = new ArrayList<>();
			phones.add(new Phone("111-111-111"));
			phones.add(new Phone("222-222-222"));
			User vladimir = new User("Vladimir", 66, address, phones);

			System.out.println("\nBefore save: " + gson.toJson(vladimir) + "\n");
			executor.save(vladimir);
			System.out.println("\nAfter save: " + gson.toJson(vladimir) + "\n");

			User loadedVladimir = executor.load(vladimir.getId(), User.class);
			System.out.println("\nAfter load: " + gson.toJson(loadedVladimir) + "\n");
			loadedVladimir.getPhones().add(new Phone("333-333-333"));
			loadedVladimir.getPhones().remove(0);
			loadedVladimir.setAddress(new Address("St. Petersburg"));
			executor.save(loadedVladimir);
			System.out.println("\nAfter update: " + gson.toJson(loadedVladimir) + "\n");
		}
	}

	private SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure(HIBERNATE_CONFIGURATION);
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return new MetadataSources(serviceRegistry)
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Address.class)
				.addAnnotatedClass(Phone.class)
				.buildMetadata()
				.buildSessionFactory();
	}
}
