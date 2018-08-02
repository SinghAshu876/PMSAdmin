package com.pms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.DisconnectReconnectDetails;
import com.pms.entity.User;
import com.pms.enums.util.ActiveStatus;
import com.pms.service.impl.FeesServiceImpl;
import com.pms.util.DBConstants;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class UserDAO implements DBConstants{
	private Logger LOG = Logger.getLogger(getClass());
	
	private FeesHistoryDAO feesHistoryDAO = new FeesHistoryDAO();
	private DisconnectReconnectDAO disconnectReconnectDAO = new DisconnectReconnectDAO();
	private AllFeesDetailsDAO allFeesDetailsDAO = new AllFeesDetailsDAO();

	public int save(User user) {
		LOG.info("save ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = SAVE_USER_QUERY;
		LOG.info("save sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getCustomerName());
			preparedStatement.setString(3, user.getStreet());
			preparedStatement.setString(4, user.getSector());
			preparedStatement.setDate(5, PMSUtility.convertToSqlDate(user.getDoc()));
			preparedStatement.setString(6, user.getMobileNumber());
			preparedStatement.setString(7, user.getSetTopBoxNumber());
			preparedStatement.setString(8, ActiveStatus.Y.name());
			preparedStatement.setInt(9, Integer.valueOf(user.getConnectionCharge()));
			preparedStatement.setString(10, user.getCafNumber());
			preparedStatement.setInt(11, user.getQrNo());
			Integer amount = user.getBackDues().equals("") ? Integer.valueOf("0") : Integer.valueOf(user.getBackDues());
			preparedStatement.setInt(12, amount);
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY INSERTED USER DATA INTO TABLE");
		} catch (SQLException e) {
			LOG.error("Db problem in save", e);
		}
		LOG.info("save EXIT");
		return success;
	}

	public int update(User user) {
		LOG.info("update ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = UPDATE_USER_QUERY;
		LOG.info("update sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, user.getCustomerName());
			preparedStatement.setInt(2, user.getQrNo());
			preparedStatement.setString(3, user.getStreet());
			preparedStatement.setString(4, user.getSector());
			preparedStatement.setString(5, user.getConnectionCharge());
			preparedStatement.setString(6, user.getMobileNumber());
			preparedStatement.setString(7, user.getSetTopBoxNumber());
			preparedStatement.setString(8, user.getCafNumber());
			Integer amount = user.getBackDues().equals("") ? Integer.valueOf("0") : Integer.valueOf(user.getBackDues());
			preparedStatement.setInt(9, amount);
			preparedStatement.setInt(10, user.getId());
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY UPDATED USER DATA INTO TABLE");

		} catch (SQLException e) {
			LOG.error("Db problem", e);
		}
		return success;
	}

	/**
	 * Delete all users and its referred tables
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteUser(Integer id) {
		LOG.info("deleteUser ENTRY");
		Connection connection = JDBCConnection.getConnection();
		String sql = DELETE_USER_QUERY;
		LOG.info("deleteUser sql : " + sql);
		int success = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, id);
			disconnectReconnectDAO.delete(id);
			allFeesDetailsDAO.delete(id);
			feesHistoryDAO.delete(id);
			success = preparedStatement.executeUpdate();
			LOG.info("successfully deleted user data from table");
		} catch (SQLException e) {
			LOG.error("problem cleaning up the Details in all tables", e);

		}
		LOG.info("deleteUser EXIT");
		return success;
	}

	public Integer disconnectReconnectUser(Date date, User user, String futureStatus) {
		LOG.info("disconnectReconnectUser ENTRY");
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = DISCONNECT_RECONNECT_USER_QUERY;
		LOG.info("disconnectReconnectUser sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, futureStatus);
			preparedStatement.setInt(2, user.getId());
			preparedStatement.executeUpdate();
			LOG.info("successfully updated status into user table");
			DisconnectReconnectDetails discoRecoDetails = new DisconnectReconnectDetails();
			discoRecoDetails.setUserId(user.getId());
			if (user.getActive().equals(ActiveStatus.Y.name())) {
				LOG.info("DISCONNECTING ");
				discoRecoDetails.setDateOfDisconnection(date);
				returnValue = new DisconnectReconnectDAO().disconnect(discoRecoDetails);
			} else {
				LOG.info("RECONNECTING ");
				discoRecoDetails.setDateOfReconnection(date);
				returnValue = new DisconnectReconnectDAO().reconnect(discoRecoDetails);
			}

		} catch (SQLException e) {
			LOG.error("DB problem in disconnectReconnectUser", e);
		}
		LOG.info("disconnectReconnectUser EXIT");
		return returnValue;

	}

	public List<User> readUsers() {
		LOG.info("readUsers ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<User> userArrayList = new ArrayList<User>();
		String sql = READUSERS_QUERY;
		LOG.info("readUsers sql : " + sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(ID));
				user.setConnectionCharge(rs.getString(CONNECTIONCHARGE));
				user.setCustomerName(rs.getString(CUSTOMERNAME));
				user.setDoc(rs.getDate(DOC));
				user.setFeesHistory(feesHistoryDAO.getLatestFees(user.getId()));
				user.setQrNo(rs.getInt(QRNO));
				user.setSector(rs.getString(SECTOR));
				user.setStreet(rs.getString(STREET));
				user.setMobileNumber(rs.getString(MOBNUMBER));
				user.setSetTopBoxNumber(rs.getString(SETTOPBOX));
				user.setCafNumber(rs.getString(CASNUMBER));
				user.setActive(rs.getString(ACTIVE));
				userArrayList.add(user);
			}

			LOG.info("No of users Fetched" + userArrayList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in readUsers", e);
		}
		LOG.info("readUsers EXIT");
		return userArrayList;
	}

	public List<User> readUsersBasedonConnectionDate(Integer year, String month) {
		LOG.info("readUsersBasedonConnectionDate ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<User> userArrayList = new ArrayList<User>();
		String sql = READUSERS_CONNECT_DATE_QUERY;
		LOG.info("readUsersBasedonConnectionDate sql :" + sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				User user = new User();
				Integer id = rs.getInt(ID);
				user.setId(id);
				user.setConnectionCharge(rs.getString(CONNECTIONCHARGE));
				user.setCustomerName(rs.getString(CUSTOMERNAME));
				user.setDoc(rs.getDate(DOC));
				user.setFeesHistory(feesHistoryDAO.findFeesDuringYearMonth(user.getId(), year, month));
				user.setQrNo(rs.getInt(QRNO));
				user.setSector(rs.getString(SECTOR));
				user.setStreet(rs.getString(STREET));
				user.setMobileNumber(rs.getString(MOBNUMBER));
				user.setSetTopBoxNumber(rs.getString(SETTOPBOX));
				user.setCafNumber(rs.getString(CASNUMBER));

				int initialBackDuesAmount = rs.getInt(BACK_DUES);
				int backDuesAmount = new FeesServiceImpl().getSkippedMonthBackDues(id, year, month);
				int totalBackDuesAmount = initialBackDuesAmount + backDuesAmount;
				user.setBackDues(String.valueOf(totalBackDuesAmount));

				userArrayList.add(user);
			}

			LOG.info("No of users Fetched" + userArrayList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in readUsersBasedonConnectionDate", e);

		}
		LOG.info("readUsersBasedonConnectionDate EXIT");
		return userArrayList;
	}

	public User readUserById(Integer id) {
		LOG.info("readUserById ENTRY");
		Connection connection = JDBCConnection.getConnection();
		User user = new User();
		String sql = READ_USER_BYID_QUERY;
		LOG.info("readUserById sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, id);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					user.setId(rs.getInt(ID));
					user.setConnectionCharge(rs.getString(CONNECTIONCHARGE));
					user.setCustomerName(rs.getString(CUSTOMERNAME));
					user.setDoc(rs.getDate(DOC));
					user.setFeesHistory(feesHistoryDAO.getLatestFees(user.getId()));
					user.setQrNo(rs.getInt(QRNO));
					user.setSector(rs.getString(SECTOR));
					user.setStreet(rs.getString(STREET));
					user.setMobileNumber(rs.getString(MOBNUMBER));
					user.setSetTopBoxNumber(rs.getString(SETTOPBOX));
					user.setCafNumber(rs.getString(CASNUMBER));
					user.setActive(rs.getString(ACTIVE));
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in readUserById", e);

		}
		LOG.info("readUserById EXIT");
		return user;
	}

	public List<User> readUsersBasedOnSector(String condition, Integer year, String month) {
		LOG.info("readUsersBasedOnSector ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<User> userArrayList = new ArrayList<User>();
		String sql = "select * from USER " + condition + "order by qrno asc";
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			LOG.info("readUsersBasedOnSector sql :" + sql);
			while (rs.next()) {
				User user = new User();
				Integer id = rs.getInt(ID);
				user.setId(id);
				user.setConnectionCharge(rs.getString(CONNECTIONCHARGE));
				user.setCustomerName(rs.getString(CUSTOMERNAME));
				user.setDoc(rs.getDate(DOC));
				user.setFeesHistory(new FeesHistoryDAO().getLatestFees(user.getId()));
				user.setQrNo(rs.getInt(QRNO));
				user.setSector(rs.getString(SECTOR));
				user.setStreet(rs.getString(STREET));
				user.setMobileNumber(rs.getString(MOBNUMBER));
				user.setSetTopBoxNumber(rs.getString(SETTOPBOX));
				user.setActive(rs.getString(ACTIVE));
				user.setCafNumber(rs.getString(CASNUMBER));
				int initialBackDuesAmount = rs.getInt(BACK_DUES);
				int backDuesAmount = new FeesServiceImpl().getSkippedMonthBackDues(id, year, month);
				int totalBackDuesAmount = initialBackDuesAmount + backDuesAmount;
				user.setBackDues(String.valueOf(totalBackDuesAmount));
				userArrayList.add(user);
			}

			LOG.info("No of rows Fetched" + userArrayList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in readUsersBasedOnSector", e);
		}
		LOG.info("readUsersBasedOnSector EXIT");
		return userArrayList;
	}

	public List<User> readUsersBasedOnStreet(String condition, Integer year, String month) {
		LOG.info("readUsersBasedOnStreet ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<User> userArrayList = new ArrayList<User>();
		String sql = "select * from USER " + condition + "order by qrno asc";
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			LOG.info("readUsersBasedOnStreet sql :" + sql);
			while (rs.next()) {
				User user = new User();
				Integer id = rs.getInt(ID);
				user.setId(id);
				user.setConnectionCharge(rs.getString(CONNECTIONCHARGE));
				user.setCustomerName(rs.getString(CUSTOMERNAME));
				user.setDoc(rs.getDate(DOC));
				user.setFeesHistory(new FeesHistoryDAO().getLatestFees(user.getId()));
				user.setQrNo(rs.getInt(QRNO));
				user.setSector(rs.getString(SECTOR));
				user.setStreet(rs.getString(STREET));
				user.setMobileNumber(rs.getString(MOBNUMBER));
				user.setSetTopBoxNumber(rs.getString(SETTOPBOX));
				user.setActive(rs.getString(ACTIVE));
				user.setCafNumber(rs.getString(CASNUMBER));
				int initialBackDuesAmount = rs.getInt(BACK_DUES);
				int backDuesAmount = new FeesServiceImpl().getSkippedMonthBackDues(id, year, month);
				int totalBackDuesAmount = initialBackDuesAmount + backDuesAmount;
				user.setBackDues(String.valueOf(totalBackDuesAmount));
				userArrayList.add(user);
			}

			LOG.info("No of rows Fetched" + userArrayList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in readUsersBasedOnStreet", e);
		}
		LOG.info("readUsersBasedOnSector EXIT");
		return userArrayList;
	}

	public List<User> readUsersBasedOnCondition(String condition) {
		LOG.info("readUsersBasedOnCondition ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<User> userArrayList = new ArrayList<User>();
		String sql = "select * from USER " + condition + "order by qrno asc";
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			LOG.info("readUsersBasedOnCondition sql :" + sql);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(ID));
				user.setConnectionCharge(rs.getString(CONNECTIONCHARGE));
				user.setCustomerName(rs.getString(CUSTOMERNAME));
				user.setDoc(rs.getDate(DOC));
				user.setFeesHistory(new FeesHistoryDAO().getLatestFees(user.getId()));
				user.setQrNo(rs.getInt(QRNO));
				user.setSector(rs.getString(SECTOR));
				user.setStreet(rs.getString(STREET));
				user.setMobileNumber(rs.getString(MOBNUMBER));
				user.setSetTopBoxNumber(rs.getString(SETTOPBOX));
				user.setActive(rs.getString(ACTIVE));
				user.setCafNumber(rs.getString(CASNUMBER));
				user.setBackDues(String.valueOf(rs.getInt(BACK_DUES)));
				userArrayList.add(user);
			}

			LOG.info("No of rows Fetched" + userArrayList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in readUsersBasedOnCondition", e);
		}
		LOG.info("readUsersBasedOnCondition EXIT");
		return userArrayList;
	}

	public Integer getSequenceNextValString() {
		LOG.info("getSequenceNextValString ENTRY");
		Connection connection = JDBCConnection.getConnection();
		Integer result = -1;
		String sql = NEXT_VALUE_QUERY;
		LOG.info("getSequenceNextValString sql :" + sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				result = rs.getInt("@p0");
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getSequenceNextValString", e);
		}
		LOG.info("getSequenceNextValString EXIT");
		return result;

	}

	public Integer getMaxSequenceValString() {
		LOG.info("getMaxSequenceValString ENTRY");
		Connection connection = JDBCConnection.getConnection();
		Integer result = -1;
		String sql = MAX_VALUE_QUERY;
		LOG.info("getMaxSequenceValString sql :" + sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				result = rs.getInt("id");
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getMaxSequenceValString", e);
		}
		LOG.info("getMaxSequenceValString EXIT");
		return result;

	}

	public Integer getNoOfNewConnectionsThisMonth() {
		LOG.info("getNoOfNewConnectionsThisMonth ENTRY");
		Integer noOfnewConnections = 0;
		java.util.Date today = new java.util.Date();
		String month = PMSUtility.getMonth(today);
		String year = PMSUtility.getYearString(today);
		LOG.info("MONTH AND YEAR "+month+"-"+year);
		Connection connection = JDBCConnection.getConnection();
		String sql = NEWCONNECTIONS_MONTHLY_QUERY;
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, month);
			preparedStatement.setString(2, year);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					noOfnewConnections = rs.getInt("newConnection");
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getNoOfNewConnectionsThisMonth", e);
		}
		LOG.info("getNoOfNewConnectionsThisMonth EXIT");
		return noOfnewConnections;
	}

	public Integer getRevenueFromNewConnectionsThisMonth() {
		LOG.info("getRevenueFromNewConnectionsThisMonth ENTRY");
		Integer revenueFromNewConnections = 0;
		java.util.Date today = new java.util.Date();
		String month = PMSUtility.getMonth(today);
		String year = PMSUtility.getYearString(today);
		LOG.info("MONTH AND YEAR "+month+"-"+year);
		Connection connection = JDBCConnection.getConnection();
		String sql = REVENUE_NEWCONNECTIONS_MONTHLY_QUERY;
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, month);
			preparedStatement.setString(2, year);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					revenueFromNewConnections = rs.getInt("revenueFromNewConnections");
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getNoOfNewConnectionsThisMonth", e);
		}
		LOG.info("getRevenueFromNewConnectionsThisMonth EXIT");
		return revenueFromNewConnections;
	}
	
	

}
