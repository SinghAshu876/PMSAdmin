package com.pms.util;

import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.JTable;
import javax.swing.JTable.PrintMode;

import org.apache.log4j.Logger;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PrinterUtil implements ApplicationConstants {
	
	
	private  Logger LOG = Logger.getLogger(getClass());
	public void print(JTable table, Integer year, String month) {
		try {
			MessageFormat headerFormat = new MessageFormat(JAI_MATA_DI);
			MessageFormat footerFormat = new MessageFormat(month.concat(DASH).concat(String.valueOf(year)).concat("- {0} -"));
			table.print(PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException e1) {
			LOG.error(e1.getMessage());
			e1.printStackTrace();
		}
	}

	public void print(JTable table) {
		try {
			MessageFormat headerFormat = new MessageFormat(JAI_MATA_DI);
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			table.print(PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException e1) {
			LOG.error(e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	public void print(JTable table, Integer year) {
		try {
			MessageFormat headerFormat = new MessageFormat(JAI_MATA_DI);
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			table.print(PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException e1) {
			LOG.error(e1.getMessage());
			e1.printStackTrace();
		}
	}
}
