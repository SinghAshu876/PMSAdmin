package com.pms.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.pms.entity.ChannelDetails;
import com.pms.entity.DisconnectReconnectDetails;
import com.pms.entity.FeesHistory;
import com.pms.entity.User;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public interface UserService {
	public int save(User user);

	public int update(User user);

	public Integer deleteUser(Integer id);

	public Integer disconnectReconnectUser(Date date, User user, String futureStatus);

	public List<User> readUsers();

	public List<User> readUsersBasedonConnectionDate(Integer year, String month);

	public User readUserById(Integer id);

	public List<User> readUsersBasedOnSector(String condition, Integer year, String month);

	public List<User> readUsersBasedOnStreet(String condition, Integer year, String month);

	public String getMaxSequence();

	public String getnextSequence();

	public List<User> readUsersBasedOnCondition(String condition);

	public Map<Integer, Integer> updateAllUsersFees(String fee);

	public Date getDisconnectedDate(Integer userId);
	
	public Integer getNoOfNewConnectionsThisMonth();
	
	public Integer getRevenueFromNewConnectionsThisMonth();
	
	public boolean isUserActive(Integer id);
	
	public List<DisconnectReconnectDetails> getDiscoRecoHistory(Integer id);
	
	public FeesHistory findFeesDuringYearMonth(Integer id, Integer year, String month) ;
	
	public int updateFees(User user);
	
	public List<FeesHistory> getListOfFeesHistory(Integer id);
	
	public List<User> getArchivedUsersList();
	
	public List<ChannelDetails> getChannelDetails();
	
	public int updateChannelDetails(ChannelDetails channelDetails);
	
	public int deleteChannelDetails(int channelId);
}
