package ru.otus.dao;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateDaoFactory {
	private MetadataSources metadataSources;

	public HibernateDaoFactory(String configPath) {
		Configuration configuration = new Configuration().configure(configPath);
		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		standardServiceRegistryBuilder.applySettings(configuration.getProperties());
		metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
	}

	public HibernateDao getDao(Class... classes) {
		for (Class clazz : classes) {
			metadataSources.addAnnotatedClass(clazz);
		}

		final Session session = metadataSources.buildMetadata().buildSessionFactory().openSession();
		return new HibernateDao(session);
	}
}
