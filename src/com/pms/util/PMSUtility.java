package com.pms.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.pms.entity.ChannelDetails;
import com.pms.entity.User;
import com.pms.enums.util.Month;
import com.pms.enums.util.StaticCodes;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PMSUtility implements ApplicationConstants {
	private static Logger LOG = Logger.getLogger(PMSUtility.class);

	public static Integer getMonthPosition(String month) {
		if (month.equalsIgnoreCase("January")) {
			return 3;
		} else if (month.equalsIgnoreCase("February")) {
			return 4;
		} else if (month.equalsIgnoreCase("March")) {
			return 5;
		} else if (month.equalsIgnoreCase("April")) {
			return 6;
		} else if (month.equalsIgnoreCase("May")) {
			return 7;
		} else if (month.equalsIgnoreCase("June")) {
			return 8;
		} else if (month.equalsIgnoreCase("July")) {
			return 9;
		} else if (month.equalsIgnoreCase("August")) {
			return 10;
		} else if (month.equalsIgnoreCase("September")) {
			return 11;
		} else if (month.equalsIgnoreCase("October")) {
			return 12;
		} else if (month.equalsIgnoreCase("November")) {
			return 13;
		} else if (month.equalsIgnoreCase("December")) {
			return 14;
		}
		return null;

	}

	public static Integer initialiseCurrentYear() {
		//Integer year = null;
		//SimpleDateFormat sdf = new SimpleDateFormat(YEAR_PATTERN);
		//String formattedDate = sdf.format(new Date());
		//year = Integer.valueOf(formattedDate);
		//return year;
		return LocalDate.now().getYear();

	}

	public static java.sql.Date convertToSqlDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}
	
	public static void main(String args[]) {
		Date d = new Date();
		System.out.println(getYearString(d));
	}

	public static Integer getYear(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d); 
		//SimpleDateFormat sdf = new SimpleDateFormat(YEAR_PATTERN);
		//return Integer.valueOf(sdf.format(d));
		return Integer.valueOf(cal.get(Calendar.YEAR));
	}

	public static String getYearString(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d); 
		//SimpleDateFormat sdf = new SimpleDateFormat(YEAR_PATTERN);
		//return sdf.format(d);
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	public static String getMonth(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat(MONTH_PATTERN);
		return sdf.format(d).toUpperCase();
	}

	public static Date getPreviousMonth(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static boolean checkYear(Object item, User user) {
		LOG.info("checking year");
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		Date date = user.getDoc();
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_PATTERN);
		String yearsString = sdf.format(date);
		Integer year = Integer.valueOf(yearsString);
		if (!year.equals(item)) {
			return true;
		}
		return false;
	}

	public static Integer getMonthSequence(String month) {
		int sequence = 0;
		Month[] months = Month.values();
		for (int i = 1; i < months.length; i++) {
			if (month.equalsIgnoreCase(months[i].toString())) {
				sequence = i;
				break;
			}
		}
		return sequence;
	}

	public static List<Integer> createYearsBasedOnConnectionDate(User user2) {
		List<Integer> newYearsList = new ArrayList<Integer>();
		try {
			Integer[] years = StaticCodes.yearArray;
			Date doc = user2.getDoc();
			SimpleDateFormat sdf = new SimpleDateFormat(YEAR_PATTERN);
			String yearsString = sdf.format(doc);
			int year = Integer.valueOf(yearsString);

			int position = 0;
			for (int i = 0; i < years.length; i++) {
				if (years[i] == year) {
					position = i;
					break;
				}
			}
			for (int i = position; i < years.length; i++) {
				newYearsList.add(years[i]);
			}
			return newYearsList;
		} catch (Exception e) {
			return newYearsList;
		}
	}

	public static List<String> createMonthsBasedOnConnectionDate(User user) {
		List<String> newMonthsList = new ArrayList<String>();
		try {
			Month[] monthsArray = Month.values();
			Date doc = user.getDoc();
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			String monthString = sdf.format(doc);
			int month = Integer.valueOf(monthString);

			newMonthsList.add(monthsArray[0].name().toString());
			for (int i = month; i < monthsArray.length; i++) {
				newMonthsList.add(monthsArray[i].name().toString());
			}
			return newMonthsList;
		} catch (Exception e) {
			return newMonthsList;
		}

	}

	public static String getMonthStringFromValue(int monthNumber) {

		if (monthNumber < 1 || monthNumber > 12) {
			throw new IllegalArgumentException("Contact Developer");
		}
		switch (monthNumber) {
		case 1:
			return "JANUARY";
		case 2:
			return "FEBRUARY";
		case 3:
			return "MARCH";
		case 4:
			return "APRIL";
		case 5:
			return "MAY";
		case 6:
			return "JUNE";
		case 7:
			return "JULY";
		case 8:
			return "AUGUST";
		case 9:
			return "SEPTEMBER";
		case 10:
			return "OCTOBER";
		case 11:
			return "NOVEMBER";
		case 12:
			return "DECEMBER";
		default:
			throw new IllegalArgumentException("Contact Developer");
		}
	}

	public static Date getMonthYearValue(String month, String year, int increaseOrDecrease) {
		Date newMonthDate = null;
		try {
			Calendar cal = Calendar.getInstance();
			Date date = new SimpleDateFormat(MONTH_PATTERN, Locale.ENGLISH).parse(month);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int monthValue = calendar.get(Calendar.MONTH);
			cal.set(Integer.valueOf(year), monthValue, 1);
			cal.add(Calendar.MONTH, increaseOrDecrease);
			newMonthDate = cal.getTime();

		} catch (ParseException e) {
			LOG.error("PARSE EXCEPTION FOR DATE IN getMonthYearValue, PLEASE CONTACT DEVELOPER");
		}
		return newMonthDate;

	}

	/*
	 * public static String findFeeCode(Object fee) { String feeCode = "";
	 * LOG.info("findFeeCode"); if (fee != null && fee instanceof Integer) { String
	 * feeString = String.valueOf(fee); for (int i = 0; i < feeString.length(); i++)
	 * { Character c = feeString.charAt(i); feeCode = feeCode +
	 * StaticCodes.codeMap.get(c); } } else if (fee != null && fee instanceof
	 * String) { String feeString = (String) fee; for (int i = 0; i <
	 * feeString.length(); i++) { Character c = feeString.charAt(i); feeCode =
	 * feeCode + StaticCodes.codeMap.get(c); } } LOG.info("findFeeCode : " +
	 * feeCode); return feeCode;
	 * 
	 * }
	 */

	public static int calculateFinalAmount(int gstRate, double amount, ArrayList<ChannelDetails> selectedChannelsList) {
		LOG.info("intial amount " + amount);
		int sdChannelsCount = 0;
		int hdChannelsCount = 0;
		int weightedCount = 0;
		for (ChannelDetails channelDetails : selectedChannelsList) {
			if (channelDetails.getChannelType().equals(SD)) {
				sdChannelsCount++;
				weightedCount = weightedCount + 1;
			} else if (channelDetails.getChannelType().equals(HD)) {
				hdChannelsCount++;
				weightedCount = weightedCount + 2;				
			}
		}

		LOG.info("sdChannelsCount " + sdChannelsCount);
		LOG.info("hdChannelsCount " + hdChannelsCount);
		LOG.info("weightedCount " + weightedCount);
		
		for (int i = 1; i <= weightedCount; i++) {
			if (i % 25 == 1) {
				amount = amount + 20;
			}
		}
		LOG.info("amount " + amount);



		double gstCalculatedRate = 0;
		LOG.info("calculate GST for " + amount + " rate " + gstRate);
		gstCalculatedRate = amount + ((amount * gstRate) / 100.0);
		LOG.info("calculated GST for " + gstCalculatedRate);
		int roundedToNearestInteger = (int) Math.ceil(gstCalculatedRate);
		LOG.info("roundedToNearestInteger  " + roundedToNearestInteger);
		return roundedToNearestInteger;
	}


	public static Date getPreviousMonthLastDate(Date date) {
		LOG.info("getPreviousMonthLastDate : ENTRY" + date);
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(date);
		aCalendar.add(Calendar.MONTH, -1);
		aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		LOG.info("getPreviousMonthLastDate : EXIT" + lastDateOfPreviousMonth);
		return lastDateOfPreviousMonth;
	}
	
	public static String getDecimalFormat(double number) {
		 DecimalFormat df = new DecimalFormat("#0.00");
	     return (df.format(number));
	}

	public static Date getCurrentMonthFirstDate(Date date) {
		LOG.info("getCurrentMonthFirstDate : ENTRY" + date);
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(date);
		aCalendar.set(Calendar.DATE, 1);
		Date firstDateOfCurrentMonth = aCalendar.getTime();
		LOG.info("getCurrentMonthFirstDate EXIT : " + firstDateOfCurrentMonth);
		return firstDateOfCurrentMonth;
	}

	public static Date createDateObject(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getTime();
	}

	public static Date createDateObject(Integer endYear, String month) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(endYear, getMonthSequence(month) - 1, 1);
		Date date = calendar.getTime();
		return date;
	}

	public static Date createDateObject(Integer endYear, String month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(endYear, getMonthSequence(month) - 1, day);
		Date date = calendar.getTime();
		return date;
	}

	public static float[] getColumnWidths4PDF(String[] headers, String fileName) {
		float[] columnWidth = new float[headers.length];
		if (fileName.equals(SETTOP_BOX_PDF)) {
			for (int i = 0; i < headers.length; i++) {
				if (headers[i] == "ID") {
					columnWidth[i] = 3f;
				}
				if (headers[i] == "NAME") {
					columnWidth[i] = 8f;
				}
				if (headers[i] == "CAFNO") {
					columnWidth[i] = 8f;
				}
				if (headers[i] == "MOBILE") {
					columnWidth[i] = 6f;
				}
				if (headers[i] == "QTR") {
					columnWidth[i] = 3f;
				}
				if (headers[i] == "STR") {
					columnWidth[i] = 3f;
				}
				if (headers[i] == "SET TOP BOX") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "SEC") {
					columnWidth[i] = 3f;
				}
			}
		} else if (fileName.equals(SIMPLE_PRINT_PDF)) {
			for (int i = 0; i < headers.length; i++) {
				if (headers[i] == "ID") {
					columnWidth[i] = 3f;
				}
				if (headers[i] == "NAME") {
					columnWidth[i] = 8f;
				}
				if (headers[i] == "CAFNO") {
					columnWidth[i] = 6f;
				}
				if (headers[i] == "SMART CARD NO") {
					columnWidth[i] = 8f;
				}
				if (headers[i] == "MOBILE") {
					columnWidth[i] = 6f;
				}
				if (headers[i] == "QTR") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "STR") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "SET TOP BOX") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "SEC") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "FEE") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "DUES") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "TOTAL") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "PAID") {
					columnWidth[i] = 4f;
				}

			}
		} else if (fileName.equals(MONTHLY_DETAILS_PDF)) {
			for (int i = 0; i < headers.length; i++) {
				if (headers[i] == "JAN") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "FEB") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "MAR") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "APR") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "MAY") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "JUN") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "JUL") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "AUG") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "SEP") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "OCT") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "NOV") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "DEC") {
					columnWidth[i] = 4f;
				}
				if (headers[i] == "YEAR") {
					columnWidth[i] = 4f;
				}

			}
		}

		else if (fileName.equals(YEARLY_REVENUE_PDF)) {
			for (int i = 0; i < headers.length; i++) {
				if (headers[i] == "JAN") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "FEB") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "MAR") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "APR") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "MAY") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "JUN") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "JUL") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "AUG") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "SEP") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "OCT") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "NOV") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "DEC") {
					columnWidth[i] = 7f;
				}
				if (headers[i] == "YEARLY TOTAL") {
					columnWidth[i] = 16f;
				}

			}
		}

		return columnWidth;

	}

}
