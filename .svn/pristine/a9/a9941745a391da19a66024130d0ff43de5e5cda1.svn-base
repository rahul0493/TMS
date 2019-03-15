package com.quinnox.flm.tms.module.service;

import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;

public interface HolidayService extends GenericService<HolidayList, Integer>{
	public void saveOrUpdateHolidayList(HolidayList holiday);
	public List<HolidayList> getHolidayList(Integer location,Integer year);
	public void deleteHoliday(Integer holidayId);
	public List<LocationMaster> getAllLocation();
	public LocationMaster getLocationByName(String name);
}
