package com.pms.validator;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pms.entity.User;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class UserValidator {

	private  Logger LOG = Logger.getLogger(getClass());
	private String messageNullValue = " is blank";
	private String lengthMesssage = "value greater than ";
	private String lengthLessMesssage = "value less than ";
	private String notCorrectMesssage = "value not correct ";

	public HashMap<String, String> validate(User user) {
		LOG.info("validate ENTRY");
		HashMap<String, String> emptyValue = new HashMap<String, String>();
		if (user.getId() == null) {
			emptyValue.put("SL NO", messageNullValue);
		}
		if (user.getCustomerName() == null || user.getCustomerName().isEmpty()) {
			emptyValue.put("Customer Name", messageNullValue);
		}
		if (user.getConnectionCharge() == null || user.getConnectionCharge().isEmpty()) {
			emptyValue.put("Connection Charge", messageNullValue);
		}
		if (user.getMobileNumber() == null || user.getMobileNumber().isEmpty()) {
			emptyValue.put("Mobile number", messageNullValue);
		}
		if (user.getDoc() == null) {
			emptyValue.put("Date of Connection", messageNullValue);
		}
		if (user.getDocValidate() != null) {
			if (String.valueOf(user.getDocValidate()).length() > 10 || String.valueOf(user.getDocValidate()).length() < 10) {
				emptyValue.put("Date of Connection is wrong", notCorrectMesssage);
			}

		}
		if (user.getFeesHistory().getFees() == null) {
			emptyValue.put("Fee", messageNullValue);
		}
		if (user.getQrNo() == null) {
			emptyValue.put("QR NO", messageNullValue);
		}
		if (user.getQrNo() != null) {
			if (String.valueOf(user.getQrNo()).length() > 4)
				emptyValue.put("QR NO", lengthMesssage + 4);
		}
		if (user.getFeesHistory().getFees() != null) {
			if (String.valueOf(user.getFeesHistory().getFees()).length() > 4) {
				emptyValue.put("Fee", lengthMesssage + 4);
			}
		}
		if (user.getMobileNumber() != null || !user.getMobileNumber().isEmpty()) {
			if (user.getMobileNumber().length() > 10) {
				emptyValue.put("Mobile Number", lengthMesssage + 10);
			}
			if (user.getMobileNumber().length() < 10) {
				emptyValue.put("Mobile Number", lengthLessMesssage + 10);
			}
		}
		if (user.getSetTopBoxNumber() != null || !user.getSetTopBoxNumber().isEmpty()) {
			if (user.getSetTopBoxNumber().length() > 20)
				emptyValue.put("Set Top Box Number", lengthMesssage + 20);
		}

		LOG.info("validate EXIT");
		return emptyValue;
		

	}

}
