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
		int success = 0;
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
}
