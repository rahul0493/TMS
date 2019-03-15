package com.quinnox.flm.tms.module.service;

import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;

public interface ShiftDetailsService extends GenericService<ShiftDetails, Integer> {
	public void saveShiftDetails(Shift shift);
	public void saveShiftDetails(ShiftDetails shift);
	public void updateShiftDetails(Shift shift);
	public List<Shift> findAllShift();
	public List<ShiftDetailsBean> findByTrackId(Integer id);
	public void deleteShiftDetailsById(Integer id);
	public void updateAllowance(Shift shift);
}
