package com.quinnox.flm.tms.module.dao.impl;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.module.dao.ProjectDetailsDao;
import com.quinnox.flm.tms.module.dao.ShiftDetailsDao;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;

@Repository("shiftDetailsDao")
public class ShiftDetailsDaoImpl extends GenericDaoImpl<ShiftDetails, Integer> implements ShiftDetailsDao {
	
	public ShiftDetailsDaoImpl() {
		super(ShiftDetails.class);
	}

	
	@Override
	public void saveShiftDetails(ShiftDetails shiftDetails) {
		getCurrentSession().saveOrUpdate(shiftDetails);
	}

	
	@Override
	@Transactional
	public void updateShiftDetails(Shift shift) {
		getCurrentSession().update(shift);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Shift> findAllShift() {
		return (List<Shift>) getCurrentSession().createQuery("from Shift")
				.list();
	}

	@Override
	public void saveShiftDetails(Shift shift) {
		// TODO Auto-generated method stub
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ShiftDetails> findByTrackId(Integer id) {
		return  (List<ShiftDetails>) getCurrentSession().createQuery("from ShiftDetails sf where sf.track.trackId=:id").setInteger("id", id)
				.list();
	}


	@Override
	@Transactional
	public void updateAllowance(Shift shift) {
		getCurrentSession().update(shift);
		
	}


	@Override
	@Transactional
	public void deleteShiftDetailsById(Integer id) {
		ShiftDetails model = getCurrentSession().get(ShiftDetails.class, id);
		getCurrentSession().delete(model);
		
	}

}
