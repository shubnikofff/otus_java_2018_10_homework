package ru.otus.executor;

import org.hibernate.Session;

public class HibernateExecutor<T> implements Executor<T> {
	private Session session;

	HibernateExecutor(Session session) {
		this.session = session;
	}

	public <T> T load(long id, Class<T> clazz) {
		return session.get(clazz, id);
	}

	public void save(T object) {
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
	}
}
