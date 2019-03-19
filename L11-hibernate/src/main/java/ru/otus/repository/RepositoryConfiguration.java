package ru.otus.repository;

import org.hibernate.SessionFactory;

public interface RepositoryConfiguration {
	SessionFactory getSessionFactory();
}
