/**
 * 
 */
package com.quinnox.flm.tms.global.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.dao.TrackDao;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.Track;



/**
 * @author RahulY
 *
 */
@Repository("trackDao")
public class TrackDaoImpl extends GenericDaoImpl<Employee, Integer> implements TrackDao {

	public TrackDaoImpl() {
		super(Employee.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public void saveTrackEmployee() {
		

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Employee> findEmployeeRolesByTrackId(Integer projectId) {
		List<Employee> empList=Collections.EMPTY_LIST;
		
		List<EmpProjMapping> empProject= getCurrentSession().createCriteria(EmpProjMapping.class)
				.add(Restrictions.eq("project.id", projectId))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		if(empProject!=null && !empProject.isEmpty()){
			empList = new ArrayList<>();
			for(EmpProjMapping empProj:empProject){
				empList.add(empProj.getEmployee());
			}
			Collections.sort(empList);
		}
		
		
		return empList;
	
	}


	@Override
	@SuppressWarnings("unchecked")
	
	@Transactional
	public List<String> findAllTrackByProjectIdforSpoc(int id) {
	
		
			return (List<String>) getCurrentSession().createQuery("select trackName from Track track where track.project.id=:id").setInteger("id", id)
					.list();
		}
	
	@Override
	@SuppressWarnings("unchecked")
	
	@Transactional
	public List<Track> findAllTrackByProjectId(int id) {
	
		
			return (List<Track>) getCurrentSession().createQuery("from Track track where track.project.id=:id").setInteger("id", id)
					.list();
		}
	
	@Override
	@SuppressWarnings("unchecked")
	
	@Transactional
    public List<String> findTrackName(Integer id) {
                    
                    return (List<String>) getCurrentSession().createQuery("select trackName from Track track where track.trackId=:id").setInteger("id", id).list();
                                                    
    }


}
