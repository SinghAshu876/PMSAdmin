package com.pms.table;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pms.service.impl.FeesServiceImpl;
import com.pms.table.editor.YearlyRevenueTableEditor;
import com.pms.table.model.YearlyRevenueTableModel;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */

public class YearlyRevenuePrintTable extends YearlyRevenueTableEditor {
	
	private  Logger LOG = Logger.getLogger(getClass());
	private Integer year;
	private FeesServiceImpl feesServiceImpl = new FeesServiceImpl();
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}

	public YearlyRevenuePrintTable(Integer year) {
		this.year = year;

	}

	public void showInTabularForm() {
		 LOG.info("showInTabularForm ENTRY");
		HashMap<String,Integer> revenueDetailsMap = feesServiceImpl.getYearlyRevenue(year);
		YearlyRevenueTableModel model = new YearlyRevenueTableModel(revenueDetailsMap);
		super.prepareTable("YEARLY REVENUE PRINT", model, year);
		 LOG.info("showInTabularForm EXIT");

	}
}
