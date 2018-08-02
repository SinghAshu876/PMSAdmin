package com.pms.validator;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pms.entity.User;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SearchValidator {

	private  Logger LOG = Logger.getLogger(getClass());
	// private String messageNullValue = " is blank";
	// private String lengthMesssage = "value greater than ";

	public HashMap<String, String> validate(User user) {
		LOG.info("validate ENTRY");
		HashMap<String, String> emptyValue = new HashMap<String, String>();
		LOG.info("validate EXIT");
		return emptyValue;

	}
}
