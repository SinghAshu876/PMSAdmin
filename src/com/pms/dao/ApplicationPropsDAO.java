package com.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.pms.util.DBConstants;

public class ApplicationPropsDAO implements DBConstants{

	private Logger LOG = Logger.getLogger(getClass());

	public String fetchProperty(String key) {
		LOG.info("fetchProperty ENTRY");
		String propValue = null;
		Connection connection = JDBCConnection.getConnection();
		String sql = APP_PROPS_QUERY;
		LOG.info("fetchProperty sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setString(1, key);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					propValue = (rs.getString(KEY_VALUE));
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in fetchProperty", e);
		}
		LOG.info("fetchProperty EXIT" + propValue);
		return propValue;
	}

}
