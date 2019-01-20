package com.pms.service.impl;

import org.apache.log4j.Logger;

import com.pms.dao.ApplicationPropsDAO;
import com.pms.service.ApplicationPropsService;

public class ApplicationPropsServiceImpl implements ApplicationPropsService {
	
	private Logger LOG = Logger.getLogger(getClass());

	private ApplicationPropsDAO  appProp = new ApplicationPropsDAO();

	@Override
	public int updateProperty(String text) {
		LOG.info("updateProperty");
		return appProp.updateProperty(text);
	}

	@Override
	public String fetchProperty(String key) {		
		return appProp.fetchProperty(key);
	}
}
