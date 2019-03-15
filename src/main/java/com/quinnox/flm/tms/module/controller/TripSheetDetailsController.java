package com.quinnox.flm.tms.module.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.TripSheetDetails;
import com.quinnox.flm.tms.module.model.Vendor;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.service.TripSheetDetailsService;
import com.quinnox.flm.tms.module.util.JsonConverter;
@Controller
@RequestMapping("tms/tripSheetDetails")
public class TripSheetDetailsController {
	
	@Autowired
	private TripSheetDetailsService tripSheetDetailsService;
	@Autowired
	private CabRequestService cabRequestService;
	@Autowired
	private ProjectDetailsService projectDetailsService;
	
	@RequestMapping(value = "/saveTripSheetDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveTripSheetDetails(HttpServletRequest request) { 

		String TripSheetDetailsJson = request.getParameter("TripSheetDetailsJson");
		
		TripSheetDetailsBean tripSheetDetailsBean = JsonConverter.jsonToTripSheetDetails(TripSheetDetailsJson);
		String update = request.getParameter("updateTripNumInCabDetails");
		String tripSheetId = request.getParameter("tripSheetId");
		if(update.equals("true"))
		{
			// get data
			TripSheetDetails tripSheetDetails = tripSheetDetailsService.findByTripSheetId(Integer.parseInt(tripSheetId));
			
			cabRequestService.updateCabReqTripSheetDetails(tripSheetDetailsBean,tripSheetDetails.getTripId());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("data", "Saved successfully.");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
		else
		{
		//	TripSheetDetailsBean tripSheetDetailsBean = JsonConverter.jsonToTripSheetDetails(TripSheetDetailsJson);
			TripSheetDetails details = null;
			//if(tripSheetDetailsBean.getTripSheetNumber() != null)
			 details=tripSheetDetailsService.findByTripSheetNumber(tripSheetDetailsBean.getTripSheetNumber()); 
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			// String arrayToJson = null;
			JSONObject jsonObject = new JSONObject();
			if(details==null) {
				
				details = new TripSheetDetails();
				details.setTripSheetNumber(tripSheetDetailsBean.getTripSheetNumber());
				details.setReportingPlace(tripSheetDetailsBean.getReportingPlace());
				details.setReportingTime(tripSheetDetailsBean.getReportingTime());
				details.setVehicleNumber(tripSheetDetailsBean.getVehicleNumber());
				details.setVehicleType(tripSheetDetailsBean.getVehicleType());
				details.setDriverName(tripSheetDetailsBean.getDriverName());
				details.setDriverMobileNumber(tripSheetDetailsBean.getDriverMobileNumber());
				details.setTripDate(tripSheetDetailsBean.getTripDate());
				details.setVendorName(tripSheetDetailsBean.getVendorName());
				details.setIsEscort(tripSheetDetailsBean.getIsEscort());
				details.setEscortName(tripSheetDetailsBean.getEscortName());
				Vendor vendor = new Vendor();
				vendor.setVendorId(tripSheetDetailsBean.getVendorId());
				details.setVendorDetails(vendor);
				Integer tripId = tripSheetDetailsService.saveTripDetails(details);
				cabRequestService.updateCabReqTripSheetDetails(tripSheetDetailsBean,tripId);
				
				try {
					jsonObject.put("data", "Saved successfully.");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return jsonObject.toString();
			}
			else {
				
				if(details.getTripDate() == tripSheetDetailsBean.getTripDate()){
					 tripSheetDetailsService.updateEscortInTripSheet(tripSheetDetailsBean);
					cabRequestService.updateCabReqTripSheetDetails(tripSheetDetailsBean,tripSheetDetailsBean.getTripId());
					jsonObject.put("data","Updated Successfully");
				}
				else{
					jsonObject.put("data","Entered Tripsheet number already exists.");
				
				}
				return jsonObject.toString();
			}
		}
	}

	@RequestMapping(value = "/updateTripSheetDetails", method = RequestMethod.POST)
	@ResponseBody
	public String updateTripSheetDetails(HttpServletRequest request) { 
		String updateTripSheetDetails = request.getParameter("updateTripSheetDetails");
		String getDetails = request.getParameter("getDetails");
		String tripSheetId = request.getParameter("tripSheetId");
		// String cabRequestId = request.getParameter("cabRequestId");
		if(getDetails.equals("true"))
		{
			// get data
			TripSheetDetails tripSheetDetails = tripSheetDetailsService.findByTripSheetId(Integer.parseInt(tripSheetId));
			// EmpCabRequestDetails details=cabRequestService.findByRequestId(Integer.parseInt(cabRequestId));
			TripSheetDetailsBean tripSheetDetailsBean =new TripSheetDetailsBean();
			
			tripSheetDetailsBean.setTripSheetNumber(tripSheetDetails.getTripSheetNumber());
			tripSheetDetailsBean.setReportingPlace(tripSheetDetails.getReportingPlace());
			tripSheetDetailsBean.setReportingTime(tripSheetDetails.getReportingTime());
			tripSheetDetailsBean.setVehicleNumber(tripSheetDetails.getVehicleNumber());
			tripSheetDetailsBean.setVehicleType(tripSheetDetails.getVehicleType());
			tripSheetDetailsBean.setVendorId(tripSheetDetails.getVendorDetails().getVendorId());
			
			
		//	List<EmpCabRequestDetails> cabDeatils = cabRequestService.findCabRequestByTripId(tripSheetDetails.getTripId());
 			
 			
 			
			if(tripSheetDetails.getIsEscort() != null)
			{
				tripSheetDetailsBean.setIsEscort(tripSheetDetails.getIsEscort() ?  true : false);
				tripSheetDetailsBean.setEscortName(tripSheetDetails.getEscortName());
			}
			else
			{
//				for(EmpCabRequestDetails cabDetails:cabDeatils) {
//	 				if(cabDetails.getIsEscort())
//	 				{
//	 					tripSheetDetailsBean.setEscortName(cabDetails.getEscortName());
//	 					
//	 	 				break;
//	 				}
//	 				
//	 			}
				
	 				if(tripSheetDetails.getIsEscort() != null && tripSheetDetails.getIsEscort() == true)
	 				{
	 					tripSheetDetailsBean.setEscortName(tripSheetDetails.getEscortName());
	 				}
	 				
				tripSheetDetailsBean.setIsEscort(false);
			}
			
			tripSheetDetailsBean.setDriverName(tripSheetDetails.getDriverName());
			tripSheetDetailsBean.setDriverMobileNumber(tripSheetDetails.getDriverMobileNumber());
			tripSheetDetailsBean.setTripDate(tripSheetDetails.getTripDate());
			tripSheetDetailsBean.setVendorName(tripSheetDetails.getVendorName());
			tripSheetDetailsBean.setTripId(Integer.parseInt(tripSheetId));
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = null;
			try {
				arrayToJson = objectMapper.writeValueAsString(tripSheetDetailsBean);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return arrayToJson;
		}
		else {
			// Update and Save 
			TripSheetDetailsBean tripSheetDetailsBean = JsonConverter.jsonToTripSheetDetails(updateTripSheetDetails);
			
			tripSheetDetailsService.updateTripSheetDetails(tripSheetDetailsBean);
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", "Saved successfully.");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jsonObject.toString();

		}

	}

	// Based on drop down selected
	@RequestMapping(value = "/getAllTripSheet", method = RequestMethod.GET)
	@ResponseBody
	public String getAllTripSheet(HttpServletRequest request) {
 		List<TripSheetDetailsBean> tripSheets=tripSheetDetailsService.findAllTripSheets();
 		
// 		for(TripSheetDetailsBean bean:tripSheets) {
//			List<EmpCabRequestDetails> cabDeatils = cabRequestService.findCabRequestByTripId(bean.getTripId());
//			for(EmpCabRequestDetails details:cabDeatils) {
//				if(details.getIsEscort())
//				{
//					bean.setEscortName(details.getEscortName());
//	 				bean.setIsEscort(details.getIsEscort());
//	 				break;
//				}
//				
//			}
//			//bean.setEmpCabRequestDetails(cabDeatils);
//			
//		}
 		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(tripSheets);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("List Of TripSheet "+ arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	
	@RequestMapping(value = "/getTripSheetByNumber", method = RequestMethod.POST)
	@ResponseBody
	public String getTripSheetByNumber(HttpServletRequest request,@RequestBody Integer tripSheetNum) {
	    TripSheetDetails tripSheetDetails=tripSheetDetailsService.findByTripSheetNumber(tripSheetNum);
	
		TripSheetDetailsBean tripSheetDetailsBean =new TripSheetDetailsBean();
		tripSheetDetailsBean.setTripSheetNumber(tripSheetDetails.getTripSheetNumber());
		tripSheetDetailsBean.setReportingPlace(tripSheetDetails.getReportingPlace());
		tripSheetDetailsBean.setReportingTime(tripSheetDetails.getReportingTime());
		tripSheetDetailsBean.setVehicleNumber(tripSheetDetails.getVehicleNumber());
		tripSheetDetailsBean.setVehicleType(tripSheetDetails.getVehicleType());
		//tripSheetDetailsBean.setIsEscort(tripSheetDetails.getIsEscort());
		//tripSheetDetailsBean.setEscortName(tripSheetDetails.getEscortName());
		tripSheetDetailsBean.setDriverName(tripSheetDetails.getDriverName());
		tripSheetDetailsBean.setDriverMobileNumber(tripSheetDetails.getDriverMobileNumber());
		tripSheetDetailsBean.setTripDate(tripSheetDetails.getTripDate());
		tripSheetDetailsBean.setVendorName(tripSheetDetails.getVendorName());
		tripSheetDetailsBean.setTripId(tripSheetDetails.getTripId());
	    
	    
	    ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(tripSheetDetailsBean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("TripSheet "+ arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}

	//for trip view
		@RequestMapping(value = "/getAllTripSheetDetails", method = RequestMethod.GET)
		@ResponseBody
		public String getAllTripSheetDetails(HttpServletRequest request) {
			List<TripSheetDetailsBean> tripSheets=tripSheetDetailsService.findAllTripSheets();
	 		for(TripSheetDetailsBean bean:tripSheets) {
	 			List<EmpCabRequestDetails> cabDeatils = cabRequestService.findCabRequestByTripId(bean.getTripId());
	 			List<AdhocCabRequestBean> beanList = new ArrayList<AdhocCabRequestBean>();
	 			for(EmpCabRequestDetails details:cabDeatils) {
	 				AdhocCabRequestBean cabRequestBean=new AdhocCabRequestBean();
	 				cabRequestBean.setId(details.getRequestId());
	 				cabRequestBean.setMobileNumber(details.getMobileNumber());
	 				cabRequestBean.setProjectId(details.getProjectId());
	 				Project project= projectDetailsService.findProjectById(details.getProjectId());
	 				cabRequestBean.setProjectName(project.getProjectName());
	 				cabRequestBean.setEmpName(details.getEmpCabRequest().getUser().getName());
	 				cabRequestBean.setFromLocation(details.getFromLocation());
	 				cabRequestBean.setToLocation(details.getToLocation());
	 				cabRequestBean.setTravelStatus(details.getTravelStatus());
	 				cabRequestBean.setEscortName(bean.getEscortName());
	 				beanList.add(cabRequestBean);
	 			}
	 			bean.setEmpCabRequestDetails(beanList);
	 		}	
	 		ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = null;
			try {
				arrayToJson = objectMapper.writeValueAsString(tripSheets);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			System.out.println("List Of TripSheet "+ arrayToJson);
			System.out.println("executed successfully");
			return arrayToJson;
		}
		
		@RequestMapping(value = "/removeTripIdInDetails", method = RequestMethod.POST)
		@ResponseBody
		public String removeTripIdInDetails(HttpServletRequest request) { 
			JSONObject jsonObject = new JSONObject();
			Integer detailsId = Integer.parseInt(request.getParameter("detailsId"));
			Integer tripId = Integer.parseInt(request.getParameter("tripId"));
			cabRequestService.removeTripId(detailsId,tripId);
			List<EmpCabRequestDetails> cabList = cabRequestService.findCabRequestByTripId(tripId);
			if(cabList.size()==0) {
				tripSheetDetailsService.deleteTripDetails(tripId);
			}
			try {
				jsonObject.put("data", "Saved successfully !!!!");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonObject.toString();
		}
		
}
