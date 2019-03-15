/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.shiftplanner.dao.ShiftAllowanceDao;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftAllowanceDetails;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftPlanner;

/**
 * @author RahulY
 *
 */
@Repository("shiftAllowanceDao")
@Transactional
@SuppressWarnings("unchecked")
public class ShiftAllwanceDaoImpl extends GenericDaoImpl<Employee, Integer> implements ShiftAllowanceDao {

	public ShiftAllwanceDaoImpl() {
		super(Employee.class);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public List<ShiftPlanner> GenerateShiftAllowance(int previousMonth,
			int currentYear) {
		// TODO Auto-generated method stub
		return (List<ShiftPlanner>)getCurrentSession().createQuery("from ShiftPlanner  shiftPlanner where shiftPlanner.monthId=:previousMonth and shiftPlanner.year=:currentYear and shiftPlanner.aprroved=true")
				.setInteger("previousMonth", previousMonth).setInteger("currentYear", currentYear).list();
	}

	@Override
	
	public String approveShiftDetails(int monthId, int year, int trackId,
			int spocId, int projectId) {
		try {
			int id = getCurrentSession().createQuery("update ShiftPlanner shiftPlanner set shiftPlanner.aprroved =true"
					+ " where shiftPlanner.trackId.trackId=:trackId and shiftPlanner.monthId=:monthId "
					+ "and shiftPlanner.year=:year and shiftPlanner.requestingEmpId=:spocId and "
					+ "shiftPlanner.projectId.id=:projectId")
		   .setInteger("monthId", monthId).setInteger("year", year)
		   .setInteger("trackId", trackId).setInteger("spocId", spocId).setInteger("projectId", projectId).executeUpdate();
			return "updated sucessfully"+id;
			
	    } catch (Exception e) {
			e.printStackTrace();
	    	return "error while updating";
	    }
	}

	@Override
	public List<ShiftPlanner> getShiftPlannerToGenerateShiftAllowance(
			int monthId, int projectId, int trackId, int year, int spocId) {
		List<ShiftPlanner> shiftPlannerList = new ArrayList<ShiftPlanner>();
		try{
			shiftPlannerList= (List<ShiftPlanner>)getCurrentSession().createQuery("from ShiftPlanner  shiftPlanner where shiftPlanner.monthId=:monthId and shiftPlanner.year=:year and shiftPlanner.requestingEmpId=:spocId "
					+ "and shiftPlanner.trackId.trackId=:trackId and shiftPlanner.projectId.id=:projectId and shiftPlanner.aprroved=true")
				.setInteger("monthId", monthId).setInteger("year", year)
				.setInteger("trackId", trackId).setInteger("spocId", spocId).setInteger("projectId", projectId).list();
		return shiftPlannerList;
	}catch(Exception e){
		e.printStackTrace();
		return shiftPlannerList;
	}
	}
		@Override
		public List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowance(
				int monthId, int projectId, int trackId, int year, int spocId) {
			List<ShiftPlanner> shiftPlannerList = new ArrayList<ShiftPlanner>();
			try{
				shiftPlannerList= (List<ShiftPlanner>)getCurrentSession().createQuery("from ShiftPlanner  shiftPlanner where shiftPlanner.monthId=:monthId and shiftPlanner.year=:year and shiftPlanner.requestingEmpId=:spocId "
						+ "and shiftPlanner.trackId.trackId=:trackId and shiftPlanner.projectId.id=:projectId")
					.setInteger("monthId", monthId).setInteger("year", year)
					.setInteger("trackId", trackId).setInteger("spocId", spocId).setInteger("projectId", projectId).list();
			return shiftPlannerList;
		}catch(Exception e){
			e.printStackTrace();
			return shiftPlannerList;
		}
		
	
	}

	@Override
	
	public void SaveShiftAllwance(List<ShiftAllowanceDetails> shiftAllowanceDetailList) {
	System.out.println("saving Allowance");
	try{	
	for (ShiftAllowanceDetails shiftAllowanceDetails : shiftAllowanceDetailList) {
		
			getCurrentSession().persist(shiftAllowanceDetails);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}

	
	@Override
	public List<HolidayList> getHolidayListByMonth(int monthId, int year) {
	 
		return (List<HolidayList>)getCurrentSession().
				createQuery("from HolidayList holiday where MONTH(holiday.holidayDate)=:month and holiday.year=:year")
				.setInteger("month", monthId).setInteger("year", year).list();
	}


	@Override
	public List<ShiftAllowanceDetails> getAllowance(Integer from, Integer to,
			Integer accountId, Integer projectId) {
		String query=null;
		Criteria criteria = getCurrentSession().createCriteria(ShiftAllowanceDetails.class);
		/* Create object of Conjunction */
        Conjunction objConjunction = Restrictions.conjunction();
		if(from!=null){
			objConjunction.add(Restrictions.ge("monthId",from));
		}
		if(to!=null){
			objConjunction.add(Restrictions.le("monthId",to));
		}
		if(projectId!=null){
			objConjunction.add(Restrictions.eqOrIsNull("projectId.id",projectId));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
		criteria.add(objConjunction);
		return criteria.list();
        
		
		
	}


	@Override
	public List<Shift> getShiftDetails() {
		// TODO Auto-generated method stub
		return (List<Shift>)getCurrentSession().createQuery("from Shift").list();
	}


	@Override
	public List<ShiftPlanner> getShiftPlannerToCalculateShiftAllowanceForSpoc(
			int monthId, int projectId, int trackId, int year, int spocId) {
		List<ShiftPlanner> shiftPlannerList = new ArrayList<ShiftPlanner>();
		try{
			shiftPlannerList= (List<ShiftPlanner>)getCurrentSession().createQuery("from ShiftPlanner  shiftPlanner where shiftPlanner.monthId=:monthId and shiftPlanner.year=:year "
					+ "and shiftPlanner.trackId.trackId=:trackId and shiftPlanner.projectId.id=:projectId")
				.setInteger("monthId", monthId).setInteger("year", year)
				.setInteger("trackId", trackId).setInteger("projectId", projectId).list();
		return shiftPlannerList;
	}catch(Exception e){
		e.printStackTrace();
		return shiftPlannerList;
	}
	}
}
