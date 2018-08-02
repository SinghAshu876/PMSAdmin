package com.pms.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import com.pms.entity.User;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SearchResultsTableModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;
	private List<User> userList;
	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, String.class, String.class, String.class, String.class, JButton.class };

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
			name = "QR NO";
			break;
		case 3:
			name = "SECTOR";
			break;
		case 4:
			name = "ACTIVE";
			break;
		case 5:
			name = "DETAILS";
			break;
		}
		return name;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public SearchResultsTableModel(List<User> userList) {
		this.userList = new ArrayList<User>(userList);
	}

	@Override
	public int getColumnCount() {
		return 6;
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
			value = user.getQrNo();
			break;
		case 3:
			value = user.getSector();
			break;
		case 4:
			value = user.getActive();
			break;
		case 5:
			value = new JButton();
			break;
		}
		return value;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 5)
			return true;
		else
			return false;
	}
}
