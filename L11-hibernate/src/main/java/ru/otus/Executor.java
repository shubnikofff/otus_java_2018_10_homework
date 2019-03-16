package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

class Executor<T> {

	private final SessionFactory sessionFactory;

	Executor(String hibernateConfiguration) {
		Configuration configuration = new Configuration().configure(hibernateConfiguration);

		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		Metadata metadata = new MetadataSources(serviceRegistry)
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Address.class)
				.addAnnotatedClass(Phone.class)
				.buildMetadata();

		sessionFactory = metadata.buildSessionFactory();
	}

	<T> T load(long id, Class<T> clazz) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(clazz, id);
		}
	}

	void save(T object) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		}
	}
}
