package com.quinnox.flm.tms.module.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.dao.TripSheetDetailsDao;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.TripSheetDetails;
import com.quinnox.flm.tms.module.model.Vendor;

@Repository("tripSheetDetailsDao")
public class TripSheetDetailsDaoImpl extends GenericDaoImpl<TripSheetDetails, Integer> implements TripSheetDetailsDao {
	public TripSheetDetailsDaoImpl() {
		super(TripSheetDetails.class);
	}

	@Override
	@Transactional
	public Integer saveTripDetails(TripSheetDetails tripSheetDetails) {
		return (Integer) getCurrentSession().save(tripSheetDetails);
	}

	@Override
	@Transactional
	public TripSheetDetails findByTripSheetNumber(int tripSheetNumber) {
		return (TripSheetDetails) getCurrentSession()
				.createQuery("from TripSheetDetails trip where trip.tripSheetNumber=:tripSheetNumber")
				.setInteger("tripSheetNumber", tripSheetNumber).uniqueResult();
	}

	@Override
	@Transactional
	public Integer updateTripSheetDetails(TripSheetDetailsBean detailsBean) {
		TripSheetDetails tripSheetDetails = new TripSheetDetails();
		Vendor vendor = new Vendor();
		
		if (detailsBean.getTripId() != null) {
			tripSheetDetails = getCurrentSession().get(TripSheetDetails.class, detailsBean.getTripId());
			tripSheetDetails.setTripSheetNumber(detailsBean.getTripSheetNumber());
			tripSheetDetails.setReportingPlace(detailsBean.getReportingPlace());
			tripSheetDetails.setReportingTime(detailsBean.getReportingTime());
			tripSheetDetails.setVehicleNumber(detailsBean.getVehicleNumber());
			tripSheetDetails.setVehicleType(detailsBean.getVehicleType());
			tripSheetDetails.setIsEscort(detailsBean.getIsEscort());
			tripSheetDetails.setEscortName(detailsBean.getEscortName());
			tripSheetDetails.setDriverName(detailsBean.getDriverName());
			tripSheetDetails.setDriverMobileNumber(detailsBean.getDriverMobileNumber());
			tripSheetDetails.setVendorName(detailsBean.getVendorName());
			tripSheetDetails.setTripDate(detailsBean.getTripDate());
			vendor.setVendorId(detailsBean.getVendorId());
			tripSheetDetails.setVendorDetails(vendor);
			getCurrentSession().update(tripSheetDetails);
			return -1;
		} else {
			tripSheetDetails.setTripSheetNumber(detailsBean.getTripSheetNumber());
			tripSheetDetails.setReportingPlace(detailsBean.getReportingPlace());
			tripSheetDetails.setReportingTime(detailsBean.getReportingTime());
			tripSheetDetails.setVehicleNumber(detailsBean.getVehicleNumber());
			tripSheetDetails.setVehicleType(detailsBean.getVehicleType());
			tripSheetDetails.setIsEscort(detailsBean.getIsEscort());
			tripSheetDetails.setEscortName(detailsBean.getEscortName());
			tripSheetDetails.setDriverName(detailsBean.getDriverName());
			tripSheetDetails.setDriverMobileNumber(detailsBean.getDriverMobileNumber());
			tripSheetDetails.setTripDate(detailsBean.getTripDate());
			vendor.setVendorId(detailsBean.getVendorId());
			tripSheetDetails.setVendorDetails(vendor);
			tripSheetDetails.setVendorName(detailsBean.getVendorName());
			Integer id = (Integer) getCurrentSession().save(tripSheetDetails);
			return id;
		}

	}

	@Override
	@Transactional
	public TripSheetDetails findByTripSheetId(int tripSheetId) {
		return getCurrentSession().get(TripSheetDetails.class, tripSheetId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TripSheetDetails> findAllTripSheets() {
		List<TripSheetDetails> tripSheetDetails = getCurrentSession().createQuery("from TripSheetDetails").list();
		return tripSheetDetails;
	}

	@Override
	public void updateEscortInTripSheet(TripSheetDetailsBean detailsBean) {
		TripSheetDetails details = (TripSheetDetails) getCurrentSession()
				.createQuery("from TripSheetDetails trip where trip.tripSheetNumber=:tripSheetNumber")
				.setInteger("", detailsBean.getTripSheetNumber()).uniqueResult();
		details.setIsEscort(detailsBean.getIsEscort());
		details.setEscortName(detailsBean.getEscortName());
		getCurrentSession().update(details);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TripSheetDetails> findAllTripSheetsByMonth(int month,int year) {
		
		List<TripSheetDetails> tripSheetDetailsList = getCurrentSession().createQuery("from TripSheetDetails trip "
				+ "where substring(trip.tripDate,5,1)=:month and substring(trip.tripDate,7,4)=:year")
				.setString("month",String.valueOf(month))
				.setString("year", String.valueOf(year))
				.list();
		
		
		
//		Calendar cal = Calendar.getInstance();
//		List<TripSheetDetails> tripListOfMonth = new ArrayList<TripSheetDetails>();
//		for (TripSheetDetails details : tripSheetDetailsList) {
//			try {
//				Date date = new SimpleDateFormat("dd-MM-yyyy").parse(details.getTripDate());
//				cal.setTime(date);
//				int currentMonth = cal.get(Calendar.MONTH);
//				int currentYear = cal.get(Calendar.YEAR);
//				if ((currentMonth + 1) == month && currentYear == year) {
//					tripListOfMonth.add(details);
//				}
//			} catch (ParseException e) {
//				// TODO Auto-generated catch bloc
//				e.printStackTrace();
//			}
//
//		}
//	
				
		return tripSheetDetailsList;
	}

	@Override
	@Transactional
	public void deleteTripDetails(int tripId) {
		getCurrentSession().delete(getCurrentSession().get(TripSheetDetails.class, tripId));
		System.out.println("trip id" + tripId);

	}
}
