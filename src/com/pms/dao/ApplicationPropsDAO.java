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

	public int updateProperty(String text, String key) {
		LOG.info("updateProperty ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = UPDATE_APP_PROPS_QUERY;
		LOG.info("update sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, text);
			preparedStatement.setString(2, key);
			
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY UPDATED PROPERTY INTO TABLE"+ success);

		} catch (SQLException e) {
			LOG.error("Db problem", e);
		}
		return success;
	
	}

}
