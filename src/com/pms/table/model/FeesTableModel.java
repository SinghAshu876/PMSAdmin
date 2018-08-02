package com.pms.table.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.pms.to.FeesDetailsDummy;
import com.pms.enums.util.Month;
import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class FeesTableModel extends AbstractTableModel implements ApplicationConstants{

	
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, List<FeesDetailsDummy>> feesDetailsDummyMap;

	private String emptyString = "";
	private String feesPaidInitialised = "0";

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "YEAR";
			break;
		case 1:
			name = "JAN";
			break;
		case 2:
			name = "FEB";
			break;
		case 3:
			name = "MAR";
			break;
		case 4:
			name = "APR";
			break;
		case 5:
			name = "MAY";
			break;
		case 6:
			name = "JUN";
			break;
		case 7:
			name = "JUL";
			break;
		case 8:
			name = "AUG";
			break;
		case 9:
			name = "SEP";
			break;
		case 10:
			name = "OCT";
			break;
		case 11:
			name = "NOV";
			break;
		case 12:
			name = "DEC";
			break;
		}
		return name;
	}

	public FeesTableModel(LinkedHashMap<Integer, List<FeesDetailsDummy>> feesDetailsDummyMap) {
		this.feesDetailsDummyMap = feesDetailsDummyMap;

	}

	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public int getRowCount() {
		return feesDetailsDummyMap.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<List<FeesDetailsDummy>> valuesList = new ArrayList<List<FeesDetailsDummy>>(feesDetailsDummyMap.values());
		List<FeesDetailsDummy> feesDetailsDummyList = valuesList.get(rowIndex);
		List<Integer> keys = new ArrayList<Integer>(feesDetailsDummyMap.keySet());
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = keys.get(rowIndex);
			break;
		case 1:
			value = prepareMonthValue(feesDetailsDummyList, Month.January.name().toUpperCase());
			break;
		case 2:
			value = prepareMonthValue(feesDetailsDummyList, Month.February.name().toUpperCase());
			break;
		case 3:
			value = prepareMonthValue(feesDetailsDummyList, Month.March.name().toUpperCase());
			break;
		case 4:
			value = prepareMonthValue(feesDetailsDummyList, Month.April.name().toUpperCase());
			break;
		case 5:
			value = prepareMonthValue(feesDetailsDummyList, Month.May.name().toUpperCase());
			break;
		case 6:
			value = prepareMonthValue(feesDetailsDummyList, Month.June.name().toUpperCase());
			break;
		case 7:
			value = prepareMonthValue(feesDetailsDummyList, Month.July.name().toUpperCase());
			break;
		case 8:
			value = prepareMonthValue(feesDetailsDummyList, Month.August.name().toUpperCase());
			break;
		case 9:
			value = prepareMonthValue(feesDetailsDummyList, Month.September.name().toUpperCase());
			break;
		case 10:
			value = prepareMonthValue(feesDetailsDummyList, Month.October.name().toUpperCase());
			break;
		case 11:
			value = prepareMonthValue(feesDetailsDummyList, Month.November.name().toUpperCase());
			break;
		case 12:
			value = prepareMonthValue(feesDetailsDummyList, Month.December.name().toUpperCase());
			break;

		}
		return value;
	}

	private String prepareMonthValue(List<FeesDetailsDummy> feesDetailsList, String month) {
		String feesPaid = feesPaidInitialised;
		String formattedDate = emptyString;
		String discount = feesPaidInitialised;
		for (FeesDetailsDummy feesDetailsReaderEntity : feesDetailsList) {
			if (feesDetailsReaderEntity.getMonth().equalsIgnoreCase(month)) {
				feesPaid = PAID.concat(String.valueOf(feesDetailsReaderEntity.getFeesPaid()).concat(NEXT_LINE));
				discount = DISCOUNT.concat(String.valueOf(feesDetailsReaderEntity.getDiscount()).concat(NEXT_LINE));
				if (feesDetailsReaderEntity.getPostingDate() != null) {
					formattedDate = FEES_PAID_DATE.concat(getFormattedDate(feesDetailsReaderEntity.getPostingDate()));
				}
				break;
			}

		}

		return feesPaid.concat(discount).concat(formattedDate);
	}

	private String getFormattedDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YY);
		return "-(" + dateFormat.format(date) + ")";
	}
}
