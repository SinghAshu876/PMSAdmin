package com.pms.service;

public interface ApplicationPropsService {

	public int updateProperty(String text, String key);

	public String fetchProperty(String key);
}
