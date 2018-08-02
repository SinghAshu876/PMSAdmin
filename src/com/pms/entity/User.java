package com.pms.entity;

import java.util.Date;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class User {

	private Integer id;
	private String mobileNumber;
	private String setTopBoxNumber;
	private String cafNumber;
	private String customerName;
	private Integer qrNo;
	private String street;
	private String sector;
	private String connectionCharge;
	private FeesHistory feesHistory;
	//private String fee;
	//private String feeCode;
	private String active;
	private Date doc;
	private String backDues;
	private String docValidate;

	public Date getDoc() {
		return doc;
	}

	public void setDoc(Date doc) {
		this.doc = doc;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public String getCafNumber() {
		return cafNumber;
	}

	public void setCafNumber(String casNumber) {
		this.cafNumber = casNumber;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getQrNo() {
		return qrNo;
	}

	public void setQrNo(Integer qrNo) {
		this.qrNo = qrNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getConnectionCharge() {
		return connectionCharge;
	}

	public void setConnectionCharge(String connectionCharge) {
		this.connectionCharge = connectionCharge;
	}

	/*public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}*/

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getSetTopBoxNumber() {
		return setTopBoxNumber;
	}

	public void setSetTopBoxNumber(String setTopBoxNumber) {
		this.setTopBoxNumber = setTopBoxNumber;
	}

	/*public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}*/

	public String getBackDues() {
		return backDues;
	}

	public void setBackDues(String backDues) {
		this.backDues = backDues;
	}

	public String getDocValidate() {
		return docValidate;
	}

	public void setDocValidate(String docValidate) {
		this.docValidate = docValidate;
	}

	public FeesHistory getFeesHistory() {
		return feesHistory;
	}

	public void setFeesHistory(FeesHistory feesHistory) {
		this.feesHistory = feesHistory;
	}

}
