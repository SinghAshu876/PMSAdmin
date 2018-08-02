package com.pms.util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.pms.dao.ApplicationPropsDAO;
/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class CSVUtil implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private static final int  COLUMN_COUNT = 7;	
	
	private ApplicationPropsDAO  appProp = new ApplicationPropsDAO();

	public boolean generateCSVFile(JTable table, String fileName) {
		boolean isSuccessfull = true;
		LOG.info("generateCSVFile ENTRY");
		try {
			String csvFile = String.format(appProp.fetchProperty(FILE_LOCATION).concat(fileName) ,new SimpleDateFormat(DDMMYYYYHHMMSS).format(System.currentTimeMillis()));
			PrintWriter w = new PrintWriter(csvFile);

			w.print(CSV_HEADER);
			w.print(NEXT_LINE);

			for (int i = 0; i < table.getModel().getRowCount(); i++) {
				for (int columnIndex = 0; columnIndex < COLUMN_COUNT; columnIndex++) {
					Object value =  table.getModel().getValueAt(i, columnIndex);
					w.print(value.toString() + ",");
				}
				w.print(NEXT_LINE);

			}
			w.flush();
			w.close();
			LOG.info("generateCSVFile EXIT");
		} catch (Exception exception) {
			isSuccessfull = false;
			LOG.error(exception.getMessage());
			exception.printStackTrace();
		}
		return isSuccessfull;
	}
}
