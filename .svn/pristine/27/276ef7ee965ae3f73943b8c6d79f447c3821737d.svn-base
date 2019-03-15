/**
 * 
 */
package com.quinnox.flm.tms.global.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.generic.service.impl.GenericServiceImpl;
import com.quinnox.flm.tms.global.beans.TrackMappingBean;
import com.quinnox.flm.tms.global.dao.TrackDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.global.service.TrackService;
import com.quinnox.flm.tms.module.model.Track;



/**
 * @author RahulY
 *
 */
@Service
public class TrackServiceImpl extends GenericServiceImpl<Employee, Integer>
		implements TrackService {

	private TrackDao trackDao;

	@Autowired
	public TrackServiceImpl(@Qualifier("trackDao") TrackDao trackDao) {
		super(trackDao);
		this.trackDao = trackDao;
	}

	@Override
	public void saveTrackForEmployee() {

		trackDao.saveTrackEmployee();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<TrackMappingBean> findEmployeeRolesByProjectId(Integer projectId) {
		List<Employee> empList = trackDao.findEmployeeRolesByTrackId( projectId);
		Boolean isTrack = false;
		
		
		List<String> ProjectTrackList = trackDao.findAllTrackByProjectIdforSpoc(projectId);
		List<TrackMappingBean> trackList=Collections.EMPTY_LIST;
		if(empList.isEmpty()  || ProjectTrackList.isEmpty() ){
			return trackList;
		}else{
			trackList = new ArrayList<>();
			
			for(Employee emp:empList){
				TrackMappingBean trackMappingBean = new TrackMappingBean();
				trackMappingBean.setEmpCode(emp.getEmpcode());
				trackMappingBean.setEmpId(emp.getId());			
				trackMappingBean.setEmpName(emp.getName());
				Set<UserRole> roles = emp.getRoles();
				for(UserRole role:roles){
					if(TmsConstant.SPOC.equalsIgnoreCase(role.getRole())){
						trackMappingBean.setIsSpoc(true);
					}
					
				}
				Map<String, Boolean> maptoTrack = new HashMap<String, Boolean>();
				for (String trackName : ProjectTrackList) {
					maptoTrack.put(trackName, isTrack);
				}
				
						
							
				if(trackMappingBean.getIsSpoc()==true && emp.getTracks()!=null){
				Set<Track> tracks = emp.getTracks();
						
				for (Track track : tracks){
					if(maptoTrack.containsKey(track.getTrackName())){
						maptoTrack.replace(track.getTrackName(), true);
					}
					
					
				}
				
trackMappingBean.setIsEmpMapToTrack(maptoTrack);	
				
				trackList.add(trackMappingBean);	
			}

			}
			return trackList;
		}
		
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<TrackMappingBean> findEmployeeByProjectId(int projectId) {
		List<Employee> empList = trackDao.findEmployeeRolesByTrackId( projectId);
		
		
		List<String> ProjectTrackList = trackDao.findAllTrackByProjectIdforSpoc(projectId);
		//List<Track> ProjectTrackList = trackDao.findAllTrackByProjectId(projectId);
		List<TrackMappingBean> trackList=Collections.EMPTY_LIST;
		
		if(empList.isEmpty() || ProjectTrackList.isEmpty()){
			return trackList;
		}else{
			trackList = new ArrayList<>();
			
			for(Employee emp:empList){
				TrackMappingBean trackMappingBean = new TrackMappingBean();
				trackMappingBean.setEmpCode(emp.getEmpcode());
				trackMappingBean.setEmpId(emp.getId());			
				trackMappingBean.setEmpName(emp.getName());
				List<String> trackNameList =null;
				//System.out.println(emp.getTrackId());
				
				Set<UserRole> roles = emp.getRoles();
				for(UserRole role:roles){
					if(TmsConstant.SPOC.equalsIgnoreCase(role.getRole())){
						trackMappingBean.setIsSpoc(true);
					}
					
				}
			/*	if(emp.getEmployeeTrackMapping()!=null){
               for(EmployeeTrackMapping emptrack : emp.getEmployeeTrackMapping() )
					
               {
					// for(Track tracks :emp.getTracksEmpMapping()){
					//trackNameList = trackDao.findTrackName(emptrack.getTrackId());
					
					//for (String trackName : trackNameList) {
						//System.out.println("inside fdor"+trackName);
						trackMappingBean.setTrackName(emptrack.getTrack().getTrackName());
						trackMappingBean.setTrackId(emptrack.getTrack().getTrackId());
						
					//}
}
					}
				System.out.println(trackMappingBean.getTrackId()+"dfd"+trackMappingBean.getTrackName());
				
							
				List<TrackBean> trackBeanList = new ArrayList<TrackBean>(); 
					for(Track track : ProjectTrackList){
						TrackBean bean = new TrackBean();
						bean.setTrackId(track.getTrackId());
						bean.setTrackName(track.getTrackName());
						trackBeanList.add(bean);
					}
					 
				trackMappingBean.setTrackDetails(trackBeanList);
					
				
				
				
				trackList.add(trackMappingBean);
			}
			}
			return trackList;*/
				Map<String, Boolean> maptoTrack = new HashMap<String, Boolean>();
				for (String trackName : ProjectTrackList) {
					maptoTrack.put(trackName, false);
				}
							
				if(emp.getTracksEmpMapping()!=null){
				 Set<Track> tracks = emp.getTracksEmpMapping();
						
				for (Track track : tracks){
					if(maptoTrack.containsKey(track.getTrackName())){
						maptoTrack.replace(track.getTrackName(), true);
						trackMappingBean.setTrackId(track.getTrackId());
						trackMappingBean.setTrackName(track.getTrackName());
					}
					
				}
			}
				trackMappingBean.setIsEmpMapToTrack(maptoTrack);	
				trackList.add(trackMappingBean);
			}
			return trackList;
		}
	}
}