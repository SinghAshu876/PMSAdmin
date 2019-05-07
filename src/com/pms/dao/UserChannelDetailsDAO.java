package com.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pms.entity.ChannelDetails;
import com.pms.entity.User;
import com.pms.util.DBConstants;

public class UserChannelDetailsDAO implements DBConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private static final int BATCH_SIZE = 50;

	public int save(User user) {
		LOG.info("save ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = 1;
		String sql = INSERT_USER_CHANNEL_DETAILS_QUERY;
		LOG.info("save sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			int i = 0;
			List<ChannelDetails> channelDetailsList = user.getChannelsList();
			if (channelDetailsList != null && channelDetailsList.size() > 0) {
				for (ChannelDetails channelDetails : channelDetailsList) {
					preparedStatement.setString(1, UUID.randomUUID().toString());
					preparedStatement.setInt(2, user.getId());
					preparedStatement.setInt(3, channelDetails.getChannelId());
					preparedStatement.addBatch();
					i++;
					if (i % BATCH_SIZE == 0) {
						preparedStatement.executeBatch();
					}
				}
				preparedStatement.executeBatch();
			}
			LOG.info("SUCCESSFULLY INSERTED USER CHANNEL DETAILS DATA INTO TABLE");
		} catch (SQLException e) {
			success = 0;
			LOG.error("Db problem in save", e);
		}
		LOG.info("save EXIT");
		return success;
	}

	public Integer[] getSelectedChannelIdsOfUser(int userId) {
		LOG.info("getSelectedChannelIdsOfUser ENTRY");

		Connection connection = JDBCConnection.getConnection();
		List<Integer> channelIdList = new ArrayList<Integer>();
		String sql = READ_USER_CHANNEL_DETAILS_BY_USERID_QUERY;
		LOG.info("getSelectedChannelIdsOfUser sql : " + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql)) {
			pStmnt.setInt(1, userId);
			try (ResultSet rs = pStmnt.executeQuery()) {
				while (rs.next()) {
					channelIdList.add(rs.getInt(CHANNELID));
				}
			}
			LOG.info("No of channel ids Fetched " + channelIdList.size());
		} catch (SQLException e) {
			LOG.error("Db problem in getting channel id", e);
		}
		Integer[] channelIdArray = channelIdList.toArray(new Integer[0]);

		LOG.info("getSelectedChannelIdsOfUser EXIT size " + channelIdArray.length);
		return channelIdArray;
	}

	public Integer delete(Integer id) {

		LOG.info("delete ENTRY");
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = DELETE_USER_CHANNEL_DETAILS_QUERY;
		LOG.info("delete sql :" + sql);
		try (PreparedStatement pStmnt = connection.prepareStatement(sql);) {
			pStmnt.setInt(1, id);
			returnValue = pStmnt.executeUpdate();
			LOG.info("SUCCESSFULLY DELETED USER_CHANNEL_DETAILS ");
		} catch (Exception e) {
			LOG.error("DB Problem in delete", e);
		}
		LOG.info("delete EXIT");
		return returnValue;

	}

}
