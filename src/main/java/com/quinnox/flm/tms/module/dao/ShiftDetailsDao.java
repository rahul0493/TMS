package com.quinnox.flm.tms.module.dao;

import java.util.List;
import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;


public interface ShiftDetailsDao extends GenericDao<ShiftDetails, Integer> {
	public void saveShiftDetails(Shift shift);
	public void saveShiftDetails(ShiftDetails shift);
	public void updateShiftDetails(Shift shift);
	public List<Shift> findAllShift();
	public List<ShiftDetails> findByTrackId(Integer id);
	public void updateAllowance(Shift shift);
	public void deleteShiftDetailsById(Integer id);
}
