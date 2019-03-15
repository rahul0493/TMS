package com.quinnox.flm.tms.generic.service.impl;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.generic.service.GenericService;

/**
 * @author AmareshP
 *
 * @param <T>
 * @param <PK>
 */
public class GenericServiceImpl<T, PK extends Serializable> implements
		GenericService<T, PK> {

	protected final Log log = LogFactory.getLog(getClass());

	protected GenericDao<T, PK> genericDao;

	public GenericServiceImpl(final GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public T get(PK id) {
		return genericDao.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(PK id) {
		genericDao.remove(id);
	}
}
