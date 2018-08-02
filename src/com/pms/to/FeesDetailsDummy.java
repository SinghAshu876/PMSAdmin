package com.pms.to;

import java.sql.Date;

public class FeesDetailsDummy {

	private String month;
	private Integer feesPaid;
	private Integer discount;
	private Date postingDate;

	public String getMonth() {
		return month;
	}

	public Integer getFeesPaid() {
		return feesPaid;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setFeesPaid(Integer feesPaid) {
		this.feesPaid = feesPaid;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
}
