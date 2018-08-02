package com.pms.dao;

import static com.pms.util.PMSUtility.createDateObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.FeesHistory;
import com.pms.util.DBConstants;
import com.pms.util.PMSUtility;

public class FeesHistoryDAO implements DBConstants{

	private Logger LOG = Logger.getLogger(getClass());

	public FeesHistory getLatestFees(Integer id) {
		LOG.info("getLatestFees ENTRY");
		Connection connection = JDBCConnection.getConnection();
		FeesHistory feesHistory = new FeesHistory();
		String sql = LATEST_FEES_QUERY;
		LOG.info("getLatestFees sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, id);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					feesHistory.setId(rs.getInt(ID));
					feesHistory.setFromDate(rs.getDate(FROM_DATE));
					feesHistory.setToDate(rs.getDate(TO_DATE));
					feesHistory.setFees(rs.getInt(FEES));
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getLatestFees", e);

		}
		LOG.info("getLatestFees EXIT");
		return feesHistory;
	}

	public FeesHistory findFeesDuringYearMonth(Integer id, Integer year, String month) {
		LOG.info("findFeesForYearMonth ENTRY");
		FeesHistory feesHistory = null;
		List<FeesHistory> feesHistoryList = getListOfFeesHistory(id);
		Date tillDateRequired = createDateObject(year, PMSUtility.getMonthSequence(month));
		for (FeesHistory feesHist : feesHistoryList) {
			if (feesHist.getFromDate() != null && feesHist.getToDate() != null) {
				if (feesHist.getFromDate().before(tillDateRequired) && feesHist.getToDate().after(tillDateRequired)) {
					feesHistory = feesHist;
					break;
				}
				else if (feesHist.getFromDate().equals(tillDateRequired)){
					feesHistory = feesHist;
					break;
				}
				else if(feesHist.getToDate().equals(tillDateRequired)){
					feesHistory = feesHist;
					break;
				}
				else if (feesHist.getFromDate().after(tillDateRequired)) {
					LOG.info("feesDesired does not exists case");
					/**by default get latest fees*/
					feesHistory = getLatestFees(id);
					break;
					
				}
			} else {				
					feesHistory = feesHist;
					break;
			
			}

		}
		LOG.info("findFeesForYearMonth EXIT");
		return feesHistory;
	}

	public Integer delete(Integer id) throws SQLException {
		LOG.info("delete ENTRY");
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = DELETE_FEES_HIST_QUERY;
		LOG.info("delete sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql);) {
			pStmnt.setInt(1, id);
			returnValue = pStmnt.executeUpdate();
			LOG.info("SUCCESSFULLY DELETED FEES_HISTORY ");
		} catch (Exception e) {
			LOG.error("DB Problem in delete", e);
		}
		LOG.info("delete EXIT");
		return returnValue;
	}

	public int save(FeesHistory feesHistory) {
		LOG.info("save ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = SAVE_FEES_HIST_QUERY;
		LOG.info("save sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, feesHistory.getId());
			preparedStatement.setDate(2, PMSUtility.convertToSqlDate(feesHistory.getFromDate()));
			preparedStatement.setDate(3, null);
			preparedStatement.setInt(4, feesHistory.getFees());
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY INSERTED FEES_HISTORY DATA INTO TABLE");
		} catch (SQLException e) {
			LOG.error("Db problem in FeesHistory save", e);
		}
		LOG.info("save EXIT");
		return success;
	}

	public int updateToDate(FeesHistory feesHistory) {
		LOG.info("updateToDate ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = UPDATE_TODATE_QUERY;
		LOG.info("update sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setDate(1, PMSUtility.convertToSqlDate(feesHistory.getToDate()));
			preparedStatement.setInt(2, feesHistory.getId());
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY UPDATED FEES_HISTORY DATA INTO TABLE");
		} catch (SQLException e) {
			LOG.error("Db problem", e);
		}
		LOG.info("updateToDate EXIT");
		return success;

	}

	public int updateFees(FeesHistory feesHistory) {
		LOG.info("updateFees ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = UPDATE_FEES_HIST_QUERY;
		LOG.info("update sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, feesHistory.getFees());
			preparedStatement.setInt(2, feesHistory.getId());
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY UPDATED FEES_HISTORY DATA INTO TABLE");
		} catch (SQLException e) {
			LOG.error("Db problem", e);
		}
		LOG.info("updateFees EXIT");
		return success;

	}

	public List<FeesHistory> getListOfFeesHistoryIntegerFormat(Integer id) {
		LOG.info("getListOfFeesHistoryIntegerFormat ENTRY");
		Connection connection = JDBCConnection.getConnection();
		String sql = LIST_FEES_HIST_QUERY;
		LOG.info("getListOfFeesHistoryIntegerFormat sql :" + sql);
		List<FeesHistory> feesHistoryList = new ArrayList<FeesHistory>();
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, id);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					FeesHistory feesHistory = new FeesHistory();
					feesHistory.setId(id);
					feesHistory.setFromYear(rs.getInt("FROM_YEAR"));
					feesHistory.setToYear(rs.getInt("TO_YEAR"));
					feesHistory.setFromMonth(rs.getInt("FROM_MONTH"));
					feesHistory.setToMonth(rs.getInt("TO_MONTH"));
					feesHistory.setFees(rs.getInt("fees"));
					feesHistoryList.add(feesHistory);
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getListOfFeesHistoryIntegerFormat", e);

		}
		LOG.info("getListOfFeesHistoryIntegerFormat EXIT size " + feesHistoryList.size());
		return feesHistoryList;
	}

	public List<FeesHistory> getListOfFeesHistory(Integer id) {
		LOG.info("getListOfFeesHistory ENTRY");
		Connection connection = JDBCConnection.getConnection();
		String sql = FEES_HIST_QUERY;
		LOG.info("getListOfFeesHistory sql :" + sql);
		List<FeesHistory> feesHistoryList = new ArrayList<FeesHistory>();
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, id);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					FeesHistory feesHistory = new FeesHistory();
					feesHistory.setId(id);
					feesHistory.setFromDate(rs.getDate(FROM_DATE));
					feesHistory.setToDate(rs.getDate(TO_DATE));
					feesHistory.setFees(rs.getInt(FEES));
					feesHistoryList.add(feesHistory);
				}
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getListOfFeesHistory", e);

		}
		LOG.info("getListOfFeesHistory EXIT size " + feesHistoryList.size());
		return feesHistoryList;
	}
}
