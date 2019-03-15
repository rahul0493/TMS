/**
 * 
 */
package com.quinnox.flm.tms.global.dao;

import java.util.List;

import com.quinnox.flm.tms.generic.dao.GenericDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.Track;


/**
 * @author RahulY
 *
 */
public interface TrackDao extends GenericDao<Employee, Integer> {

	public void saveTrackEmployee();

	public List<Employee> findEmployeeRolesByTrackId(Integer projectId);

	public List<Track> findAllTrackByProjectId(int projectId);

	public List<String> findTrackName(Integer trackId);

	List<String> findAllTrackByProjectIdforSpoc(int id);
}
