package com.pms.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.pms.entity.User;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SetTopBoxTableModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;
	private List<User> userList;
	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, String.class, String.class, String.class, String.class, String.class };

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "ID";
			break;
		case 1:
			name = "NAME";
			break;
		case 2:
			name = "CAF NO";
			break;
		case 3:
			name = "SMART CARD NO";
			break;
		case 4:
			name = "MOBILE NO";
			break;
		case 5:
			name = "QTR NO";
			break;
		case 6:
			name = "STR";
			break;
		}
		return name;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public SetTopBoxTableModel(List<User> userList) {
		this.userList = new ArrayList<User>(userList);
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return userList.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, int columnIndex) {
		User user = userList.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = user.getId();
			break;
		case 1:
			value = user.getCustomerName();
			break;
		case 2:
			value = user.getCafNumber();
			break;
		case 3:
			value = user.getSetTopBoxNumber();
			break;
		case 4:
			value = user.getMobileNumber();
			break;
		case 5:
			value = user.getQrNo();
			break;
		case 6:
			value = user.getStreet();
			break;

		}
		return value;
	}

}
