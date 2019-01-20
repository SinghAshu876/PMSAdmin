package com.pms.service.impl;

import static com.pms.util.PMSUtility.getMonth;
import static com.pms.util.PMSUtility.getYear;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pms.dao.ChannelDetailsDAO;
import com.pms.dao.DisconnectReconnectDAO;
import com.pms.dao.FeesHistoryDAO;
import com.pms.dao.UserChannelDetailsDAO;
import com.pms.dao.UserDAO;
import com.pms.entity.ChannelDetails;
import com.pms.entity.DisconnectReconnectDetails;
import com.pms.entity.FeesHistory;
import com.pms.entity.User;
import com.pms.enums.util.ActiveStatus;
import com.pms.service.UserService;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class UserServiceImpl implements UserService {

	private Logger LOG = Logger.getLogger(getClass());

	private UserDAO userDAO = new UserDAO();
	private DisconnectReconnectDAO disconnectReconnectDAO = new DisconnectReconnectDAO();
	private FeesHistoryDAO feesHistoryDAO = new FeesHistoryDAO();
	private UserChannelDetailsDAO userChannelDetailsDAO = new UserChannelDetailsDAO();
	private ChannelDetailsDAO channelDetailsDAO = new ChannelDetailsDAO();

	@Override
	public int save(User user) {
		int success = 1;
		int failure = 0;
		int suc1 = userDAO.save(user);
		if (suc1 == success) {
			int suc2 = feesHistoryDAO.save(user.getFeesHistory());
			if (suc2 == success) {
				int suc3 = userChannelDetailsDAO.save(user);
				if (suc3 == success) {
					return success;
				} else {
					return failure;
				}

			} else {
				return failure;
			}
		} else {
			return failure;
		}

	}

	@Override
	public int update(User user) {
		return userDAO.update(user);
	}

	@Override
	public Integer deleteUser(Integer id) {
		return userDAO.deleteUser(id);
	}

	@Override
	public Integer disconnectReconnectUser(Date date, User user, String futureStatus) {
		return userDAO.disconnectReconnectUser(date, user, futureStatus);
	}

	@Override
	public List<User> readUsers() {
		return userDAO.readUsers();
	}

	@Override
	public List<User> readUsersBasedonConnectionDate(Integer year, String month) {
		return userDAO.readUsersBasedonConnectionDate(year, month);
	}

	@Override
	public User readUserById(Integer id) {
		return userDAO.readUserById(id);
	}

	@Override
	public List<User> readUsersBasedOnSector(String condition, Integer year, String month) {
		return userDAO.readUsersBasedOnSector(condition, year, month);
	}

	@Override
	public List<User> readUsersBasedOnStreet(String condition, Integer year, String month) {
		return userDAO.readUsersBasedOnStreet(condition, year, month);
	}

	@Override
	public String getMaxSequence() {
		return String.valueOf(userDAO.getMaxSequenceValString());
	}

	@Override
	public String getnextSequence() {
		return String.valueOf(userDAO.getSequenceNextValString());
	}

	@Override
	public List<User> readUsersBasedOnCondition(String condition) {
		return userDAO.readUsersBasedOnCondition(condition);
	}

	@Override
	public Map<Integer, Integer> updateAllUsersFees(String fee) {
		LOG.info("updateAllUsersFees ENTRY");
		java.util.Date today = new java.util.Date();
		List<User> userList = userDAO.readUsers();
		Map<Integer, Integer> returnIdMap = new HashMap<Integer, Integer>();
		for (User user : userList) {
			if (user.getActive().equals(ActiveStatus.Y.name())) {
				FeesHistory feesHistory = new FeesHistory();
				feesHistory.setId(user.getId());
				feesHistory.setToDate(today);
				feesHistory.setFromDate(null);
				feesHistory.setFees(Integer.parseInt(fee));
				user.setFeesHistory(feesHistory);
				int successValue = updateFees(user);
				returnIdMap.put(user.getId(), successValue);
			}
		}
		LOG.info("updateAllUsersFees EXIT");
		return returnIdMap;

	}

	@Override
	public Date getDisconnectedDate(Integer userId) {
		return disconnectReconnectDAO.getDisconnectedDate(userId);
	}

	@Override
	public Integer getNoOfNewConnectionsThisMonth() {
		return userDAO.getNoOfNewConnectionsThisMonth();
	}

	@Override
	public Integer getRevenueFromNewConnectionsThisMonth() {
		return userDAO.getRevenueFromNewConnectionsThisMonth();
	}

	@Override
	public boolean isUserActive(Integer id) {
		LOG.info("isUserActive ENTRY");
		boolean isUserActive = true;
		User user = readUserById(id);
		if (user.getActive().equals(ActiveStatus.N.name())) {
			isUserActive = false;
		}
		return isUserActive;
	}

	@Override
	public List<DisconnectReconnectDetails> getDiscoRecoHistory(Integer id) {
		return disconnectReconnectDAO.getDiscoRecoHistory(id);
	}

	/**
	 * if existing value of fees is same which is entered by user , no need to
	 * update else if when user tried to update the fees and date of connection,year
	 * = updation month,year then update only fees else update existing date to
	 * previous month last date and insert a new row from first of the current month
	 */
	@Override
	public int updateFees(User user) {
		LOG.info("updateFees ENTRY");
		FeesHistory feesHistoryFromDB = feesHistoryDAO.getLatestFees(user.getId());
		FeesHistory feesHistoryFromGUI = user.getFeesHistory();
		if (feesHistoryFromDB.getFees().intValue() == user.getFeesHistory().getFees().intValue()) {
			LOG.info("NO NEED TO UPDATE,EXIT");
			return 1;
		} else {
			/*
			 * if (isUpdateFeesDateAndDocSame(feesHistoryFromGUI, user)) {
			 * LOG.info("SAME MONTH AS USER ONBOARDED/FEES UPDATE CASE, EXIT"); return
			 * feesHistoryDAO.updateFees(feesHistoryFromGUI); } else
			 */ if (isUpdateFeesDateAndExistingFeesDateSame(feesHistoryFromGUI, feesHistoryFromDB)) {
				LOG.info("TOO MANY TIMES FEES UPDATE CASE IN SAME MONTH, EXIT");
				return feesHistoryDAO.updateFees(feesHistoryFromGUI);
			} else {
				LOG.info("DIFFERENT MONTH FEES UPDATE, ENTRY");
				java.util.Date toDate = feesHistoryFromGUI.getToDate();
				feesHistoryFromGUI.setToDate(PMSUtility.getPreviousMonthLastDate(toDate));
				int suc1 = feesHistoryDAO.updateToDate(feesHistoryFromGUI);
				feesHistoryFromGUI.setFromDate(PMSUtility.getCurrentMonthFirstDate(toDate));
				int suc2 = feesHistoryDAO.save(feesHistoryFromGUI);
				if (suc1 == 1 && suc2 == 1) {
					LOG.info("SUCCESSFULLY UPDATED, EXIT");
					return 1;
				} else
					LOG.info("PROBLEM OCCURED, EXIT");
				return 0;
			}

		}

	}

	private boolean isUpdateFeesDateAndExistingFeesDateSame(FeesHistory feesHistoryFromGUI,
			FeesHistory feesHistoryFromDB) {
		LOG.info("isUpdateFeesDateAndExistingFeesDateSame ENTRY");
		boolean isUpdateFeesDateAndExistingFeesDateSame = false;
		String toMonthGUI = getMonth(feesHistoryFromGUI.getToDate());
		String fromMonthDB = getMonth(feesHistoryFromDB.getFromDate());
		Integer toYearGUI = getYear(feesHistoryFromGUI.getToDate());
		Integer fromYearDB = getYear(feesHistoryFromDB.getFromDate());
		if ((toMonthGUI).equals(fromMonthDB) && toYearGUI.intValue() == fromYearDB.intValue()) {
			isUpdateFeesDateAndExistingFeesDateSame = true;
		}
		LOG.info("isUpdateFeesDateAndExistingFeesDateSame EXIT " + isUpdateFeesDateAndExistingFeesDateSame);
		return isUpdateFeesDateAndExistingFeesDateSame;
	}

	@SuppressWarnings("unused")
	private boolean isUpdateFeesDateAndDocSame(FeesHistory feesHistoryFromGUI, User user) {
		LOG.info("isUpdateFeesDateAndDocSame ENTRY");
		boolean isUpdateFeesDateAndDocSame = false;
		String docMonth = getMonth(user.getDoc());
		String updationMonth = getMonth(feesHistoryFromGUI.getToDate());
		Integer docYear = getYear(user.getDoc());
		Integer updationYear = getYear(feesHistoryFromGUI.getToDate());
		if ((docMonth).equals(updationMonth) && docYear.intValue() == updationYear.intValue()) {
			isUpdateFeesDateAndDocSame = true;
		}
		LOG.info("isUpdateFeesDateAndDocSame EXIT " + isUpdateFeesDateAndDocSame);
		return isUpdateFeesDateAndDocSame;
	}

	@Override
	public List<FeesHistory> getListOfFeesHistory(Integer id) {
		return feesHistoryDAO.getListOfFeesHistoryIntegerFormat(id);
	}

	@Override
	public FeesHistory findFeesDuringYearMonth(Integer id, Integer year, String month) {
		return feesHistoryDAO.findFeesDuringYearMonth(id, year, month);
	}

	@Override
	public List<User> getArchivedUsersList() {
		return userDAO.readArchivedUsers();
	}

	@Override
	public List<ChannelDetails> getAllChannelDetails() {
		return channelDetailsDAO.getAllChannelDetails();
	}

	@Override
	public int updateChannelDetails(ChannelDetails channelDetails) {
		return channelDetailsDAO.updateChannelDetails(channelDetails);
	}

	@Override
	public int deleteChannelDetails(int channelId) {
		return channelDetailsDAO.deleteChannelDetails(channelId);
	}

	@Override
	public int addChannelDetails(ChannelDetails channelDetails) {
		return channelDetailsDAO.addChannelDetails(channelDetails);
	}

	@Override
	public int getNextChannelId() {
		return channelDetailsDAO.getChannelId();
	}

	@Override
	public List<ChannelDetails> getSelectedChannelsOfUser(int userId) {
		return channelDetailsDAO.getSelectedChannelsOfUser(userId);
	}
	
	@Override
	public List<ChannelDetails> getUnSelectedChannelsOfUser(int userId) {
		return channelDetailsDAO.getUnSelectedChannelsOfUser(userId);
	}

}
