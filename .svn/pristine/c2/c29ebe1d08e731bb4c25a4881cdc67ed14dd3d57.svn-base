package com.quinnox.flm.tms.module.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.module.dao.HolidayDao;
import com.quinnox.flm.tms.module.dao.ProjectDetailsDao;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.service.HolidayService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;

@Service("holidayService")
public class HolidayServiceImpl extends GenericServiceImpl<HolidayList, Integer> implements HolidayService {
	
	private HolidayDao holidayDao;
	@Autowired
	public HolidayServiceImpl(
			@Qualifier("holidayDao") HolidayDao holidayDao) {
		super(holidayDao);
		this.holidayDao = holidayDao;
	}
	@Override
	public void saveOrUpdateHolidayList(HolidayList holiday) {
		holidayDao.saveOrUpdateHolidayList(holiday);
		
	}
	@Override
	public List<HolidayList> getHolidayList(Integer location, Integer year) {
		return holidayDao.getHolidayList(location, year);
	}
	@Override
	public void deleteHoliday(Integer holidayId) {
		holidayDao.deleteHoliday(holidayId);
		
	}
	@Override
	public List<LocationMaster> getAllLocation() {
		return holidayDao.getAllLocation();
	}
	@Override
	public LocationMaster getLocationByName(String name) {
		return holidayDao.getLocationByName(name);
	}
}
