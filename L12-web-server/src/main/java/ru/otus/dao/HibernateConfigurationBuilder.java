package ru.otus.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateConfigurationBuilder {
	private StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
	private MetadataSources metadataSources;

	public static HibernateConfigurationBuilder builder() {
		return new HibernateConfigurationBuilder();
	}

	public HibernateConfigurationBuilder setXmlConfig(String xmlConfig) {
		Configuration configuration = new Configuration().configure(xmlConfig);
		standardServiceRegistryBuilder.applySettings(configuration.getProperties());
		return this;
	}

	public HibernateConfigurationBuilder addAnnotatedClasses(Class... classes) {
		metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
		for (Class clazz : classes) {
			metadataSources.addAnnotatedClass(clazz);
		}
		return this;
	}

	public SessionFactory getSessionFactory() {
		return metadataSources.buildMetadata().buildSessionFactory();
	}
}
