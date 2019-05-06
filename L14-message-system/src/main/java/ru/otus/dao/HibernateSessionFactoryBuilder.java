package ru.otus.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryBuilder {
	private MetadataSources metadataSources;

	public HibernateSessionFactoryBuilder(String configPath) {
		Configuration configuration = new Configuration().configure(configPath);
		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		standardServiceRegistryBuilder.applySettings(configuration.getProperties());
		metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
	}

	public HibernateSessionFactoryBuilder withAnnotatedClasses(Class... classes) {
		for (Class clazz : classes) {
			metadataSources.addAnnotatedClass(clazz);
		}

		return this;
	}

	public SessionFactory build() {
		return metadataSources.buildMetadata().buildSessionFactory();
	}
}
