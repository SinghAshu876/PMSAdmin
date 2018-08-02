package com.pms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pms.entity.DisconnectReconnectDetails;
import com.pms.util.DBConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class DisconnectReconnectDAO implements DBConstants{

	private  Logger LOG = Logger.getLogger(getClass());
	
	public int disconnect(DisconnectReconnectDetails disconnectReconnectDetails) {
		LOG.info("disconnect ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 0;
		String sql = DISCONNECT_QUERY;
		LOG.info("disconnect sql :"+sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, UUID.randomUUID().toString());
			preparedStatement.setInt(2, disconnectReconnectDetails.getUserId());
			preparedStatement.setDate(3, disconnectReconnectDetails.getDateOfDisconnection());
			preparedStatement.setDate(4, null);
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY INSERTED DISCO_RECO_DETAILS DATA INTO TABLE");
		} catch (Exception e) {
			LOG.error("Problem in Disconnect", e);
		}
		return success;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	private String getId(Integer userId) {
		LOG.info("getId ENTRY");
		String id = null;
		Connection connection = JDBCConnection.getConnection();
		String sql = ID_QUERY;
		LOG.info("getId sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, userId);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					id = rs.getString(ID);
				}
			}
			LOG.info("Unique id found" + id);
		} catch (Exception e) {
			LOG.error("Problem in getId", e);
		}
		LOG.info("getId EXIT");
		return id;

	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Date getDisconnectedDate(Integer userId) {
		LOG.info("getDisconnectedDate ENTRY");
		Date dateofDisconnection = null;
		Connection connection = JDBCConnection.getConnection();
		String sql = DISCONNECTED_DATE_QUERY;
		LOG.info("getDisconnectedDate sql :"+sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, userId);
			try (ResultSet rs = pStmnt.executeQuery()) {
			while (rs.next()) {
				dateofDisconnection = rs.getDate(DODC);
			}
			LOG.info("Date of disconnection" + dateofDisconnection);
		   } 
		}catch (Exception e) {
			LOG.error("DB Problem in DisconnectedDate", e);
		}
		LOG.info("getDisconnectedDate EXIT");
		return dateofDisconnection;

	}

	public Integer reconnect(DisconnectReconnectDetails disconnectReconnectDetails) {
		LOG.info("reconnect ENTRY");
		Integer returnValue = 0;
		LOG.info(disconnectReconnectDetails.getDateOfReconnection());
		Connection connection = JDBCConnection.getConnection();
		String sql = "update DISCO_RECO_DETAILS set dorc=DATE '" + disconnectReconnectDetails.getDateOfReconnection() + "' where id='" + getId(disconnectReconnectDetails.getUserId()) + "'";
		LOG.info("reconnect sql :"+sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			returnValue = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error("DB Problem in reconnect", e);
		}

		LOG.info("reconnect EXIT");
		return returnValue;

	}

	public Integer delete(Integer id) {
		LOG.info("delete ENTRY");
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = DELETE_DISCO_RECO_QUERY;
		LOG.info("delete sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql);) {
			pStmnt.setInt(1, id);
			returnValue = pStmnt.executeUpdate();
			LOG.info("SUCCESSFULLY DELETED DISCO_RECO_DETAILS ");
		} catch (Exception e) {
			LOG.error("DB Problem in delete", e);
		}
		LOG.info("delete EXIT");
		return returnValue;
	}
	
	public List<DisconnectReconnectDetails> getDiscoRecoHistory(Integer id){
		LOG.info("getDiscoRecoHistory ENTRY");
		List<DisconnectReconnectDetails> disconnectHistory = new ArrayList<DisconnectReconnectDetails>();
		Connection connection = JDBCConnection.getConnection();
		String sql = DISCO_RECO_HISTORY_QUERY;
		LOG.info("getDiscoRecoHistory sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, id);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					DisconnectReconnectDetails details = new DisconnectReconnectDetails();
					details.setDateOfDisconnection(rs.getDate(DODC));
					details.setDateOfReconnection(rs.getDate(DORC));
					details.setUserId(rs.getInt(USERID));
					disconnectHistory.add(details);
				}
				LOG.info("getDisconnectionHistory for id : " + id);
			}
		}catch (Exception e) {
			LOG.error(" DB Problem in getDisconnectionHistory", e);

		}
		LOG.info("getDiscoRecoHistory EXIT");
		return disconnectHistory;
	}

}
