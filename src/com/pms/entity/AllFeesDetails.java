package com.pms.entity;

import java.sql.Date;

public class AllFeesDetails {

	private Integer id;
	private String month;
	private Integer year;
	private Date feesInsertionDate;
	private String feesPaid;
	private Integer discount;
	private Integer monthSequence;

	public Integer getId() {
		return id;
	}

	public String getMonth() {
		return month;
	}

	public Integer getYear() {
		return year;
	}

	public Date getFeesInsertionDate() {
		return feesInsertionDate;
	}

	public String getFeesPaid() {
		return feesPaid;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setFeesInsertionDate(Date feesInsertionDate) {
		this.feesInsertionDate = feesInsertionDate;
	}

	public void setFeesPaid(String feesPaid) {
		this.feesPaid = feesPaid;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getMonthSequence() {
		return monthSequence;
	}

	public void setMonthSequence(Integer monthSequence) {
		this.monthSequence = monthSequence;
	}
}
