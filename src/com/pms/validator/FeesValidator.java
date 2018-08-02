package com.pms.validator;

import java.util.HashMap;

import com.pms.entity.AllFeesDetails;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class FeesValidator {
	
	
	private String messageNullValue = " is blank";
	private String lengthMesssage = "value greater than ";

	public HashMap<String, String> validate(AllFeesDetails feesDetails) {
		HashMap<String, String> emptyValue = new HashMap<String, String>();
		if (feesDetails.getYear() == null) {
			emptyValue.put("Year", messageNullValue);
		}
		if (feesDetails.getFeesPaid().isEmpty()) {
			emptyValue.put("fees", messageNullValue);
		}
		if (!feesDetails.getFeesPaid().isEmpty() && feesDetails.getFeesPaid().length() > 5) {
			emptyValue.put("fees", lengthMesssage + 4);
		}
		return emptyValue;

	}

}
