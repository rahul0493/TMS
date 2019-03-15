package com.quinnox.flm.tms.module.service.impl;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.dao.TripSheetDetailsDao;
import com.quinnox.flm.tms.module.model.TripSheetDetails;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.TripSheetDetailsService;

@Service
public class TripSheetDetailsServiceImpl extends GenericServiceImpl<TripSheetDetails, Integer> implements TripSheetDetailsService {
	private TripSheetDetailsDao tripSheetDetailsDao; 
	
	@Autowired
	private CabRequestService cabRequestService; 
	
	@Autowired
	public TripSheetDetailsServiceImpl(@Qualifier("tripSheetDetailsDao") TripSheetDetailsDao tripSheetDetailsDao) {
		super(tripSheetDetailsDao);
		this.tripSheetDetailsDao = tripSheetDetailsDao;
	}

	@Override
	public Integer saveTripDetails(TripSheetDetails tripSheetDetails) {
		return tripSheetDetailsDao.saveTripDetails(tripSheetDetails);
	}

	@Override
	public TripSheetDetails findByTripSheetNumber(int tripSheetNumber) {
		return tripSheetDetailsDao.findByTripSheetNumber(tripSheetNumber);
	}

	@Override
	public void updateTripSheetDetails(TripSheetDetailsBean detailsBean) {
	
		// 1st n 2nd scenario
		if(detailsBean.getTripId() != null)
		{
			int id = detailsBean.getTripId();
			tripSheetDetailsDao.updateTripSheetDetails(detailsBean);
			cabRequestService.updateCabReqTripSheetDetails(detailsBean,id);  // detailsBean.getCabDetailsId()
		} 
		else
		{
			Integer id = tripSheetDetailsDao.updateTripSheetDetails(detailsBean);
			if(id != -1)
				cabRequestService.updateCabReqTripSheetDetails(detailsBean,id);
		}
	}

	@Override
	public TripSheetDetails findByTripSheetId(int tripSheetId) {
		return tripSheetDetailsDao.findByTripSheetId(tripSheetId);
	}
	@Override
	public List<TripSheetDetailsBean> findAllTripSheets() {
			List<TripSheetDetails> details= tripSheetDetailsDao.findAllTripSheets();
			List<TripSheetDetailsBean> beans= new ArrayList<TripSheetDetailsBean>();
			for(TripSheetDetails sheetDetails:details) {
				TripSheetDetailsBean bean=new TripSheetDetailsBean();
				bean.setDriverMobileNumber(sheetDetails.getDriverMobileNumber());
				bean.setDriverName(sheetDetails.getDriverName());
				bean.setReportingPlace(sheetDetails.getReportingPlace());
				bean.setReportingTime(sheetDetails.getReportingTime());
				bean.setTripDate(sheetDetails.getTripDate());
				bean.setTripId(sheetDetails.getTripId());
				bean.setTripSheetNumber(sheetDetails.getTripSheetNumber());
				bean.setVehicleNumber(sheetDetails.getVehicleNumber());
				bean.setVehicleType(sheetDetails.getVehicleType());
				bean.setVendorName(sheetDetails.getVendorName());
				beans.add(bean);
			}
		
		
		return beans;
	}

	@Override
	public void updateEscortInTripSheet(TripSheetDetailsBean detailsBean) {
		tripSheetDetailsDao.updateEscortInTripSheet(detailsBean);
	} 
	
	@Override
	public List<TripSheetDetailsBean> findAllTripSheetsByMonth(int month , int year) {
		List<TripSheetDetails> details= tripSheetDetailsDao.findAllTripSheetsByMonth(month,year);
		List<TripSheetDetailsBean> beans= new ArrayList<TripSheetDetailsBean>();
		for(TripSheetDetails sheetDetails:details) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			TripSheetDetailsBean bean=new TripSheetDetailsBean();
			bean.setDriverMobileNumber(sheetDetails.getDriverMobileNumber());
			bean.setDriverName(sheetDetails.getDriverName());
			bean.setReportingPlace(sheetDetails.getReportingPlace());
			bean.setReportingTime(sheetDetails.getReportingTime());
			bean.setTripId(sheetDetails.getTripId());
			bean.setTripSheetNumber(sheetDetails.getTripSheetNumber());
			bean.setVehicleNumber(sheetDetails.getVehicleNumber());
			bean.setVehicleType(sheetDetails.getVehicleType());
			bean.setVendorName(sheetDetails.getVendorName());
			bean.setEscortName(sheetDetails.getEscortName());
			bean.setVendorId(sheetDetails.getVendorDetails().getVendorId());
			bean.setIsEscort(sheetDetails.getIsEscort());
			try {
				bean.setTripScheduleDate(dateFormat.parse(sheetDetails.getTripDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			beans.add(bean);
		}
	
	
	return beans;
	}

	@Override
	public void deleteTripDetails(int tripId) {
		tripSheetDetailsDao.deleteTripDetails(tripId);
		
	} 
}
