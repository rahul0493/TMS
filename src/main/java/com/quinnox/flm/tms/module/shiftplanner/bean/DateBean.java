package com.quinnox.flm.tms.module.shiftplanner.bean;

import java.util.List;

public class DateBean {

	private int date;
	private List<ShiftPlan> ShiftPlans;

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public List<ShiftPlan> getShiftPlans() {
		return ShiftPlans;
	}

	public void setShiftPlans(List<ShiftPlan> ShiftPlans) {
		this.ShiftPlans = ShiftPlans;
	}

	@Override
	public String toString() {
		return "DateBean [date=" + date + ", ShiftPlans=" + ShiftPlans + "]";
	}

}
