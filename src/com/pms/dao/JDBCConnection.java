package com.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class JDBCConnection implements ApplicationConstants {

	private static Connection conn = null;
	
	private static Logger LOG = Logger.getLogger(JDBCConnection.class);



	public static Connection getConnection() {
		LOG.info("getConnection ENTRY");
		try {
			if (conn != null) {
				return conn;
			}

			Class.forName("org.hsqldb.jdbcDriver");
			LOG.info("Connecting to database..."+System.getProperty(DB_URL));
			conn = DriverManager.getConnection(System.getProperty(DB_URL), System.getProperty(USER), System.getProperty(PASS));

		} catch (Exception e) {
			LOG.error("DB PROBLEM in getConnection", e);
		}
		LOG.info("getConnection EXIT");
		return conn;

	}

	public static void disposeConnection() {

		try {
			if (conn != null)
				conn.close();
			LOG.info("connection closed");
		} catch (Exception e) {
			LOG.error("DB PROBLEM in disposeConnection", e);

		}
	}

}