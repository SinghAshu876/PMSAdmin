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
public class SectorPrintTable extends SimplePrintTableEditor {

	private  Logger LOG = Logger.getLogger(getClass());
	private String sector;
	private String month;
	private Integer year;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

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

	public SectorPrintTable(String sector, String month, Integer year) {
		this.sector = sector;
		this.year = year;
		this.month = month;
	}

	public void showInTabularForm() {
		 LOG.info("showInTabularForm ENTRY");
		List<User> userList = userServiceImpl.readUsersBasedOnSector("where sector like '%" + getSector() + "%' AND active='Y'", year, month);
		SimplePrintTableModel model = new SimplePrintTableModel(userList);
		super.prepareTable("SECTOR PRINT", model, year, month);
		 LOG.info("showInTabularForm EXIT");

	}

}
