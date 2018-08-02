package com.pms.table.model;

import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class YearlyRevenueTableModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;
	private HashMap<String,Integer> revenueMap;

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "JAN";
			break;
		case 1:
			name = "FEB";
			break;
		case 2:
			name = "MAR";
			break;
		case 3:
			name = "APR";
			break;
		case 4:
			name = "MAY";
			break;
		case 5:
			name = "JUN";
			break;
		case 6:
			name = "JUL";
			break;
		case 7:
			name = "AUG";
			break;
		case 8:
			name = "SEP";
			break;
		case 9:
			name = "OCT";
			break;
		case 10:
			name = "NOV";
			break;
		case 11:
			name = "DEC";
			break;
		case 12:
			name = "YEARLY TOTAL";
			break;
		}
		return name;
	}

	public YearlyRevenueTableModel(HashMap<String,Integer> revenueMap) {
		this.revenueMap = revenueMap;

	}

	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object value = null;
		switch (columnIndex) {

		case 0:
			value = revenueMap.get("JANUARY");
			break;
		case 1:
			value = revenueMap.get("FEBRUARY");
			break;
		case 2:
			value = revenueMap.get("MARCH");
			break;
		case 3:
			value = revenueMap.get("APRIL");
			break;
		case 4:
			value = revenueMap.get("MAY");
			break;
		case 5:
			value = revenueMap.get("JUNE");
			break;
		case 6:
			value = revenueMap.get("JULY");
			break;
		case 7:
			value = revenueMap.get("AUGUST");
			break;
		case 8:
			value = revenueMap.get("SEPTEMBER");
			break;
		case 9:
			value = revenueMap.get("OCTOBER");
			break;
		case 10:
			value = revenueMap.get("NOVEMBER");
			break;
		case 11:
			value = revenueMap.get("DECEMBER");
			break;
		case 12:
			value = getTotalSum(revenueMap);
			break;

		}
		return value;
	}

	private Object getTotalSum(HashMap<String, Integer> revenueMap2) {
		Integer sum = 0;
		for(Integer k:revenueMap2.values()){
			sum += k;
		}
		return sum;
	}
}
