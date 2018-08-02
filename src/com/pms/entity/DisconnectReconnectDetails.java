package com.pms.entity;

import java.sql.Date;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class DisconnectReconnectDetails {

	private String id;
	private Integer userId;
	private Date dateOfDisconnection;
	private Date dateOfReconnection;

	public Date getDateOfDisconnection() {
		return dateOfDisconnection;
	}

	public void setDateOfDisconnection(Date dateOfDisconnection) {
		this.dateOfDisconnection = dateOfDisconnection;
	}

	public Date getDateOfReconnection() {
		return dateOfReconnection;
	}

	public void setDateOfReconnection(Date dateOfReconnection) {
		this.dateOfReconnection = dateOfReconnection;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
