package com.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.ChannelDetails;
import com.pms.util.DBConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class ChannelDetailsDAO implements DBConstants {

	private Logger LOG = Logger.getLogger(getClass());

	public List<ChannelDetails> getAllChannelDetails() {
		LOG.info("getAllChannelDetails ENTRY");
		Connection connection = JDBCConnection.getConnection();
		List<ChannelDetails> channelDetailsList = new ArrayList<ChannelDetails>();
		String sql = READ_CHANNEL_DETAILS_QUERY;
		LOG.info("getAllChannelDetails sql : " + sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				ChannelDetails channelDetails = new ChannelDetails();
				channelDetails.setChannelId(rs.getInt(CHANNEL_ID));
				channelDetails.setChannelName(rs.getString(CHANNEL_NAME));
				channelDetails.setChannelPrice(rs.getInt(CHANNEL_PRICE));

				channelDetailsList.add(channelDetails);
			}

			LOG.info("No of channels Fetched" + channelDetailsList.size());

		} catch (SQLException e) {
			LOG.error("Db problem in getting channels", e);
		}
		LOG.info("getAllChannelDetails EXIT");
		return channelDetailsList;
	}

	public int deleteChannelDetails(int channelId) {
		LOG.info("deleteChannelDetails ENTRY");
		Connection connection = JDBCConnection.getConnection();
		String sql = DELETE_CHANNEL_DETAILS_QUERY;
		LOG.info("deleteChannelDetails sql : " + sql);
		int success = -1;
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, channelId);
			success = preparedStatement.executeUpdate();
			LOG.info("successfully deleted ChannelDetails from table");
		} catch (SQLException e) {
			LOG.error("problem cleaning up the ChannelDetails table", e);

		}
		LOG.info("deleteChannelDetails EXIT");
		return success;

	}

	public int updateChannelDetails(ChannelDetails channelDetails) {
		LOG.info("updateChannelDetails ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = -1;
		String sql = UPDATE_CHANNEL_DETAILS_QUERY;
		LOG.info("update sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, channelDetails.getChannelName());
			preparedStatement.setInt(2, channelDetails.getChannelPrice());
			preparedStatement.setInt(3, channelDetails.getChannelId());
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY UPDATED CHANNEL DETAILS INTO TABLE");

		} catch (SQLException e) {
			LOG.error("Db problem", e);
		}
		LOG.info("updateChannelDetails EXIT");
		return success;
	
	}

	public int addChannelDetails(ChannelDetails channelDetails) {
		LOG.info("addChannelDetails ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int success = -1;
		String sql = INSERT_CHANNEL_DETAILS_QUERY;
		LOG.info("addChannelDetails sql : " + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, channelDetails.getChannelId());
			preparedStatement.setString(2, channelDetails.getChannelName());
			preparedStatement.setInt(3, channelDetails.getChannelPrice());
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY INSERTED CHANNEL DETAILS INTO TABLE");
		} catch (SQLException e) {
			LOG.error("Db problem in saving Channel Details", e);
		}
		LOG.info("addChannelDetails EXIT");
		return success;

	}
	
	
	public Integer getChannelId() {
		LOG.info("getChannelId ENTRY");
		Connection connection = JDBCConnection.getConnection();
		Integer result = -1;
		String sql = NEXT_CHANNEL_ID_QUERY;
		LOG.info("getChannelId sql :" + sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				result = rs.getInt("@p0");
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getChannelId", e);
		}
		LOG.info("getChannelId EXIT");
		return result;

	}
}
