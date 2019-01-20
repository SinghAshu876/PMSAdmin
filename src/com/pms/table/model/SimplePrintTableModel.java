package com.pms.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.pms.entity.User;
//import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SimplePrintTableModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;
	private List<User> userList;

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "NAME";
			break;
		case 1:
			name = "CAF NO";
			break;
		case 2:
			name = "SET TOP BOX";
			break;
		case 3:
			name = "SEC";
			break;
		case 4:
			name = "ID";
			break;
		case 5:
			name = "STR";
			break;
		case 6:
			name = "QTR";
			break;
		case 7:
			name = "MOBILE";
			break;
		case 8:
			name = "FEE";
			break;
		case 9:
			name = "DUES";
			break;
		case 10:
			name = "TOTAL";
			break;
		case 11:
			name = "PAID";
			break;
		case 12:
			name = "DUES";
			break;
		}
		return name;
	}

	public SimplePrintTableModel(List<User> userList) {
		this.userList = new ArrayList<User>(userList);

	}

	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public int getRowCount() {
		return userList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = userList.get(rowIndex);
		Object value = null;
		switch (columnIndex) {

		case 0:
			value = user.getCustomerName();
			break;
		case 1:
			value = user.getCafNumber();
			break;
		case 2:
			value = user.getSetTopBoxNumber();
			break;
		case 3:
			value = user.getSector();
			break;
		case 4:
			value = user.getId();
			break;
		case 5:
			value = user.getStreet();
			break;
		case 6:
			value = user.getQrNo();
			break;
		case 7:
			value = user.getMobileNumber();
			break;
		case 8:
			//value = PMSUtility.findFeeCode((null != user.getFeesHistory() && null != user.getFeesHistory().getFees())?user.getFeesHistory().getFees():"-");
			value = ((null != user.getFeesHistory() && null != user.getFeesHistory().getFees())?user.getFeesHistory().getFees():"-");

			break;
		case 9:
			value = Integer.valueOf(null != user.getBackDues() ? user.getBackDues() : "0")-((null != user.getFeesHistory() && null != user.getFeesHistory().getFees())? user.getFeesHistory().getFees() : 0);
			break;
		case 10:
			value = Integer.valueOf(null != user.getBackDues() ? user.getBackDues() : "0"); 
			break;
		case 11:
			value = "";
			break;
		case 12:
			value = "";
			break;

		}
		return value;
	}
}
