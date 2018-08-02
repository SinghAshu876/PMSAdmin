package com.pms.entity;

public class ApplicationUsers {

	private Integer id;
	private String userName;
	private String password;
	private String userAlias;
	private String userGroup;

	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

}
