package com.quinnox.flm.tms.module.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.dao.ShiftDetailsDao;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.service.ShiftDetailsService;

@Service
public class ShiftDetailsServiceImpl extends GenericServiceImpl<ShiftDetails, Integer> implements ShiftDetailsService {
	private ShiftDetailsDao shiftDetailsDao;
	@Autowired
	public ShiftDetailsServiceImpl(
			@Qualifier("shiftDetailsDao") ShiftDetailsDao shiftDetailsDao) {
		super(shiftDetailsDao);
		this.shiftDetailsDao = shiftDetailsDao;
	}
	@Override
	public void saveShiftDetails(Shift shift) {
		shiftDetailsDao.saveShiftDetails(shift);
	}

	@Override
	public void updateShiftDetails(Shift shift) {
		shiftDetailsDao.updateShiftDetails(shift);
	}
	

	@Override
	public List<Shift> findAllShift() {
		return shiftDetailsDao.findAllShift();
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveShiftDetails(ShiftDetails shiftDetails) {
		shiftDetailsDao.saveShiftDetails(shiftDetails);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ShiftDetailsBean> findByTrackId(Integer id) {
		
		List<ShiftDetailsBean> shiftDetailsBeans =  new ArrayList<ShiftDetailsBean>();
		List<ShiftDetails> shiftDetails = shiftDetailsDao.findByTrackId(id);
		
		for(ShiftDetails shiftDetailsModel : shiftDetails)
		{
			ShiftDetailsBean shiftDetailsBean = new ShiftDetailsBean();

			shiftDetailsBean.setEndTime(shiftDetailsModel.getEndTime());
			shiftDetailsBean.setStartTime(shiftDetailsModel.getStartTime());
			shiftDetailsBean.setShiftDetailsId(shiftDetailsModel.getId());
			shiftDetailsBean.setShiftInitials(shiftDetailsModel.getShift().getShiftInitials());
			shiftDetailsBean.setShiftId(shiftDetailsModel.getShift().getShiftId());
			shiftDetailsBean.setShiftName(shiftDetailsModel.getShift().getShiftName());
			shiftDetailsBean.setPickup(shiftDetailsModel.getPickUpEligible());
			shiftDetailsBean.setDrop(shiftDetailsModel.getDropEligible());
			shiftDetailsBean.setTrackName(shiftDetailsModel.getTrack().getTrackName());
			shiftDetailsBeans.add(shiftDetailsBean);
		}
		Collections.sort(shiftDetailsBeans);
		return shiftDetailsBeans;
		
	}
	@Override
	public void updateAllowance(Shift shift) {
		shiftDetailsDao.updateAllowance(shift);
		
	}
	@Override
	public void deleteShiftDetailsById(Integer id) {
		shiftDetailsDao.deleteShiftDetailsById(id);
	}

}
