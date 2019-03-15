package com.quinnox.flm.tms.module.shiftplanner.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.gson.JsonObject;
import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.dao.TrackDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.bean.AccountBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.CabRequestBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.DateBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.EmployeeBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.EmployeeTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.MonthBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.OtherTrackBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ProjectBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.ShiftPlan;
import com.quinnox.flm.tms.module.shiftplanner.bean.ShiftPlannerBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.StatusBean;
import com.quinnox.flm.tms.module.shiftplanner.bean.TrackShift;
import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.shiftplanner.bean.TrackBean;
import com.quinnox.flm.tms.module.shiftplanner.dao.ShiftPlannerDao;
import com.quinnox.flm.tms.module.shiftplanner.model.OtherTrack;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftKey;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanDetail;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;
import com.quinnox.flm.tms.module.shiftplanner.service.ShiftPlannerService;

@Service
public class ShiftPlannerServiceImpl  extends GenericServiceImpl<Employee,Integer> implements ShiftPlannerService {

	
	@Autowired
	private ShiftPlannerDao shiftPlannerDao;
	
	@Autowired
	private TrackDao trackDao;
	
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());   

	@Autowired
	public ShiftPlannerServiceImpl(@Qualifier("shiftPlannerDao") ShiftPlannerDao shiftPlannerDao) {
		super(shiftPlannerDao);
		this.shiftPlannerDao = shiftPlannerDao;
	}

	@Override
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Employee> findSpocDetails(int id) {
		
		return shiftPlannerDao.findAllAccounts(id);
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Set<TrackShift> FindShiftbyTrack(int id) {
		Set<TrackShift> beanList = new HashSet<TrackShift>();
		List<ShiftDetails> shiftListRegular =shiftPlannerDao.findShiftByTrack(id);
		List<Shift> shiftList=shiftPlannerDao.getPrevilegedandHolidayShift();
		
		for(Shift shift : shiftList){
			TrackShift trackShiftBean = new TrackShift();
			trackShiftBean.setShift_id(shift.getShiftId());
			trackShiftBean.setShift_display_nm(shift.getShiftName());
			trackShiftBean.setShift_master_nm(shift.getShiftInitials());
			trackShiftBean.setShift_type(shift.getEnumValues());
					beanList.add(trackShiftBean);
		}
		for (ShiftDetails shiftDetailObject : shiftListRegular) {
			TrackShift trackShiftBean = new TrackShift();
			trackShiftBean.setShift_id(shiftDetailObject.getShift()
					.getShiftId());
			trackShiftBean.setShift_display_nm(shiftDetailObject.getShift()
					.getShiftName());
			trackShiftBean.setShift_master_nm(shiftDetailObject.getShift()
					.getShiftInitials());
			trackShiftBean.setShift_from_time(shiftDetailObject.getStartTime());
			trackShiftBean.setShift_to_time(shiftDetailObject.getEndTime());
			trackShiftBean.setShift_type(shiftDetailObject.getShift().getEnumValues());
			beanList.add(trackShiftBean);
		}
				return beanList;
			}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<ShiftPlanDetail> findShiftDetailByMonth(int id,int monthId,int trackId) {		
				return shiftPlannerDao.getShiftDetails(id,monthId,trackId);
		
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Set<TrackBean> findTrackbyProject(int projectId) {
		  Set<TrackBean> trackBeanSet = new HashSet<TrackBean>();
		System.out.println(shiftPlannerDao.findTrackbyProject(  projectId));
		for( Track track : shiftPlannerDao.findTrackbyProject(  projectId))
		{
			TrackBean trackBean = new TrackBean();
			trackBean.setTrackId(track.getTrackId());
			trackBean.setTrack_nm(track.getTrackName());
			trackBeanSet.add(trackBean);
		}
				
				return trackBeanSet;
	}

		
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<TrackBean> findAllProjectTrackByProjectId(int projectId,int trackId) {
		List<TrackBean> tracks = new ArrayList<TrackBean>();
		List<Track> trackList = trackDao.findAllTrackByProjectId(projectId);
		for (Track track : trackList) {
			
			if(track.getTrackId() != trackId){
				TrackBean trackBean = new TrackBean();
				trackBean.setTrack_nm(track.getTrackName());
				trackBean.setTrackId(track.getTrackId());
				tracks.add(trackBean);
			}
		}
		
		return tracks ;
	}


	@Override
	public List<Employee> getEmployeeByTrackId(Integer trackId) {

		return shiftPlannerDao.getEmployeeByTrackId(trackId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Set<ProjectBean> getProjectByAccount(int empId,int accountId) {
		Set<ProjectBean> projectBeanSet = new HashSet<ProjectBean>();
		List<Employee> employeeList =findSpocDetails(empId);
	
		for (Employee emp : employeeList) {
			for (Track track : emp.getTracks()) {
				if(accountId == track.getProject().getAccount().getId()){
			ProjectBean projectBean = new ProjectBean();
				projectBean.setProjId(track.getProject().getId());
				projectBean.setProj_nm(track.getProject().getProjectName());
				projectBeanSet.add(projectBean);
			}
			}
		}
		return projectBeanSet;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MonthBean> getShiftPlanner(int year, int monthId, int trackId,int projectId) {
		//LOG.debug("get the shift planner for the year:= "+year + "month:= " + monthId + "and track:=" + trackId);
		
		List<ShiftPlanDetail> shiftPlanDetailsList = findShiftDetailByMonth(year, monthId, trackId);
	Set<ShiftPlanDetail> shiftPlannerList = new HashSet<>(shiftPlanDetailsList);				
		
		
		List<MonthBean> monthBeanList = null;
		List<DateBean> dateBeanList = null;
		
		monthBeanList = new ArrayList<MonthBean>();
		
		//List<ShiftPlan> shiftPlanList = new ArrayList<ShiftPlan>();
		
		MonthBean monthBean = new MonthBean();

		Map<Integer, List<ShiftPlan>> shiftPlanMap = new HashMap<Integer, List<ShiftPlan>>();

		dateBeanList = new ArrayList<DateBean>();

		for (ShiftPlanDetail shiftPlanDetail : shiftPlannerList) {

			ShiftPlan shiftPlan = new ShiftPlan();
			
			if (monthBean.getMonth() == null) {
				monthBean.setMonth(shiftPlanDetail.getMonth_name());
				monthBean.setMonthId(shiftPlanDetail.getKey().getMonth());
			}

			shiftPlan.setEmp_init(shiftPlanDetail.getEmp_init());
			shiftPlan.setEmp_id(shiftPlanDetail.getKey().getEmployee().getId());
		shiftPlan.setShift_id(shiftPlanDetail.getShift().getShiftId());
			shiftPlan.setShift_nm(shiftPlanDetail.getShift().getShiftInitials());
			shiftPlan.setTrack_id(shiftPlanDetail.getTrackBelongsTo().getTrackId());
			List<OtherTrackBean> otherTrackBeans = new ArrayList<OtherTrackBean>();
			List<Track> tracks = trackDao.findAllTrackByProjectId(projectId);
			 for (Track track : tracks) {
				 if(track.getTrackId() != shiftPlanDetail.getTrackBelongsTo().getTrackId() ){
				 OtherTrackBean otherTrackBean = new OtherTrackBean();
	            	otherTrackBean.setTrck_id(track.getTrackId());
	            	otherTrackBean.setTrck_nm(track.getTrackName());
	            	otherTrackBean.setTrackAssignedTo("false");
	            	for (OtherTrack otherTrack : shiftPlanDetail.getOtherTrack()) {
	            		if(otherTrack.getTrackAssignedTo().getTrackId()==track.getTrackId()){
		            		otherTrackBean.setTrackAssignedTo("true");
		            	}
					}
	            	
	            	otherTrackBeans.add(otherTrackBean);
				 }
			}     
			shiftPlan.setOther_trck(otherTrackBeans);
			if (shiftPlanMap.containsKey(shiftPlanDetail.getKey().getDate())) {
				List<ShiftPlan> shiftPlans = shiftPlanMap.get(shiftPlanDetail.getKey()
						.getDate());

				shiftPlans.add(shiftPlan);
			} else {
				List<ShiftPlan> addValues = new ArrayList<ShiftPlan>();
				addValues.add(shiftPlan);
				shiftPlanMap.put(shiftPlanDetail.getKey().getDate(), addValues);

			}
		}

		for (Entry<Integer, List<ShiftPlan>> entrySet : shiftPlanMap.entrySet()) {
			DateBean dateBean = new DateBean();
			dateBean.setDate(entrySet.getKey());
			dateBean.setShiftPlans(entrySet.getValue());
			dateBeanList.add(dateBean);
			monthBean.setList(dateBeanList);
		}

		if (monthBean.getMonth() == null) {
             
		} else {
			monthBeanList.add(monthBean);
		}
		return monthBeanList;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Set<EmployeeTrackBean> getEmployeebyProject(int projectId,Integer spocId) {
		Set<EmployeeTrackBean> trackBeanList = new HashSet<EmployeeTrackBean>();
		List<Employee> employeeDetailList = findSpocDetails(spocId);
//		/List<Track> trackList = shiftPlannerDao.findTrackbyProject(projectId);		
		   for (Employee emp : employeeDetailList) {
				for (Track track : emp.getTracks()) {
               if(track.getProject().getId() == projectId){
				EmployeeTrackBean employeeTrackBean = new EmployeeTrackBean();
				employeeTrackBean.setEmp_trackid(track.getTrackId());
				employeeTrackBean.setEmp_trcknm(track.getTrackName());
				
				//LOG.debug("getting all the employee based on the track");
				List<Employee> employeeList = getEmployeeByTrackId(track.getTrackId());
				
				List<EmployeeBean> employeeBeanList = new ArrayList<EmployeeBean>();
				
				for (Employee employee : employeeList) {
					EmployeeBean employeeBean = new EmployeeBean();
					employeeBean.setEmpid(employee.getId());
					employeeBean.setEmpnm(employee.getName());
					employeeBean.setEmp_trackid(track.getTrackId());
					if(employee.getName().length()>3){
					employeeBean.setEmp_init(employee.getName().substring(0, 3));
					}else{
						employeeBean.setEmp_init(employee.getName());
					}
					//LOG.debug("getting all the track except employee assigned tracks in project");
					//get all the track except employee assigned track 
					employeeBean.setOther_track(findAllProjectTrackByProjectId(projectId,
									track.getTrackId()));
					employeeBeanList.add(employeeBean);
					employeeTrackBean.setEmp(employeeBeanList);
					trackBeanList.add(employeeTrackBean);
				}


			}}}
				return trackBeanList;
		
		
	}

	@Override
	public Integer createShiftPlanner(int accountId,int monthId, int trackId, int year,
			int projectId, Map<Integer,List<ShiftPlannerBean>> map, UserProfileBean profileBean,String cabReq) {
		Integer message = 0;
		try{
			 
			for (Entry<Integer, List<ShiftPlannerBean>> entrySet : map.entrySet()) {
				System.out.println("Key" + entrySet.getKey() + "Value:"
						+ entrySet.getValue());
				ShiftPlanner planner = new ShiftPlanner();
		Account account = new Account();
		account.setId(accountId);
		planner.setAccountName(account);
		planner.setMonthId(monthId);
	    Project project = new Project();
	    project.setId(projectId);
		planner.setProjectId(project);
		planner.setReqDate(new Date());
		planner.setRequestingEmpId(profileBean.getEmployeeId());
		if(trackId==0){
		Track track = new Track();
		track.setTrackId(entrySet.getKey());
		planner.setTrackId(track);
		}else{
			Track track = new Track();
			track.setTrackId(trackId);
			planner.setTrackId(track);
		}
		planner.setYear(year);
		if(cabReq.equalsIgnoreCase("true")){
			planner.setCabGenerated(true);
		}
		Set<ShiftPlanDetail> shiftPlanDetailSet = new HashSet<ShiftPlanDetail>();	
		Set<OtherTrack> otherTrackSet = new HashSet<OtherTrack>();
		for (ShiftPlannerBean shiftPlannerBean : entrySet.getValue()) {
			
		/*}
		JSONArray jsonArray = new JSONArray(createJson);
		for (int i = 0; i < jsonArray.length(); i++) {*/
			//JSONObject jsonObject = jsonArray.getJSONObject(i);
			ShiftPlanDetail shiftPlanDetail = new ShiftPlanDetail();
			shiftPlanDetail.setEmp_init(shiftPlannerBean.getEmployeeIntials()); 
			shiftPlanDetail.setMonth_name(shiftPlannerBean.getMonthName());
			shiftPlanDetail.setSpocId(profileBean.getEmployeeId());
			Track trackBelongsTo = new Track();
			trackBelongsTo.setTrackId(shiftPlannerBean.getTrackId());
			shiftPlanDetail.setTrackBelongsTo(trackBelongsTo);
			//composite key 
			ShiftKey key = new ShiftKey();
			key.setDate(shiftPlannerBean.getDay());
			Employee employee = new Employee();
			employee.setId(shiftPlannerBean.getEmployeeId());
			key.setEmployee(employee);
			key.setMonth(shiftPlannerBean.getMonthId());
			key.setYear(shiftPlannerBean.getYear());
			Shift shift = new Shift();
			shift.setShiftId(shiftPlannerBean.getShiftId());
			shiftPlanDetail.setShift(shift);
			shiftPlanDetail.setKey(key);
			//If employee is assigned only for his track
			
			OtherTrack onTrack = new OtherTrack();
			Track ownTrack = new Track();
			ownTrack.setTrackId(shiftPlannerBean.getTrackId());
			onTrack.setTrackAssignedTo(ownTrack);
			onTrack.setShiftPlanDetail(shiftPlanDetail);
			otherTrackSet.add(onTrack);
			
			//other tracks
			/*for (int j = 0; j < jsonObject.getJSONArray("other_trck").length(); j++) {
				
				if(!jsonObject.getJSONArray("other_trck").toString().equalsIgnoreCase("[{}]")){
				JSONObject jObject = jsonObject.getJSONArray("other_trck").getJSONObject(j);*/
			if( shiftPlannerBean.getOtherTrack()!=null){
			for (OtherTrackBean otherTrackBean : shiftPlannerBean.getOtherTrack()) {
				
				OtherTrack otherTrack = new OtherTrack();
				Track otrack = new Track();
				otrack.setTrackId(otherTrackBean.getTrck_id());
				otherTrack.setTrackAssignedTo(otrack);
				otherTrack.setShiftPlanDetail(shiftPlanDetail);
				otherTrackSet.add(otherTrack);
			}}
			
			shiftPlanDetail.setOtherTrack(otherTrackSet);
			shiftPlanDetail.setShiftPlan(planner);
			shiftPlanDetailSet.add(shiftPlanDetail);
		
		
			
		}
		planner.setShiftPlanDetails(shiftPlanDetailSet);
		message= shiftPlannerDao.saveShiftPlan(planner);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			StackTraceElement[] s =e.getStackTrace();
			message=0;
			
		}
		
		return message;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MonthBean> getShiftPlannerByProjectId(int year, int monthId,
			int projectId,Integer spocId) {

		List<ShiftPlanner> shiftPlannerList = findShiftDetailByProjectId(year, monthId, projectId,spocId);
						
		
		
		List<MonthBean> monthBeanList = null;
		List<DateBean> dateBeanList = null;
		
		monthBeanList = new ArrayList<MonthBean>();
		
		//List<ShiftPlan> shiftPlanList = new ArrayList<ShiftPlan>();
		
		MonthBean monthBean = new MonthBean();

		Map<Integer, List<ShiftPlan>> shiftPlanMap = new HashMap<Integer, List<ShiftPlan>>();

		dateBeanList = new ArrayList<DateBean>();
        for(ShiftPlanner shiftPlanner : shiftPlannerList){
		for (ShiftPlanDetail shiftPlanDetail : shiftPlanner.getShiftPlanDetails()) {

			ShiftPlan shiftPlan = new ShiftPlan();
			
			if (monthBean.getMonth() == null) {
				monthBean.setMonth(shiftPlanDetail.getMonth_name());
				monthBean.setMonthId(shiftPlanDetail.getKey().getMonth());
			}

			shiftPlan.setEmp_init(shiftPlanDetail.getEmp_init());
			shiftPlan.setEmp_id(shiftPlanDetail.getKey().getEmployee().getId());
		shiftPlan.setShift_id(shiftPlanDetail.getShift().getShiftId());
			shiftPlan.setShift_nm(shiftPlanDetail.getShift().getShiftInitials());
			shiftPlan.setTrack_id(shiftPlanDetail.getTrackBelongsTo().getTrackId());
			List<OtherTrackBean> otherTrackBeans = new ArrayList<OtherTrackBean>();
			List<Track> tracks = trackDao.findAllTrackByProjectId(projectId);
			 for (Track track : tracks) {
				 if(track.getTrackId() != shiftPlanDetail.getTrackBelongsTo().getTrackId() ){
				 OtherTrackBean otherTrackBean = new OtherTrackBean();
	            	otherTrackBean.setTrck_id(track.getTrackId());
	            	otherTrackBean.setTrck_nm(track.getTrackName());
	            	otherTrackBean.setTrackAssignedTo("false");
	            	for (OtherTrack otherTrack : shiftPlanDetail.getOtherTrack()) {
	            		if(otherTrack.getTrackAssignedTo().getTrackId()==track.getTrackId()){
		            		otherTrackBean.setTrackAssignedTo("true");
		            	}
					}
	            	
	            	otherTrackBeans.add(otherTrackBean);
				 }
			}     
			shiftPlan.setOther_trck(otherTrackBeans);
			if (shiftPlanMap.containsKey(shiftPlanDetail.getKey().getDate())) {
				List<ShiftPlan> shiftPlans = shiftPlanMap.get(shiftPlanDetail.getKey()
						.getDate());

				shiftPlans.add(shiftPlan);
			} else {
				List<ShiftPlan> addValues = new ArrayList<ShiftPlan>();
				addValues.add(shiftPlan);
				shiftPlanMap.put(shiftPlanDetail.getKey().getDate(), addValues);

			}
		}
        }
		for (Entry<Integer, List<ShiftPlan>> entrySet : shiftPlanMap.entrySet()) {
			DateBean dateBean = new DateBean();
			dateBean.setDate(entrySet.getKey());
			dateBean.setShiftPlans(entrySet.getValue());
			dateBeanList.add(dateBean);
			monthBean.setList(dateBeanList);
		}

		if (monthBean.getMonth() == null) {
             
		} else {
			monthBeanList.add(monthBean);
		}
		return monthBeanList;
	}

	private List<ShiftPlanner> findShiftDetailByProjectId(int year,
			int monthId, int projectId,Integer spocId) {
	
		return shiftPlannerDao.findShiftDetailByProjectId(year,monthId,projectId,spocId);
	}

	/*s*/

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Set<TrackShift> getShiftForProject(UserProfileBean profileBean,
			int projectId) {
		Set<TrackShift> trackShiftList = new HashSet<TrackShift>();
List<Shift> shiftPrevilegedList=shiftPlannerDao.getPrevilegedandHolidayShift();
		
		for(Shift shift : shiftPrevilegedList){
			TrackShift trackShiftBean = new TrackShift();
			trackShiftBean.setShift_id(shift.getShiftId());
			trackShiftBean.setShift_display_nm(shift.getShiftName());
			trackShiftBean.setShift_master_nm(shift.getShiftInitials());
            trackShiftBean.setShift_type(shift.getEnumValues());
			trackShiftList.add(trackShiftBean);
		}
		List<Employee> employeedetailsList = findSpocDetails(profileBean.getEmployeeId());
		List<String> displayName = new ArrayList<String>();
		for(Employee employee : employeedetailsList) {
			for(Track track : employee.getTracks()){
				
				if(track!=null & track.getProject().getId()==projectId){
				//LOG.debug("getting list of shift in track");		
			List<ShiftDetails> shiftList = shiftPlannerDao.findShiftByTrack(track.getTrackId());
			
			for (ShiftDetails shiftDetailList : shiftList) {
				TrackShift trackShift = new TrackShift();
				trackShift.setShift_id(shiftDetailList.getShift().getShiftId());
				if(displayName.contains(shiftDetailList.getShift().getShiftName())){
					trackShift.setShift_display_nm(shiftDetailList.getShift().getShiftName()+" - "+shiftDetailList.getTrack().getTrackName()+" - "+shiftDetailList.getShift().getShiftInitials());
				}else
				{
					trackShift.setShift_display_nm(shiftDetailList.getShift().getShiftName());
				}
				trackShift.setShift_master_nm(shiftDetailList.getShift().getShiftInitials());
				trackShift.setShift_from_time(shiftDetailList.getStartTime());
				trackShift.setShift_to_time(shiftDetailList.getEndTime());
				trackShift.setShift_type(shiftDetailList.getShift().getEnumValues());
				trackShiftList.add(trackShift);
				displayName.add(trackShift.getShift_display_nm());
				
			}	
			
		}
			}
			}
		return trackShiftList;
	}

	
//need to check this
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ShiftPlanner> shiftPlanByProjectId(int year, int monthId,
			int projectId,Integer spocId) {
	
		 List<ShiftPlanner> previousList = findShiftDetailByProjectId(year, monthId, projectId,spocId);
		
		 return previousList;
	}

	@Override
	public void deleteShiftPlan(List<ShiftPlanner> previousShiftPlan) {
		int i = shiftPlannerDao.deleteShiftPlannerDetails(previousShiftPlan);
		
	}

	@Override
	public List<ShiftPlanner> getShiftPlannerByTrackId(
			UserProfileBean profileBean, int trackId, int projectId,
			int monthId, int year) {
		
		return shiftPlannerDao.getMonthlyShiftPlan( monthId,  trackId,
				 year,  profileBean.getEmployeeId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<StatusBean> getStatusOfShiftPlanner(int projectId, int monthId, int year, Integer spocId) {
		List<Employee> spocDetailsList = findSpocDetails(spocId);
		List<ShiftPlanner> shiftPlannerList = shiftPlannerDao.getStatusOfShiftPlanner(projectId,monthId,year,spocId);
		List<StatusBean> statusBeanList = new ArrayList<StatusBean>();
		for (Employee spoocDetails : spocDetailsList) {
			for(Track spocTrack : spoocDetails.getTracks()){
				if(spocTrack.getProject().getId() == projectId){
				StatusBean statusBean = new StatusBean();
				statusBean.setTrackId(spocTrack.getTrackId());
				statusBean.setTrackName(spocTrack.getTrackName());
				statusBeanList.add(statusBean);
			}
			}
		}
		
		
			for (ShiftPlanner shiftPlanner : shiftPlannerList) {
				for (StatusBean statusBean : statusBeanList) {
				if(shiftPlanner.getTrackId()!=null && shiftPlanner.getTrackId().getTrackId()==statusBean.getTrackId())
				{statusBean.setShiftPlanId(shiftPlanner.getId());
				statusBean.setCabGeneratedStatus(shiftPlanner.getCabGenerated().toString());
				statusBean.setApproveStatus(shiftPlanner.getAprroved().toString());
				statusBean.setDraftStatus("true");
				statusBean.setTrackId(shiftPlanner.getTrackId().getTrackId());
				if(shiftPlanner.getId()!=null && shiftPlanner.getAprroved()==false && shiftPlanner.getCabGenerated()==false 
						&&shiftPlanner.getId()>0){
					statusBean.setDraftStatus("true");
				}
				}
				
			}	
		}		
		return statusBeanList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Set<TrackBean> findAllSpocTrack(Integer employeeId,int projectId) {
		Set<TrackBean> beanList = new HashSet<TrackBean>();
		List<Employee> accountList = findSpocDetails(employeeId);
		for (Employee emp : accountList) {
			for (Track track : emp.getTracks()) {
         if(projectId==track.getProject().getId()){		
				TrackBean bean = new TrackBean();
				bean.setTrackId(track.getTrackId());
				bean.setTrack_nm(track.getTrackName());
				beanList.add(bean);
  }
	}
		}
		return beanList;
	}

	@Override
	public void deleteShiftPlanerDetails(
			List<ShiftPlanDetail> previousShiftPlannnerDetails) {
		shiftPlannerDao.deleteShiftPlanDetails(previousShiftPlannnerDetails);		
	}

	@Override
	public void deleteMasterEntery(int year, int monthId, int projectId,
			Integer employeeId,int trackId) {
		shiftPlannerDao.deleteMasterRecord(employeeId, trackId, projectId, monthId, year);
	}

	@Override
	public List<TrackBean> findAllProjectTrackByProgetStatusOfShiftPlannerjectId(int project_id, int trackId) {
		// TODO Auto-generated method stub
		return null;
	}
}
