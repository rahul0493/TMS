package com.quinnox.flm.tms.generic.dao.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.quinnox.flm.tms.generic.dao.GenericDao;

/**
 * @author AmareshP
 *
 */
public class GenericDaoImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDao<T, PK> {

	protected final Log log = LogFactory.getLog(getClass());

	private Class<T> persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	public void wireSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public GenericDaoImpl(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public T get(PK id) {

		T entity = (T) getCurrentSession().get(this.persistentClass, id);
		return entity;
	}

	public void remove(PK id) {
		getCurrentSession().delete(this.get(id));
	}
}
