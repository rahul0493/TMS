/**
 * 
 */
package com.quinnox.flm.tms.global.service;

import java.util.List;

import com.quinnox.flm.tms.generic.service.GenericService;
import com.quinnox.flm.tms.global.beans.TrackMappingBean;
import com.quinnox.flm.tms.global.model.Employee;


/**
 * @author RahulY
 *
 */


public interface TrackService extends GenericService<Employee, Integer> {

	public void saveTrackForEmployee();

	public List<TrackMappingBean> findEmployeeRolesByProjectId(Integer projectId);

	public List<TrackMappingBean> findEmployeeByProjectId(int id);
	

}
