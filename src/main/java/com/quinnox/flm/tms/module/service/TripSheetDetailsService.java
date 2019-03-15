package com.quinnox.flm.tms.module.service;

import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.model.TripSheetDetails;

public interface TripSheetDetailsService extends GenericService<TripSheetDetails, Integer>{
	Integer saveTripDetails(TripSheetDetails tripSheetDetails);
	TripSheetDetails findByTripSheetNumber(int tripSheetNumber);
	void updateTripSheetDetails(TripSheetDetailsBean detailsBean);
	TripSheetDetails findByTripSheetId(int tripSheetId);
	List<TripSheetDetailsBean> findAllTripSheets(); 
	void updateEscortInTripSheet(TripSheetDetailsBean detailsBean);
	List<TripSheetDetailsBean> findAllTripSheetsByMonth(int month, int year);
	void deleteTripDetails(int tripId);
}
