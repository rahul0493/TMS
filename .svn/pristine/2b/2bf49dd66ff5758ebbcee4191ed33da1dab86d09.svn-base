/**
 * 
 */
package com.quinnox.flm.tms.module.shiftplanner.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quinnox.flm.tms.generic.dao.impl.GenericDaoImpl;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.shiftplanner.dao.MonthlyCabRequestDao;

/**
 * @author RahulY
 *
 */
@Repository("MonthlycabRequestDao")
public class MonthlyCabRequestDaoImpl extends GenericDaoImpl<EmpCabRequest, Integer> implements MonthlyCabRequestDao {

	
	public MonthlyCabRequestDaoImpl() {
		super(EmpCabRequest.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer saveCabRequest(EmpCabRequest cabRequest) {
		return (Integer) getCurrentSession().save(cabRequest);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Employee> isEmployeeRequiredCab(int id) {
		System.out.println("inside dao");
		return (List<Employee>) getCurrentSession()
				.createQuery("from Employee emp  where emp.id=:id")
				.setInteger("id", id).list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ShiftDetails> isShiftRequired(int shiftId,int trackId) {
		System.out.println("inside dao");
		return (List<ShiftDetails>) getCurrentSession()
				.createQuery("from ShiftDetails shiftDetails where shiftDetails.shift.shiftId=:shiftId and shiftDetails.track.trackId=:trackId")
				.setInteger("shiftId", shiftId).setInteger("trackId", trackId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Integer> deletePreviousCabRequest(int month, int year, int empId) {
		System.out.println("inside dao");
		return (List<Integer>) getCurrentSession()
				.createQuery("select empCabRequest.id from EmpCabRequest empCabRequest where MONTH(empCabRequest.reqDate)=:month and YEAR(empCabRequest.reqDate)=:year and empCabRequest.user.id=:id")
				.setInteger("month", month).setInteger("year", year).setInteger("id", empId).list();
		
	}

	@Override
	@Transactional
	public String DeleteFutureCabrequest(Integer integer) {
		Query query = getCurrentSession()
				.createQuery(
						"Delete from EmpCabRequestDetails details where details.empCabRequest.id=:id and details.scheduleDate > CURDATE()")
				.setInteger("id", integer);
		query.executeUpdate();
		return "Delete Succesfull";
	}
	@Override
	public EmployeeAddress getOfficeAddress() {
		try{
			return (EmployeeAddress) getCurrentSession().createQuery("from EmployeeAddress add where add.alias=:alias").setString("alias", "office").uniqueResult();
			
			
			
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
	}
}
