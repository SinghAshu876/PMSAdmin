package com.pms.entity;

import java.util.Date;



public class FeesHistory {

	private Integer id;
	private Date fromDate;
	private Date toDate;
	private Integer fees;
	
	/**DUMMY PROPERTIES TO HOLD EXTRACTED VALUE*/
	private Integer fromYear;
	private Integer toYear;
	private Integer toMonth;
	private Integer fromMonth;
	
	public Integer getFromYear() {
		return fromYear;
	}

	public void setFromYear(Integer fromYear) {
		this.fromYear = fromYear;
	}

	public Integer getToYear() {
		return toYear;
	}

	public void setToYear(Integer toYear) {
		if(toYear.intValue()==0){
			this.toYear = null;
		}else{
			this.toYear = toYear;
		}
		
	}

	public Integer getToMonth() {
		return toMonth;
	}

	public void setToMonth(Integer toMonth) {
		if(toMonth.intValue()==0){
			this.toMonth = null;
		}else{
			this.toMonth = toMonth;
		}
	}

	public Integer getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(Integer fromMonth) {
		this.fromMonth = fromMonth;
	}

	public Integer getId() {
		return id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public Integer getFees() {
		return fees;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public void setFees(Integer fees) {
		this.fees = fees;
	}

}
