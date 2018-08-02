package com.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.ApplicationUsers;
import com.pms.util.DBConstants;

public class ApplicationUsersDAO implements DBConstants{
	
	private  Logger LOG = Logger.getLogger(getClass());

	public List<ApplicationUsers> readLoginDetails(String userName, String password) {
		LOG.info("readLoginDetails ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<ApplicationUsers> userArrayList = new ArrayList<ApplicationUsers>();
		String sql = APP_USERS_QUERY;
		LOG.info("readLoginDetails sql :"+sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setString(1, userName); 
			pStmnt.setString(2, password);
			try(ResultSet rs = pStmnt.executeQuery()){
				while (rs.next()) {
					ApplicationUsers applicationUsers = new ApplicationUsers();
					applicationUsers.setId(rs.getInt(ID));
					applicationUsers.setUserName(rs.getString(USERNAME));
					applicationUsers.setPassword(rs.getString(PASSWORD));
					applicationUsers.setUserAlias(rs.getString(USER_ALIAS));
					applicationUsers.setUserGroup(rs.getString(USER_GROUP));
					userArrayList.add(applicationUsers);
				}
			}
			LOG.info("No of users Fetched :" + userArrayList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in readUsers", e);
		}
		LOG.info("readLoginDetails EXIT");
		return userArrayList;
	}
}
