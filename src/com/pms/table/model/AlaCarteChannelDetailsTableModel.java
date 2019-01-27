package com.pms.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.pms.entity.ChannelDetails;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class AlaCarteChannelDetailsTableModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;
	private List<ChannelDetails> channelDetailsList;
	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, String.class, String.class};

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "CHANNEL ID";
			break;
		case 1:
			name = "CHANNEL NAME";
			break;
		case 2:
			name = "CHANNEL PRICE";
			break;
		case 3:
			name = "CHANNEL TYPE";
			break;
		}
		return name;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public AlaCarteChannelDetailsTableModel(List<ChannelDetails> channelDetailsList) {
		this.channelDetailsList = new ArrayList<ChannelDetails>(channelDetailsList);
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return channelDetailsList.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, int columnIndex) {
		ChannelDetails channelDetails = channelDetailsList.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = channelDetails.getChannelId();
			break;
		case 1:
			value = channelDetails.getChannelName();
			break;
		case 2:
			value = PMSUtility.getDecimalFormat(channelDetails.getChannelPrice());
			break;
		case 3:
			value = channelDetails.getChannelType();
			break;
		}
		return value;
	}
	
}
