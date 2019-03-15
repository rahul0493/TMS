package com.quinnox.flm.tms.module.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.HolidayBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.beans.VendorBean;
import com.quinnox.flm.tms.module.model.Project;

public class JsonConverter {

	public static AdhocCabRequestBean jsonToCabrequest(String cabRequestJson){
		 
		AdhocCabRequestBean adhocCabRequestBean=null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			adhocCabRequestBean = objectMapper.readValue(cabRequestJson, AdhocCabRequestBean.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return adhocCabRequestBean;
		
	}
	
	public static ProjectDetailsBean jsonToProjectDetails(String projectDetailsJson){
		 
		ProjectDetailsBean projectDetailsBean=null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			projectDetailsBean = objectMapper.readValue(projectDetailsJson, ProjectDetailsBean.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectDetailsBean;
		
	}
	
	public static ShiftDetailsBean jsonToShiftDetails(String shiftDetailsJson){
		 
		ShiftDetailsBean shiftDetailsBean=null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			shiftDetailsBean = objectMapper.readValue(shiftDetailsJson, ShiftDetailsBean.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shiftDetailsBean;
		
	}
	
	public static EmployeeBean jsonToEmployeeDetails(String employeeDetailsJson){
		 
		EmployeeBean employeeBean=null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			employeeBean = objectMapper.readValue(employeeDetailsJson, EmployeeBean.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeeBean;
	}	
	public static EmployeeAddressBean jsonToEmployeeAddressDetails(String employeeDetailsJson){
		 
		EmployeeAddressBean employeeBean=null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			employeeBean = objectMapper.readValue(employeeDetailsJson, EmployeeAddressBean.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeeBean;
	}
	public static String getJsonCabRequestDetails(List<AdhocCabRequestBean> requestList) {

		JSONArray jsonArray = new JSONArray();
		    for (AdhocCabRequestBean cabRequestBean:requestList){
	         JSONObject historyObject = new JSONObject();
	         try {
				 historyObject.put("scheduleDate", cabRequestBean.getScheduleDate());
				 historyObject.put("mobileNumber", cabRequestBean.getMobileNumber());
		         historyObject.put("requestType",cabRequestBean.getRequestType());
		         historyObject.put("empName", cabRequestBean.getEmpName());
		         historyObject.put("empMail", cabRequestBean.getEmpMailId());
		         historyObject.put("requestId", cabRequestBean.getId());
		         historyObject.put("requestTime", cabRequestBean.getReqTime());
		         historyObject.put("requestStatus", cabRequestBean.getRequestStatus());
		         historyObject.put("projectId", cabRequestBean.getProjectId());
		         historyObject.put("projectName", cabRequestBean.getProjectName());
		         
		         historyObject.put("fromAddress", cabRequestBean.getFromAddress());
		         historyObject.put("fromAliasName", cabRequestBean.getFromAliasName());
		         historyObject.put("fromCity", cabRequestBean.getFromCity());
		         historyObject.put("fromLandmark", cabRequestBean.getFromLandmark());
		         historyObject.put("fromLocation", cabRequestBean.getFromLocation());
		         historyObject.put("fromPincode", cabRequestBean.getFromPincode());
		         
		         historyObject.put("toAddress", cabRequestBean.getToAddress());
		         historyObject.put("toAliasName", cabRequestBean.getToAliasName());
		         historyObject.put("toCity", cabRequestBean.getToCity());
		         historyObject.put("toLandmark", cabRequestBean.getToLandmark());
		         historyObject.put("toLocation", cabRequestBean.getToLocation());
		         historyObject.put("toPincode", cabRequestBean.getToPincode());
		         
//		         historyObject.put("receiveType", cabRequestBean.getPickTime());

	         } catch (JSONException e) {
				e.printStackTrace();
			}
	 	 
	         jsonArray.put(historyObject);
		      
	    }
	    //responseDetailsJson.put("assetList", jsonArray);
	    return jsonArray.toString();
 }
	
	public static CabRequestRemarkBean jsonToCabrequestRemark(String cabRequestJson){
		 
		CabRequestRemarkBean cabRequestRemark=null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			cabRequestRemark = objectMapper.readValue(cabRequestJson, CabRequestRemarkBean.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cabRequestRemark;
		
	}
public static String getProjectList(List<Project> projectList){
		
		JSONArray jsonArray = new JSONArray();
		 for (Project project: projectList){
	         JSONObject projectObject = new JSONObject();
	         try {
	        	 projectObject.put("projectId", project.getId());
	        	 projectObject.put("projectName", project.getProjectName());
	         } catch (JSONException e) {
				e.printStackTrace();
			}
	 	 
	         jsonArray.put(projectObject);
		      
	    }
		 return jsonArray.toString();
	}
public static TripSheetDetailsBean jsonToTripSheetDetails(String jsonToTripSheetDetails){
	 
	TripSheetDetailsBean tripSheetDetailsBean=null;
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		
		tripSheetDetailsBean = objectMapper.readValue(jsonToTripSheetDetails, TripSheetDetailsBean.class);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return tripSheetDetailsBean;
}	 
public static List<AdhocCabRequestBean> jsonToFLMAction(String flmActionJson){
	 
	List<AdhocCabRequestBean> adhocCabRequestBean=null;
	
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<AdhocCabRequestBean>> mapType = new TypeReference<List<AdhocCabRequestBean>>(){};
		adhocCabRequestBean = objectMapper.readValue(flmActionJson, mapType);
	//	adhocCabRequestBean = (List<AdhocCabRequestBean>) objectMapper.readValue((JsonParser) Arrays.asList(flmActionJson), AdhocCabRequestBean.class);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return adhocCabRequestBean;
	
}
public static List<SpocBean> jsonToSpocMappingBean(String SpocMappingBean){
	 List<SpocBean> beanList = new ArrayList<SpocBean>();
	
	
	try {
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<SpocBean>> mapType = new TypeReference<List<SpocBean>>(){};	
		beanList = objectMapper.readValue(SpocMappingBean, mapType);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return beanList;
	
}
public static List<SpocBean> jsonToTrackMappingBean(String SpocMappingBean){
	 List<SpocBean> beanList = new ArrayList<SpocBean>();
	
	//changed spoc bean from userTrackMapping Bean
	try {
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<SpocBean>> mapType = new TypeReference<List<SpocBean>>(){};	
		beanList = objectMapper.readValue(SpocMappingBean, mapType);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return beanList;
	
}

public static VendorBean jsonToVendorDetails(String vendorDetailsJson){
	
	VendorBean vendorDetailsBean=null;
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		vendorDetailsBean = objectMapper.readValue(vendorDetailsJson, VendorBean.class);
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return vendorDetailsBean;
	
}
public static List<EmployeeBean> jsonToEmpData(String empDataJson){
	
	 
	List<EmployeeBean> beanList=null;
	
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<EmployeeBean>> mapType = new TypeReference<List<EmployeeBean>>(){};
		beanList = objectMapper.readValue(empDataJson, mapType);
	//	adhocCabRequestBean = (List<AdhocCabRequestBean>) objectMapper.readValue((JsonParser) Arrays.asList(flmActionJson), AdhocCabRequestBean.class);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return beanList;
	
}

public static List<HolidayBean> jsonToHolidayList(String holidayJson){
	 
	List<HolidayBean> holidayBean=null;
	
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<HolidayBean>> mapType = new TypeReference<List<HolidayBean>>(){};
		holidayBean = objectMapper.readValue(holidayJson, mapType);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return holidayBean;
	
}

public static List<ShiftDetailsBean> jsonToAllowanceList(String allowanceJson){
	 
	List<ShiftDetailsBean> shiftBean=null;
	
	try {
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<List<ShiftDetailsBean>> mapType = new TypeReference<List<ShiftDetailsBean>>(){};
		shiftBean = objectMapper.readValue(allowanceJson, mapType);
		
	} catch (JsonParseException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return shiftBean;
	
}

}
