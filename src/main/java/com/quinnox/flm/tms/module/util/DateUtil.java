package com.quinnox.flm.tms.module.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;

public class DateUtil {

	public static Date getTime(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	
	public static Boolean todayValidReq(String reqTimeAmPm,String currTimeAmPM,Integer reqHour, Integer currHour)
	{
		//currTimeAmPM = "AM";
	//	currHour = 11;
		if(reqTimeAmPm.compareTo(currTimeAmPM) < 0) 
			return false;// am -> reqTime n pm -> currTime n vice versa
		else if (reqTimeAmPm.compareTo(currTimeAmPM) == 0)
		{
			if(reqHour <= currHour)
				return false; // shudnt raise req
			return true;
		}
		return true;
	}
	
	public static Boolean tmrsValidReq(String reqTimeAmPm,String currTimeAmPM,Integer reqHour, Integer currHourFmt,Integer curHr)
	{
		if(reqTimeAmPm.compareTo(currTimeAmPM) < 0) {
		
			if(curHr<17) {
				return true;
			}
			return false;

		}
		else if (reqTimeAmPm.compareTo(currTimeAmPM) == 0)
		{
			if(reqHour <= currHourFmt)
				return false;
			return true;
		}
		return true;
	}
	public static List<AdhocCabRequestBean> checkValidUpcomingRequest(List<AdhocCabRequestBean> cabReqList){
		Integer currentHour = 16 == 24 ? 0 :16;
		Integer currHr = currentHour - 12;
		String currentHourFormated = currentHour >= 12 ?  "0"+currHr.toString() : currentHour.toString();
		String currTimeAmPM = currentHour < 12 ? "AM" : "PM";
		List<AdhocCabRequestBean> adhocCabRequestList = new ArrayList<AdhocCabRequestBean>();
		Boolean validReq=false;
		for(AdhocCabRequestBean details : cabReqList) {
			if(details.getScheduleDate().getDate() == new Date().getDate()
					&& details.getScheduleDate().getMonth() == new Date().getMonth()
					&& details.getScheduleDate().getYear() == new Date().getYear()) {
				String reqTimeAmPm = details.getReqTime().substring(6, 8);
				Integer reqTimeHour = Integer.parseInt(details.getReqTime().substring(0, 2));
				validReq = 	DateUtil.todayValidReq(reqTimeAmPm,currTimeAmPM,reqTimeHour,Integer.parseInt(currentHourFormated));
				if(validReq)
					adhocCabRequestList.add(details);
				
			}
			else if(details.getScheduleDate().getDate() > new Date().getDate()
					&& details.getScheduleDate().getMonth() == new Date().getMonth()
					&& details.getScheduleDate().getYear() == new Date().getYear()) {
				adhocCabRequestList.add(details);
			}
			else if(details.getScheduleDate().getDate() < new Date().getDate()
					&& details.getScheduleDate().getMonth() == new Date().getMonth()
					&& details.getScheduleDate().getYear() == new Date().getYear()) {
				adhocCabRequestList.add(details);
			}
			else {
				adhocCabRequestList.add(details);
			}
		}
		return adhocCabRequestList;
		
	}
	
	public static List<EmpCabRequestDetails> checkValidUpcomingRequestCount(List<EmpCabRequestDetails> cabReqList){
		Integer currentHour = 16 == 24 ? 0 :16;
		Integer currHr = currentHour - 12;
		String currentHourFormated = currentHour >= 12 ?  "0"+currHr.toString() : currentHour.toString();
		String currTimeAmPM = currentHour < 12 ? "AM" : "PM";
		List<EmpCabRequestDetails> adhocCabRequestList = new ArrayList<EmpCabRequestDetails>();
		Boolean validReq=false;
		for(EmpCabRequestDetails details : cabReqList) {
			if(details.getScheduleDate().getDate() == new Date().getDate()
					&& details.getScheduleDate().getMonth() == new Date().getMonth()
					&& details.getScheduleDate().getYear() == new Date().getYear()) {
				String reqTimeAmPm = details.getRequestime().substring(6, 8);
				Integer reqTimeHour = Integer.parseInt(details.getRequestime().substring(0, 2));
				validReq = 	DateUtil.todayValidReq(reqTimeAmPm,currTimeAmPM,reqTimeHour,Integer.parseInt(currentHourFormated));
				if(validReq)
					adhocCabRequestList.add(details);
				
			}	
			else if(details.getScheduleDate().getDate() > new Date().getDate()
					&& details.getScheduleDate().getMonth() == new Date().getMonth()
					&& details.getScheduleDate().getYear() == new Date().getYear()) {
				adhocCabRequestList.add(details);
			}
			else if(details.getScheduleDate().getDate() < new Date().getDate()
					&& details.getScheduleDate().getMonth() == new Date().getMonth()
					&& details.getScheduleDate().getYear() == new Date().getYear()) {
				adhocCabRequestList.add(details);
			}
			else {
				adhocCabRequestList.add(details);
			}
				
		}
		return adhocCabRequestList;
		
	}
}
