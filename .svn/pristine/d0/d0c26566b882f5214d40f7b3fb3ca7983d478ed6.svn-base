/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.service.impl;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.dao.ProjectDetailsDao;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.shiftplanner.bean.AllowanceBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.OtherTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ShiftAllowanceBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ShiftPlannerBean;
import com.quinnox.flm.tms.module.shiftplanner.dao.ShiftAllowanceDao;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftAllowanceDetails;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanDetail;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;
import com.quinnox.flm.tms.module.shiftplanner.service.ShiftAllowanceService;

/**
 * @author RahulY
 *
 */
@Service
public class ShiftAllowanceServiceImpl  extends GenericServiceImpl<Employee,Integer>  implements ShiftAllowanceService {

	
	private ShiftAllowanceDao shiftAllowanceDao;
	
	@Autowired
	private ProjectDetailsService projectDetailsService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	public ShiftAllowanceServiceImpl(@Qualifier("shiftAllowanceDao") ShiftAllowanceDao shiftAllowanceDao) {
		super(shiftAllowanceDao);
		this.shiftAllowanceDao = shiftAllowanceDao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<AllowanceBean> FindAllShiftAllowance() {
		List<AllowanceBean> list = new ArrayList<AllowanceBean>();
		
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void GenerateShiftAllowance(int monthId, int year, int trackId, int spocId, int projectId) {
	    List<ShiftPlanner> shiftPlannerList = new ArrayList<ShiftPlanner>();
	  try{  
	    shiftPlannerList = getShiftPlannerToGenerateShiftAllowance(monthId,projectId,trackId,year,spocId);
	   System.out.println("shiftPlannerList::"+shiftPlannerList.toString()+"Size::"+shiftPlannerList.size());
	    List<ShiftAllowanceBean> shiftAllowanceBeanList = new ArrayList<ShiftAllowanceBean>(); 
	    Map<Integer,List<ShiftAllowanceBean>> map = new HashMap<Integer,List<ShiftAllowanceBean>>();
		//List<HolidayList> holiday	= shiftAllowanceDao.getHolidayListByMonth(monthId,year);
		
		for (ShiftPlanner shiftPlanner: shiftPlannerList) {
			
			for(ShiftPlanDetail shiftPlanDetail : shiftPlanner.getShiftPlanDetails()){
				System.out.println("Inside shiftPlanDetails for allowance");
				ShiftAllowanceBean shiftAllowanceBean = new ShiftAllowanceBean();
				shiftAllowanceBean.setProjectId(shiftPlanner.getProjectId().getId());
				shiftAllowanceBean.setMonthId(shiftPlanner.getMonthId());
				shiftAllowanceBean.setAccountId(shiftPlanner.getAccountName().getId());
				shiftAllowanceBean.setSpocId(shiftPlanner.getRequestingEmpId());
	            shiftAllowanceBean.setShiftPlanId(shiftPlanner.getId());
	            shiftAllowanceBean.setShiftName(shiftPlanDetail.getShift().getShiftInitials());
				shiftAllowanceBean.setYear(shiftPlanner.getYear());
			    shiftAllowanceBean.setDay(shiftPlanDetail.getKey().getDate());
			    shiftAllowanceBean.setEmployeeId(shiftPlanDetail.getKey().getEmployee().getId());
			    shiftAllowanceBean.setEmployeeName(shiftPlanDetail.getKey().getEmployee().getName());
			    shiftAllowanceBean.setShiftId(shiftPlanDetail.getShift().getShiftId());
			    shiftAllowanceBean.setTrackId(shiftPlanDetail.getTrackBelongsTo().getTrackId());
                shiftAllowanceBean.setLocation(shiftPlanDetail.getKey().getEmployee().getLocation().getLocationName());  
			
			
						if (map.containsKey(shiftAllowanceBean.getEmployeeId())) {

				shiftAllowanceBeanList = map.get(shiftAllowanceBean.getEmployeeId());

				shiftAllowanceBeanList.add(shiftAllowanceBean);
			} else {
				List<ShiftAllowanceBean> addValues = new ArrayList<ShiftAllowanceBean>();
				addValues.add(shiftAllowanceBean);
				map.put(shiftAllowanceBean.getEmployeeId(), addValues);
			}
			}				
		}
		
		List<HolidayList>  holidayList= shiftAllowanceDao.getHolidayListByMonth(monthId, year);
		List<ShiftAllowanceDetails> shiftAllowanceDetailList = new ArrayList<ShiftAllowanceDetails>();
		for (Entry<Integer, List<ShiftAllowanceBean>> entrySet : map.entrySet()) {
			System.out.println("Key" + entrySet.getKey() + "Value:"
					+ entrySet.getValue());
			Integer totalNoOfNightShift =0;
			Integer totalNoOfDayShift =0;
			Integer totalNoOfPublicHoliday=0;
			Integer totalNoOfLeave =0;
			Integer totalNoOfNationalHoliday=0;
			Integer totalNoOfWorkingDays =0;
			Integer totalNoOFWeekOff =0;
			
			//Iterator<ShiftAllowanceBean> itr = entrySet.getValue().iterator();
			
			ShiftAllowanceDetails  allowanceDetails=null;
			for (ShiftAllowanceBean shiftAllowanceBean : entrySet.getValue()) {
				allowanceDetails = new ShiftAllowanceDetails();
			    
				
					Track track = new Track();
					track.setTrackId(shiftAllowanceBean.getTrackId());
					allowanceDetails.setTrackId(track);
					Employee employee = new Employee();
					employee.setId(shiftAllowanceBean.getEmployeeId());
					allowanceDetails.setEmpId(employee);
					allowanceDetails.setEmployeeName(shiftAllowanceBean.getEmployeeName());
					allowanceDetails.setMonthId(shiftAllowanceBean.getMonthId());
					Project project = new Project();
					project.setId(shiftAllowanceBean.getProjectId());
					allowanceDetails.setProjectId(project);
					ShiftPlanner shiftPlanner = new ShiftPlanner();
					shiftPlanner.setId(shiftAllowanceBean.getShiftPlanId());
					allowanceDetails.setShiftPlanId(shiftPlanner);
					Employee spocDetails = new Employee();
					spocDetails.setId(shiftAllowanceBean.getSpocId());
					allowanceDetails.setSpocId(spocDetails);
									
				System.out.println("Check If Employee is eligible for SHIFT ALLOWANCE");
				if(shiftAllowanceBean.getShiftName().equalsIgnoreCase("NS")){
				totalNoOfNightShift++;
				}else if(shiftAllowanceBean.getShiftName().equalsIgnoreCase("L")){
					totalNoOfLeave++;
					}else if(shiftAllowanceBean.getShiftName().equalsIgnoreCase("W")){
				    totalNoOFWeekOff++;
					}else{
					totalNoOfDayShift++;
					}
				if(!holidayList.isEmpty() && holidayList!=null){
				for (HolidayList holiday : holidayList) {
					if(shiftAllowanceBean.getDay()==holiday.getHolidayDate().getDate()){
						if(shiftAllowanceBean.getLocation().equalsIgnoreCase(holiday.getLocation().getLocationName()))
						{if(holiday.getIsNationalHoliday()){
							totalNoOfNationalHoliday++;
						}else if(holiday.getIsPublicHoliday()){
							totalNoOfPublicHoliday++;
						}
						}
					}
					
				}}
				
				
			}
			 System.out.println("Size"+entrySet.getValue().size());
				System.out.println("nightShift"+totalNoOfNightShift);
				
				System.out.println("totalnoofWorkingDays"+totalNoOfWorkingDays);
				System.out.println("totalNoOfWeekOff"+totalNoOFWeekOff);
				System.out.println("totalNoOFDayShift"+totalNoOfDayShift);
				System.out.println("totalNoOfPH"+totalNoOfPublicHoliday);
				System.out.println("totalNoNH"+totalNoOfNationalHoliday);
				System.out.println("total");
				if(totalNoOfNightShift>=5){
			 allowanceDetails.setTotalNoOfDayShiftWorked(totalNoOfDayShift);
			 totalNoOfWorkingDays=entrySet.getValue().size()-totalNoOFWeekOff-totalNoOfLeave;
			 allowanceDetails.setTotalNoOfDaysWorked(totalNoOfWorkingDays);
			 allowanceDetails.setTotalNoOfNationalHolidayWorked(totalNoOfNationalHoliday);
			 allowanceDetails.setTotalNoOfNightShiftWorked(totalNoOfNightShift);
			 allowanceDetails.setTotalNoOfPublicHolidayWorked(totalNoOfPublicHoliday);
			 shiftAllowanceDetailList.add(allowanceDetails);
				}else if(totalNoOfNightShift<5){
					allowanceDetails.setTotalNoOfDayShiftWorked(0);
					 totalNoOfWorkingDays=entrySet.getValue().size()-totalNoOFWeekOff-totalNoOfLeave;
					 allowanceDetails.setTotalNoOfDaysWorked(totalNoOfWorkingDays);
					 allowanceDetails.setTotalNoOfNationalHolidayWorked(totalNoOfNationalHoliday);
					 allowanceDetails.setTotalNoOfNightShiftWorked(totalNoOfNightShift);
					 allowanceDetails.setTotalNoOfPublicHolidayWorked(totalNoOfPublicHoliday);
					 shiftAllowanceDetailList.add(allowanceDetails);
				}
			
			}
	        shiftAllowanceDao.SaveShiftAllwance(shiftAllowanceDetailList);    
		
	  }catch(Exception e){
		  e.printStackTrace();
	  }

	}

	@Override
	public String approveShiftDetails(int monthId, int year, int trackId,
			int spocId, int projectId) {
		return shiftAllowanceDao.approveShiftDetails( monthId,  year,
				 trackId,spocId,projectId);
	}

	@Override
	public List<ShiftPlanner> getShiftPlannerToGenerateShiftAllowance(
			int monthId, int projectId, int trackId, int year, int spocId) {
		
		return shiftAllowanceDao.getShiftPlannerToGenerateShiftAllowance(monthId,projectId,trackId,year,spocId);
	}
	
	@Override
	public List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowance(
			int monthId, int projectId, int trackId, int year, int spocId) {
		
		return shiftAllowanceDao.getShiftPlannerToCalculateShiftAllowance(monthId,projectId,trackId,year,spocId);
	}
	
	@Override
	public List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowanceForSpoc(
			int monthId, int projectId, int trackId, int year, int spocId) {
		
		return shiftAllowanceDao.getShiftPlannerToCalculateShiftAllowanceForSpoc(monthId,projectId,trackId,year,spocId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<AllowanceBean> getAllowance(Integer from, Integer to,
			Integer accountId, Integer projectId) {
		List<AllowanceBean> allowanceBeanList = new ArrayList<AllowanceBean>();
		List<ShiftAllowanceDetails> shiftAllowanceDetailsList = shiftAllowanceDao.getAllowance(from,to,accountId,projectId);
		List<Shift> shiftdetail = shiftAllowanceDao.getShiftDetails();
		for(ShiftAllowanceDetails shiftAllowanceDetails : shiftAllowanceDetailsList) {
			AllowanceBean shiftAllowanceDetailsNightShift =new AllowanceBean();
			AllowanceBean shiftAllowanceDetailsDayShift =new AllowanceBean();
			AllowanceBean shiftAllowanceDetailsPh =new AllowanceBean();
		    	AllowanceBean shiftAllowanceDetailsNh =new AllowanceBean();
		    	String monthString = new DateFormatSymbols().getMonths()[shiftAllowanceDetails.getMonthId()-1];
		    	if(shiftAllowanceDetails.getTotalNoOfNightShiftWorked()!=null && shiftAllowanceDetails.getTotalNoOfNightShiftWorked()>0){
		    	shiftAllowanceDetailsNightShift.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
		    	shiftAllowanceDetailsNightShift.setEmpName(shiftAllowanceDetails.getEmployeeName());
		    	
		    	shiftAllowanceDetailsNightShift.setMonth(monthString);
		    	shiftAllowanceDetailsNightShift.setProjectName(shiftAllowanceDetails.getProjectId().getProjectName());
		    	shiftAllowanceDetailsNightShift.setRemark("Night Shift (3rd)"+"-"+monthString.substring(0,3)+"-"+18);
		    	shiftAllowanceDetailsNightShift.setEarningComponentAffected("Shift Allowance");
		    	shiftAllowanceDetailsNightShift.setSpocName(shiftAllowanceDetails.getSpocId().getName());
		    	for (Shift shift : shiftdetail) {
		    		if(shift.getShiftInitials().equalsIgnoreCase("NS")){
		    		if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfNightShiftWorked()!=null){
		    			shiftAllowanceDetailsNightShift.setAllowanceAmount((long)(shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfNightShiftWorked()));
		    		 }
		    		}
				}
		    	
		    	allowanceBeanList.add(shiftAllowanceDetailsNightShift);
		    	}
		    	if(shiftAllowanceDetails.getTotalNoOfDayShiftWorked()!=null && shiftAllowanceDetails.getTotalNoOfDayShiftWorked()>0){
			    	shiftAllowanceDetailsDayShift.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
			    	shiftAllowanceDetailsDayShift.setEmpName(shiftAllowanceDetails.getEmployeeName());
			    	shiftAllowanceDetailsDayShift.setMonth(monthString);
			    	shiftAllowanceDetailsDayShift.setProjectName(shiftAllowanceDetails.getProjectId().getProjectName());
			    	shiftAllowanceDetailsDayShift.setRemark("Day Shift (2nd)"+"-"+monthString.substring(0,3)+"-"+18);
			    	shiftAllowanceDetailsDayShift.setEarningComponentAffected("Shift Allowance");
			    	shiftAllowanceDetailsDayShift.setSpocName(shiftAllowanceDetails.getSpocId().getName());
			    	for (Shift shift : shiftdetail) {
			    		if(shift.getShiftInitials().equalsIgnoreCase("1S")||shift.getShiftInitials().equalsIgnoreCase("2S") ){
			    			if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfDayShiftWorked()!=null){
			    				shiftAllowanceDetailsDayShift.setAllowanceAmount((long) (shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfDayShiftWorked()));
			    			}
			    		}
			    	}
			    	allowanceBeanList.add(shiftAllowanceDetailsDayShift);
			    	}
		    	if(shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()!=null && shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()>0){
			    	shiftAllowanceDetailsPh.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
			    	shiftAllowanceDetailsPh.setEmpName(shiftAllowanceDetails.getEmployeeName());
			    	shiftAllowanceDetailsPh.setMonth(monthString);
			    	shiftAllowanceDetailsPh.setProjectName(shiftAllowanceDetails.getProjectId().getProjectName());
			    	shiftAllowanceDetailsPh.setRemark("Holiday Pay"+"-"+monthString.substring(0,3)+"-"+18);
			    	shiftAllowanceDetailsPh.setEarningComponentAffected("Holiday Pay");
			    	shiftAllowanceDetailsPh.setSpocName(shiftAllowanceDetails.getSpocId().getName());
			    	for (Shift shift : shiftdetail) {
			    		if(shift.getShiftInitials().equalsIgnoreCase("PH")){
			    		if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()!=null){
			    			shiftAllowanceDetailsPh.setAllowanceAmount((long) (shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()));
			    		}
			    		}
			    	}
			    		allowanceBeanList.add(shiftAllowanceDetailsPh);
			    	}
		    	if(shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()!=null && shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()>0){
			    	shiftAllowanceDetailsNh.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
			    	shiftAllowanceDetailsNh.setEmpName(shiftAllowanceDetails.getEmployeeName());
			    	shiftAllowanceDetailsNh.setMonth(monthString);
			    	shiftAllowanceDetailsNh.setProjectName(shiftAllowanceDetails.getProjectId().getProjectName());
			    	shiftAllowanceDetailsNh.setRemark("Holiday Pay"+"-"+monthString.substring(0,3)+"-"+18);
			    	shiftAllowanceDetailsNh.setEarningComponentAffected("Holiday Pay");
			    	shiftAllowanceDetailsNh.setSpocName(shiftAllowanceDetails.getSpocId().getName());
			    	for (Shift shift : shiftdetail) {
			    		if(shift.getShiftInitials().equalsIgnoreCase("NH")){
			    			if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()!=null){
			    			  shiftAllowanceDetailsNh.setAllowanceAmount((long) (shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()));
			    			}
			    		}
			    		
			    	}
			    		allowanceBeanList.add(shiftAllowanceDetailsNh);
			    	}
		    	
		    	
		}
		return allowanceBeanList;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<ShiftAllowanceDetails> calculateShiftAllowance(int monthId, int year, int trackId, int spocId, int projectId) {
	    List<ShiftPlanner> shiftPlannerList = new ArrayList<ShiftPlanner>();
	    List<ShiftAllowanceDetails> shiftAllowanceDetailList = new ArrayList<ShiftAllowanceDetails>();
	  try{  
	    shiftPlannerList = getShiftPlannerToCalculateShiftAllowanceForSpoc(monthId,projectId,trackId,year,spocId);
	   System.out.println("shiftPlannerList::"+shiftPlannerList.toString()+"Size::"+shiftPlannerList.size());
	    List<ShiftAllowanceBean> shiftAllowanceBeanList = new ArrayList<ShiftAllowanceBean>(); 
	    Map<Integer,List<ShiftAllowanceBean>> map = new HashMap<Integer,List<ShiftAllowanceBean>>();
		//List<HolidayList> holiday	= shiftAllowanceDao.getHolidayListByMonth(monthId,year);
	    
		for (ShiftPlanner shiftPlanner: shiftPlannerList) {
			
			for(ShiftPlanDetail shiftPlanDetail : shiftPlanner.getShiftPlanDetails()){
				System.out.println("Inside shiftPlanDetails for allowance");
				ShiftAllowanceBean shiftAllowanceBean = new ShiftAllowanceBean();
				
				shiftAllowanceBean.setProjectId(shiftPlanner.getProjectId().getId());
				
				
				shiftAllowanceBean.setMonthId(shiftPlanner.getMonthId());
				shiftAllowanceBean.setAccountId(shiftPlanner.getAccountName().getId());
				shiftAllowanceBean.setSpocId(shiftPlanner.getRequestingEmpId());
	            shiftAllowanceBean.setShiftPlanId(shiftPlanner.getId());
	            shiftAllowanceBean.setShiftName(shiftPlanDetail.getShift().getShiftInitials());
				shiftAllowanceBean.setYear(shiftPlanner.getYear());
			    shiftAllowanceBean.setDay(shiftPlanDetail.getKey().getDate());
			    shiftAllowanceBean.setEmployeeId(shiftPlanDetail.getKey().getEmployee().getId());
			    shiftAllowanceBean.setEmpCode(shiftPlanDetail.getKey().getEmployee().getEmpcode());
			    shiftAllowanceBean.setEmployeeName(shiftPlanDetail.getKey().getEmployee().getName());
			    shiftAllowanceBean.setShiftId(shiftPlanDetail.getShift().getShiftId());
			    shiftAllowanceBean.setTrackId(shiftPlanDetail.getTrackBelongsTo().getTrackId());
                shiftAllowanceBean.setLocation(shiftPlanDetail.getKey().getEmployee().getLocation().getLocationName());  
			
			
						if (map.containsKey(shiftAllowanceBean.getEmployeeId())) {

				shiftAllowanceBeanList = map.get(shiftAllowanceBean.getEmployeeId());

				shiftAllowanceBeanList.add(shiftAllowanceBean);
			} else {
				List<ShiftAllowanceBean> addValues = new ArrayList<ShiftAllowanceBean>();
				addValues.add(shiftAllowanceBean);
				map.put(shiftAllowanceBean.getEmployeeId(), addValues);
			}
			}				
		}
		
		List<HolidayList>  holidayList= shiftAllowanceDao.getHolidayListByMonth(monthId, year);
		
		for (Entry<Integer, List<ShiftAllowanceBean>> entrySet : map.entrySet()) {
			System.out.println("Key" + entrySet.getKey() + "Value:"
					+ entrySet.getValue());
			Integer totalNoOfNightShift =0;
			Integer totalNoOfDayShift =0;
			Integer totalNoOfPublicHoliday=0;
			Integer totalNoOfLeave =0;
			Integer totalNoOfNationalHoliday=0;
			Integer totalNoOfWorkingDays =0;
			Integer totalNoOFWeekOff =0;
			
			//Iterator<ShiftAllowanceBean> itr = entrySet.getValue().iterator();
			
			ShiftAllowanceDetails  allowanceDetails=null;
			for (ShiftAllowanceBean shiftAllowanceBean : entrySet.getValue()) {
				allowanceDetails = new ShiftAllowanceDetails();
			    
				
					Track track = new Track();
					track.setTrackId(shiftAllowanceBean.getTrackId());
					allowanceDetails.setTrackId(track);
					Employee employee = new Employee();
					employee.setId(shiftAllowanceBean.getEmployeeId());
					employee.setEmpcode(shiftAllowanceBean.getEmpCode());
					allowanceDetails.setEmpId(employee);
					allowanceDetails.setEmployeeName(shiftAllowanceBean.getEmployeeName());
					allowanceDetails.setMonthId(shiftAllowanceBean.getMonthId());
					Project project = new Project();
					project.setId(shiftAllowanceBean.getProjectId());
					allowanceDetails.setProjectId(project);
					ShiftPlanner shiftPlanner = new ShiftPlanner();
					shiftPlanner.setId(shiftAllowanceBean.getShiftPlanId());
					allowanceDetails.setShiftPlanId(shiftPlanner);
					employee.setId(shiftAllowanceBean.getSpocId());
					allowanceDetails.setSpocId(employee);
									
				System.out.println("Check If Employee is eligible for SHIFT ALLOWANCE");
				if(shiftAllowanceBean.getShiftName().equalsIgnoreCase("NS")){
				totalNoOfNightShift++;
				}else if(shiftAllowanceBean.getShiftName().equalsIgnoreCase("L")){
					totalNoOfLeave++;
					}else if(shiftAllowanceBean.getShiftName().equalsIgnoreCase("W")){
				    totalNoOFWeekOff++;
					}else{
					totalNoOfDayShift++;
					}
				if(!holidayList.isEmpty() && holidayList!=null){
				for (HolidayList holiday : holidayList) {
					if(shiftAllowanceBean.getDay()==holiday.getHolidayDate().getDate()){
						if(shiftAllowanceBean.getLocation().equalsIgnoreCase(holiday.getLocation().getLocationName()))
						{if(holiday.getIsNationalHoliday()){
							totalNoOfNationalHoliday++;
						}else if(holiday.getIsPublicHoliday()){
							totalNoOfPublicHoliday++;
						}
						}
					}
					
				}}
				
				
			}
			 System.out.println("Size"+entrySet.getValue().size());
				System.out.println("nightShift"+totalNoOfNightShift);
				
				System.out.println("totalnoofWorkingDays"+totalNoOfWorkingDays);
				System.out.println("totalNoOfWeekOff"+totalNoOFWeekOff);
				System.out.println("totalNoOFDayShift"+totalNoOfDayShift);
				System.out.println("totalNoOfPH"+totalNoOfPublicHoliday);
				System.out.println("totalNoNH"+totalNoOfNationalHoliday);
				System.out.println("total");
				if(totalNoOfNightShift>=5){
			 allowanceDetails.setTotalNoOfDayShiftWorked(totalNoOfDayShift);
			 totalNoOfWorkingDays=entrySet.getValue().size()-totalNoOFWeekOff-totalNoOfLeave;
			 allowanceDetails.setTotalNoOfDaysWorked(totalNoOfWorkingDays);
			 allowanceDetails.setTotalNoOfNationalHolidayWorked(totalNoOfNationalHoliday);
			 allowanceDetails.setTotalNoOfNightShiftWorked(totalNoOfNightShift);
			 allowanceDetails.setTotalNoOfPublicHolidayWorked(totalNoOfPublicHoliday);
			 shiftAllowanceDetailList.add(allowanceDetails);
				}else if(totalNoOfNightShift<5){
					allowanceDetails.setTotalNoOfDayShiftWorked(0);
					 totalNoOfWorkingDays=entrySet.getValue().size()-totalNoOFWeekOff-totalNoOfLeave;
					 allowanceDetails.setTotalNoOfDaysWorked(totalNoOfWorkingDays);
					 allowanceDetails.setTotalNoOfNationalHolidayWorked(totalNoOfNationalHoliday);
					 allowanceDetails.setTotalNoOfNightShiftWorked(totalNoOfNightShift);
					 allowanceDetails.setTotalNoOfPublicHolidayWorked(totalNoOfPublicHoliday);
					 shiftAllowanceDetailList.add(allowanceDetails);
				}
			
			}
	     
		
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	return shiftAllowanceDetailList;

	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<AllowanceBean> calcAllowance(List<ShiftAllowanceDetails> shiftAllowanceDetailsList, Integer fromDate, Integer toDate) {
		List<AllowanceBean> allowanceBeanList = new ArrayList<AllowanceBean>();
	//	List<ShiftAllowanceDetails> shiftAllowanceDetailsList = shiftAllowanceDao.getAllowance(from,to,accountId,projectId);
		List<Shift> shiftdetail = shiftAllowanceDao.getShiftDetails();
		for(ShiftAllowanceDetails shiftAllowanceDetails : shiftAllowanceDetailsList) {
			Project project = projectDetailsService.findProjectById(shiftAllowanceDetails.getProjectId().getId());
			Employee spocDetails = employeeService.findById(shiftAllowanceDetails.getSpocId().getId());
			AllowanceBean shiftAllowanceDetailsNightShift =new AllowanceBean();
			AllowanceBean shiftAllowanceDetailsDayShift =new AllowanceBean();
			AllowanceBean shiftAllowanceDetailsPh =new AllowanceBean();
		    	AllowanceBean shiftAllowanceDetailsNh =new AllowanceBean();
		    	String monthString = new DateFormatSymbols().getMonths()[shiftAllowanceDetails.getMonthId()-1];
		    	if(shiftAllowanceDetails.getTotalNoOfNightShiftWorked()!=null && shiftAllowanceDetails.getTotalNoOfNightShiftWorked()>0){
		    	shiftAllowanceDetailsNightShift.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
		    	shiftAllowanceDetailsNightShift.setEmpName(shiftAllowanceDetails.getEmployeeName());
		    	
		    	shiftAllowanceDetailsNightShift.setMonth(monthString);
		    	shiftAllowanceDetailsNightShift.setProjectName(project.getProjectName());
		    	shiftAllowanceDetailsNightShift.setRemark("Night Shift (3rd)"+"-"+monthString.substring(0,3)+"-"+18);
		    	shiftAllowanceDetailsNightShift.setEarningComponentAffected("Shift Allowance");
		    	shiftAllowanceDetailsNightShift.setSpocName(spocDetails.getName());
		    	for (Shift shift : shiftdetail) {
		    		if(shift.getShiftInitials().equalsIgnoreCase("NS")){
		    		if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfNightShiftWorked()!=null){
		    			shiftAllowanceDetailsNightShift.setAllowanceAmount((long)(shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfNightShiftWorked()));
		    			shiftAllowanceDetailsNightShift.setNoOfDays(shiftAllowanceDetails.getTotalNoOfNightShiftWorked());
		    		 }
		    		}
				}
		    	
		    	allowanceBeanList.add(shiftAllowanceDetailsNightShift);
		    	}
		    	if(shiftAllowanceDetails.getTotalNoOfDayShiftWorked()!=null && shiftAllowanceDetails.getTotalNoOfDayShiftWorked()>0){
			    	shiftAllowanceDetailsDayShift.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
			    	shiftAllowanceDetailsDayShift.setEmpName(shiftAllowanceDetails.getEmployeeName());
			    	shiftAllowanceDetailsDayShift.setMonth(monthString);
			    	shiftAllowanceDetailsDayShift.setProjectName(project.getProjectName());
			    	shiftAllowanceDetailsDayShift.setRemark("Day Shift (2nd)"+"-"+monthString.substring(0,3)+"-"+18);
			    	shiftAllowanceDetailsDayShift.setEarningComponentAffected("Shift Allowance");
			    	shiftAllowanceDetailsDayShift.setSpocName(spocDetails.getName());
			    	for (Shift shift : shiftdetail) {
			    		if(shift.getShiftInitials().equalsIgnoreCase("1S")||shift.getShiftInitials().equalsIgnoreCase("2S") ){
			    			if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfDayShiftWorked()!=null){
			    				shiftAllowanceDetailsDayShift.setAllowanceAmount((long) (shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfDayShiftWorked()));
			    				shiftAllowanceDetailsDayShift.setNoOfDays(shiftAllowanceDetails.getTotalNoOfDayShiftWorked());
			    			}
			    		}
			    	}
			    	allowanceBeanList.add(shiftAllowanceDetailsDayShift);
			    	}
		    	if(shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()!=null && shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()>0){
			    	shiftAllowanceDetailsPh.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
			    	shiftAllowanceDetailsPh.setEmpName(shiftAllowanceDetails.getEmployeeName());
			    	shiftAllowanceDetailsPh.setMonth(monthString);
			    	shiftAllowanceDetailsPh.setProjectName(project.getProjectName());
			    	shiftAllowanceDetailsPh.setRemark("Holiday Pay"+"-"+monthString.substring(0,3)+"-"+18);
			    	shiftAllowanceDetailsPh.setEarningComponentAffected("Holiday Pay");
			    	shiftAllowanceDetailsPh.setSpocName(spocDetails.getName());
			    	for (Shift shift : shiftdetail) {
			    		if(shift.getShiftInitials().equalsIgnoreCase("PH")){
			    		if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()!=null){
			    			shiftAllowanceDetailsPh.setAllowanceAmount((long) (shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked()));
			    			shiftAllowanceDetailsPh.setNoOfDays(shiftAllowanceDetails.getTotalNoOfPublicHolidayWorked());
			    		}
			    		}
			    	}
			    		allowanceBeanList.add(shiftAllowanceDetailsPh);
			    	}
		    	if(shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()!=null && shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()>0){
			    	shiftAllowanceDetailsNh.setEmpId(shiftAllowanceDetails.getEmpId().getEmpcode());
			    	shiftAllowanceDetailsNh.setEmpName(shiftAllowanceDetails.getEmployeeName());
			    	shiftAllowanceDetailsNh.setMonth(monthString);
			    	shiftAllowanceDetailsNh.setProjectName(project.getProjectName());
			    	shiftAllowanceDetailsNh.setRemark("Holiday Pay"+"-"+monthString.substring(0,3)+"-"+18);
			    	shiftAllowanceDetailsNh.setEarningComponentAffected("Holiday Pay");
			    	shiftAllowanceDetailsNh.setSpocName(spocDetails.getName());
			    	for (Shift shift : shiftdetail) {
			    		if(shift.getShiftInitials().equalsIgnoreCase("NH")){
			    			if(shift.getAllowance()!=null && shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()!=null){
			    			  shiftAllowanceDetailsNh.setAllowanceAmount((long) (shift.getAllowance()*shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked()));
			    			  shiftAllowanceDetailsNh.setNoOfDays(shiftAllowanceDetails.getTotalNoOfNationalHolidayWorked());
			    			}
			    		}
			    		
			    	}
			    		allowanceBeanList.add(shiftAllowanceDetailsNh);
			    	}
		    	
		}
		return allowanceBeanList;
	}
	
	
}
