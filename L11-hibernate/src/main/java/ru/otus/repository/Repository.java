package ru.otus.repository;

import org.hibernate.Session;
import ru.otus.executor.Executor;

public class Repository<T> implements Executor<T>, AutoCloseable {
	private final Session session;

	public Repository(RepositoryConfiguration configuration) {
		session = configuration.getSessionFactory().openSession();
	}

	@Override
	public <T> T load(long id, Class<T> clazz) {
		return session.get(clazz, id);
	}

	@Override
	public void save(T object) {
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}

	@Override
	public void close() {
		session.close();
	}
}
