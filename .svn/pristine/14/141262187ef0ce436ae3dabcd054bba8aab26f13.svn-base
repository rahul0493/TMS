package com.quinnox.flm.tms.module.shiftplanner.dao.impl;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;









import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.dao.ShiftPlannerDao;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanDetail;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;
import com.quinnox.flm.tms.module.model.Track;

@Repository("shiftPlannerDao")
public class ShiftPlannerDaoImpl extends GenericDaoImpl<Employee, Integer>
		implements ShiftPlannerDao {

	public ShiftPlannerDaoImpl() {
		super(Employee.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Employee> findAllAccounts(int id) {
		System.out.println("inside dao");
		return (List<Employee>) getCurrentSession()
				.createQuery("from Employee emp  where emp.id=:id")
				.setInteger("id", id).list();

	}

/*	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Project> findProjectbyAccount(int id) {
		List<Project> list = null;
		System.out.println("inside dao");
		list = (List<Project>) getCurrentSession()
				.createQuery("from Project proj  where proj.id=:id")
				.setInteger("id", id).list();

		return list;

	}*/

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ShiftDetails> findShiftByTrack(int trackId) {
		System.out.println("inside dao");
		return (List<ShiftDetails>) getCurrentSession()
				.createQuery(
						"from ShiftDetails q where q.track.trackId=:trackId")
				.setInteger("trackId", trackId).list();
	}
/*
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Employee> findEmployeebyTrack(int trackId) {
		System.out.println("inside dao");
		List<Employee> list = (List<Employee>) getCurrentSession()
				.createQuery("from Employee emp  where emp.trackId=:trackId")
				.setInteger("trackId", trackId).list();
		return list;
	}
*/
	
	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Employee> findEmployeeRolesByTrackId(Integer trackId) {
		List<Employee> empList=Collections.EMPTY_LIST;
		
		List<EmployeeTrackMapping> empProject= getCurrentSession().createCriteria(EmployeeTrackMapping.class)
				.add(Restrictions.eq("track.id", trackId))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		if(empProject!=null && !empProject.isEmpty()){
			empList = new ArrayList<>();
			for(EmployeeTrackMapping empProj:empProject){
				empList.add(empProj.getEmployee());
			}
			Collections.sort(empList);
		}
		
		
		return empList;
	
	}*/
	/*@Override
	@Transactional
	public int saveShiftDetails(List<ShiftPlanDetail> shiftPlan) {
		int id = 0;
		Session session = sessionFactory.openSession();
		for (int i = 0; i < shiftPlan.size(); i++) {
			try {
				
				session.saveOrUpdate(shiftPlan.get(i));
				id = 1;
			} catch (Exception exception) {
				exception.printStackTrace();
			} finally {
				// getCurrentSession().close();
			}

		}
		return id;
	}*/

	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ShiftPlanDetail> GetShiftDetails(int id, int monthId,
			int trackId, int spocId) {
		System.out.println("inside dao");
		List<ShiftPlanDetail> list = (List<ShiftPlanDetail>) getCurrentSession()
				.createQuery(
						"from ShiftPlanDetail details where details.key.month=:id and details.track.trackId=:trackId and details.key.year=:yearId and details.spocId=:spocId ")
				.setInteger("id", monthId).setInteger("trackId", trackId)
				.setInteger("yearId", id).setInteger("spocId", spocId).list();
		return list;

		
		 * @Override
		 * 
		 * @Transactional public void findShiftDetailsByProjectId(int id) {
		 * Query q = getCurrentSession().createQuery(
		 * "Delete from ShiftDetails  shiftdetails where shiftdetails.project.id =:id"
		 * ).setInteger("id", id) ; q.executeUpdate(); }
		 
	}*/
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ShiftPlanDetail> getShiftDetails(int id, int monthId,
			int trackId) {
		System.out.println("inside dao");
		try{
		List<ShiftPlanDetail> list = (List<ShiftPlanDetail>) getCurrentSession()
				.createQuery(
						"select details from ShiftPlanDetail details INNER JOIN details.otherTrack otherTrack where details.key.month=:id and details.key.year=:yearId and otherTrack.trackAssignedTo.trackId=:assignedTo")
				.setInteger("id", monthId)
				.setInteger("yearId", id).setInteger("assignedTo", trackId).list();
		return list;
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<ShiftPlanDetail>();
		}
		/*
		 * @Override
		 * 
		 * @Transactional public void findShiftDetailsByProjectId(int id) {
		 * Query q = getCurrentSession().createQuery(
		 * "Delete from ShiftDetails  shiftdetails where shiftdetails.project.id =:id"
		 * ).setInteger("id", id) ; q.executeUpdate(); }
		 */
	}

	/*@Override
	@Transactional
	public String deleteMonthShiftPlan(int monthId, int trackId, int year) {
		System.out.println("insideDaoaa");
		try{
		Query query = getCurrentSession()
				.createQuery(
						"Delete from ShiftPlanner shiftPlan where shiftPlan.monthId=:id and shiftPlan.year=:yearId and shiftPlan.trackId=:trackId")
				.setInteger("id", monthId).setInteger("trackId", trackId)
				.setInteger("yearId", year);
		int numberOfDelete = query.executeUpdate();
		return "sucesfully deleted"+numberOfDelete;
		}catch(Exception e){
		e.printStackTrace();
		return "unsucessfull delete";
		}
		
	}*/

	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer saveShiftPlan(ShiftPlanner shiftPlanner) throws SQLException{
	
		getCurrentSession().save(shiftPlanner);    
        return shiftPlanner.getId();
	}

	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public int getShiftPlanByMonth(int trackId, int monthId, int spocId,
			int year) {
		// TODO Auto-generated method stub
		return (Integer) getCurrentSession()
				.createQuery(
						"select shiftPlanner.id from ShiftPlanner shiftPlanner where shiftPlanner.monthId=:month and shiftPlanner.year=:year and shiftPlanner.requestingEmpId=:spocId and shiftPlanner.trackId.trackId=:trackId")
				.setInteger("month", monthId).setInteger("year", year)
				.setInteger("spocId", spocId).setInteger("trackId", trackId)
				.uniqueResult();

	}
*/
	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void updateLastModifiedDate(ShiftPlanner shiftPlan, int shiftPlanId) {
		try{
		int id = getCurrentSession()
				.createQuery(
						"update ShiftPlanner shiftPlanner set shiftPlanner.reqDate =:reqDate where shiftPlanner.id=:shiftPlanId")
				.setDate("reqDate", shiftPlan.getReqDate())
				.setInteger("shiftPlanId", shiftPlanId).executeUpdate();
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
	}*/
	
	

	@Override
	@Transactional
	public List<Employee> getEmployeeByTrackId(Integer trackId) {
		
		
	List<Integer> empIdList =(List<Integer>) getCurrentSession().createSQLQuery("select user_id FROM tms_dev.employee_track_table where track_Id="+trackId).list();
		
		if(!empIdList.isEmpty()){
	Query query = getCurrentSession().createQuery("from Employee e where e.id in (:ids)").setParameterList("ids", empIdList);
		
  	List<Employee> employeeList = query.list();
  	return employeeList;
		}
		else{
			List<Employee> employeeList=Collections.EMPTY_LIST;
			return employeeList;
		}
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Project> getProjectByAccount(int empId,int accountId) {
		List<Project> list = (List<Project>)getCurrentSession().createQuery("select proj from Employee emp JOIN emp.empProjMapping empprojmapping Join empprojmapping.project proj where emp.id=:id and proj.account.id=:accountId").setInteger("id", empId).setInteger("accountId", accountId).list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Track> findTrackbyProject(int projectId) {
		List<Track> list = (List<Track>)getCurrentSession().createQuery(" from Track track where track.project.id=:projectId").setInteger("projectId", projectId).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ShiftPlanner> findShiftDetailByProjectId(int year, int monthId,
			int projectId,Integer spocId) {
		List<ShiftPlanner> list = new ArrayList<ShiftPlanner>();
		try{
		 list = (List<ShiftPlanner>)getCurrentSession().createQuery("from ShiftPlanner shiftPlanner "
		 		+ "where shiftPlanner.year=:year "
		 		+ "and shiftPlanner.monthId=:monthId "
		 		+ "and shiftPlanner.projectId.id=:projectId")
				.setInteger("year", year).setInteger("monthId", monthId)
				.setInteger("projectId", projectId).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
				return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ShiftPlanner> getMonthlyShiftPlan(int monthId, int trackId,
			int year, Integer employeeId) {
		List<ShiftPlanner> list = (List<ShiftPlanner>)getCurrentSession().createQuery("from ShiftPlanner shiftPlanner where shiftPlanner.requestingEmpId=:empId and shiftPlanner.year=:year and shiftPlanner.monthId=:monthId and shiftPlanner.trackId.trackId=:trackId")
		.setInteger("year", year).setInteger("monthId", monthId).setInteger("trackId", trackId).setInteger("empId", employeeId).list();
		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.quinnox.flm.tms.module.shiftplanner.dao.ShiftPlannerDao#delete(java.util.List)
	 */
	/*@Transactional
	@Override
	public void delete(List<Integer> previousShiftPlan) {
		for (Integer shiftPlannerId : previousShiftPlan) {
			Object persistentInstance = getCurrentSession().load(ShiftPlanner.class, shiftPlannerId);
			if (persistentInstance != null) {
				getCurrentSession().delete(persistentInstance);
			}
			}
		}*/

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public void deleteShiftPlanDetails(List<ShiftPlanDetail> shiftPlanDetails) {
			
		try{
		
		   for (ShiftPlanDetail shiftPlanDetail : shiftPlanDetails) {
			   getCurrentSession().delete(shiftPlanDetail);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//session.close();
		}
		
			
	
		
	}
	

	@Override
	@Transactional
	public int deleteMasterRecord(Integer spocId, int trackId,
			int projectId, int monthId, int year) {
		int id=0;
		try{
		getCurrentSession().createSQLQuery(" DELETE shift_planner "
				+ "FROM shift_planner LEFT JOIN shiftplandetails"
				+ " ON shiftplandetails.Shift_plan_id=shift_planner.Shift_plan_id "
				+ "WHERE shiftplandetails.Shift_plan_id IS NULL").executeUpdate();
		
		id =1;
		}catch(Exception e){
			e.printStackTrace();
			id =0;
		}
		return id;
	}

	@Override
	@Transactional
	public int deleteShiftPlannerDetails(List<ShiftPlanner> previousShiftPlan) {
		try{
			
			   for (ShiftPlanner shiftPlanDetail : previousShiftPlan) {
				  getCurrentSession().delete(shiftPlanDetail);
				  
			}
			   return 1;
			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}finally{
				//session.close();
			}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ShiftPlanner> getStatusOfShiftPlanner(int projectId, int monthId, int year,
			Integer spocId) {
				return (List<ShiftPlanner>) getCurrentSession().createQuery("from ShiftPlanner shiftPlanner where shiftPlanner.projectId.id=:projectId and shiftPlanner.monthId=:monthId and shiftPlanner.year=:year")
				.setInteger("projectId", projectId)
				.setInteger("monthId", monthId).setInteger("year", year).list();
		
				
	}

	@Override
	@Transactional
	public List<Shift> getPrevilegedandHolidayShift() {
		// TODO Auto-generated method stub
		return (List<Shift>)getCurrentSession().createQuery("from Shift sh where sh.enumValues=:Privileged").setString("Privileged", "Privileged").list();
	}

	
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

