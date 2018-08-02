package com.pms.table;

import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.User;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.editor.SimplePrintTableEditor;
import com.pms.table.model.SimplePrintTableModel;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */

public class SimplePrintTable extends SimplePrintTableEditor {
	
	private  Logger LOG = Logger.getLogger(getClass());
	private String month;
	private Integer year;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	public String getMonth() {
		return month;
	}

	public Integer getYear() {
		return year;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public SimplePrintTable(String month, Integer year) {
		this.year = year;
		this.month = month;
	}

	public void showInTabularForm() {
		 LOG.info("showInTabularForm ENTRY");
		List<User> userList = userServiceImpl.readUsersBasedonConnectionDate(year, month);
		SimplePrintTableModel model = new SimplePrintTableModel(userList);
		super.prepareTable("SIMPLE PRINT", model, year, month);
		 LOG.info("showInTabularForm EXIT");

	}
}
