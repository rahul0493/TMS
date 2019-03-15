package com.quinnox.flm.tms.module.dao;

import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;

public interface HolidayDao extends GenericDao<HolidayList, Integer>{
	public void saveOrUpdateHolidayList(HolidayList holiday);
	public List<HolidayList> getHolidayList(Integer location,Integer year);
	public void deleteHoliday(Integer holidayId);
	public List<LocationMaster> getAllLocation();
	public LocationMaster getLocationByName(String name);
}
