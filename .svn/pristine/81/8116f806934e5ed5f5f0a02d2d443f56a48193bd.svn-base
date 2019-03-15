package com.quinnox.flm.tms.module.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.dao.HolidayDao;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;


@Repository("holidayDao")
public class HolidayDaoImpl extends GenericDaoImpl<HolidayList, Integer> implements HolidayDao{
	public HolidayDaoImpl() {
		super(HolidayList.class);
	}

	@Override
	@Transactional
	public void saveOrUpdateHolidayList(HolidayList holiday) {
		getCurrentSession().saveOrUpdate(holiday);
		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<HolidayList> getHolidayList(Integer location, Integer year) {
		return (List<HolidayList>) getCurrentSession().createQuery("from HolidayList hol where hol.location=:location and hol.year=:year")
				.setInteger("location", location)
				.setInteger("year", year)
				.list();
	}

	@Override
	@Transactional
	public void deleteHoliday(Integer holidayId) {
		HolidayList holiday = getCurrentSession().get(HolidayList.class, holidayId);
		getCurrentSession().delete(holiday);
		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<LocationMaster> getAllLocation() {
		return (List<LocationMaster>) getCurrentSession().createQuery("from LocationMaster")
				.list();
	}

	@Override
	@Transactional
	public LocationMaster getLocationByName(String locationName) {
		return (LocationMaster) getCurrentSession().createQuery("from LocationMaster loc where loc.locationName=:locationName").setString("locationName", locationName)
				.uniqueResult();
	}
}
