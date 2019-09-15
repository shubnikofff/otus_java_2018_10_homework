package ru.otus.dao;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class HibernateDao<T> implements Dao<T> {
	private Session session;

	HibernateDao(Session session) {
		this.session = session;
	}

	@Override
	public <T> T load(Serializable id, Class<T> clazz) {
		return session.get(clazz, id);
	}
	@Override
	public List<T> getAll(Class<T> clazz) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);
		return session.createQuery(criteriaQuery.select(root)).getResultList();
	}

	@Override
	public Serializable save(T object) {
		Serializable id = null;

		try {
			session.beginTransaction();
			id = session.save(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}

		return id;
	}
}
