package ru.otus;

import org.hibernate.Session;

class HibernateExecutor<T> {
	private Session session;

	HibernateExecutor(Session session) {
		this.session = session;
	}

	<T> T load(long id, Class<T> clazz) {
		return session.get(clazz, id);
	}

	void save(T object) {
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
	}
}
