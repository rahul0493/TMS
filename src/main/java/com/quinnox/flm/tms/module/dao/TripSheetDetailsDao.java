package com.quinnox.flm.tms.module.dao;

import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.model.TripSheetDetails;

public interface TripSheetDetailsDao extends GenericDao<TripSheetDetails, Integer> {
	Integer saveTripDetails(TripSheetDetails tripSheetDetails);
	TripSheetDetails findByTripSheetNumber(int tripSheetNumber);
	Integer updateTripSheetDetails(TripSheetDetailsBean detailsBean);
	TripSheetDetails findByTripSheetId(int tripSheetId);
	List<TripSheetDetails> findAllTripSheets(); 
	void updateEscortInTripSheet(TripSheetDetailsBean detailsBean);
	List<TripSheetDetails> findAllTripSheetsByMonth(int month,int year);
	void deleteTripDetails(int tripId);
}
