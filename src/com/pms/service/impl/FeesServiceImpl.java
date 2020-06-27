package com.pms.service.impl;

import static com.pms.util.PMSUtility.createDateObject;
import static com.pms.util.PMSUtility.getMonth;
import static com.pms.util.PMSUtility.getMonthSequence;
import static com.pms.util.PMSUtility.getYear;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.dao.AllFeesDetailsDAO;
import com.pms.entity.AllFeesDetails;
import com.pms.entity.DisconnectReconnectDetails;
import com.pms.entity.FeesHistory;
import com.pms.entity.User;
import com.pms.service.FeesService;

public class FeesServiceImpl implements FeesService {

	private Logger LOG = Logger.getLogger(getClass());

	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private AllFeesDetailsDAO allfeesDetailsDAO = new AllFeesDetailsDAO();

	@Override
	public int getSkippedMonthBackDues(Integer id, Integer endYear, String month) {
		LOG.info("getSkippedMonthBackDues ENTRY  " + endYear + month);
		List<DisconnectReconnectDetails> disconnectHistory = userServiceImpl.getDiscoRecoHistory(id);
		int sumOfFeesReceived = allfeesDetailsDAO.getSumOfFeesReceived(id);
		int proposedAmount = 0;
		if (disconnectHistory.isEmpty()) {
			proposedAmount = getProposedAmount(id, endYear, month);
			return proposedAmount - sumOfFeesReceived;
		} else {
			/*** currently disconnect reconnect table will have only 1 entry per  user*/
			/*** for multiple disconnect reconnect entries for each user development is required*/
			DisconnectReconnectDetails d = disconnectHistory.get(0);

			/** 1] Calculation from date of connection to date of disconnection */
			Date dodc = d.getDateOfDisconnection();
			proposedAmount = getProposedAmount(id, getYear(dodc), getMonth(dodc));
			LOG.info("FeesServiceImpl.getSkippedMonthBackDues" + proposedAmount);

			/** 2] Calculation from date of reconnection to date passed */
			Date dorc = d.getDateOfReconnection();
			if (dorc != null) {
				Date endDate = createDateObject(endYear, month);
				proposedAmount = getProposedAmount(id, getYear(endDate), getMonth(endDate));
			}
			LOG.info("getSkippedMonthBackDues EXIT");
			return proposedAmount - sumOfFeesReceived;
		}

	}

	


	public int getNoOfMonthsBetween(int from_year, int from_month, int to_year, int to_month) {
		LOG.info("getNoOfMonthsBetween from_year and from_month " + from_year + "-" + from_month);
		LOG.info("getNoOfMonthsBetween to_year and to_month " + to_year + "-" + to_month);
		int count_of_months =0;
		while (from_year <= to_year) {
			if (from_month == 12) {
				from_year++;
				from_month = 0;
			} else if (from_month == to_month && from_year == to_year) {
                count_of_months ++;
                break;
			}
			count_of_months++;
			from_month++;
		}

		LOG.info("getNoOfMonthsBetweenTwoDates EXIT " + count_of_months);
		return count_of_months;
	}

	public int getDisconnectedUsersBackDues(Integer id, Date disconnectedDate) {
		int proposedAmount = getProposedAmount(id, getYear(disconnectedDate), getMonth(disconnectedDate));
		int sumOfFeesReceived = allfeesDetailsDAO.getSumOfFeesReceived(id);
		return proposedAmount - sumOfFeesReceived;
	}


	private int getProposedAmount(Integer id, Integer tillEndYear, String tillMonth) {
		LOG.info("getProposedAmount for" + id);
		List<FeesHistory> feesHistoryList = userServiceImpl.getListOfFeesHistory(id);
		int proposedAmount = 0;
		int tillIntMonth = getMonthSequence(tillMonth);
		for (FeesHistory feesHistory : feesHistoryList) {
			if (feesHistory.getFromYear() != null && feesHistory.getFromMonth() != null && feesHistory.getToMonth() != null && feesHistory.getToYear() != null) {
				if (feesHistory.getFromYear() <= tillEndYear && tillEndYear <= feesHistory.getToYear()) {
					LOG.info("calculation till  " + tillIntMonth + "-" + tillEndYear);
					LOG.info("create three instances of dates ,fromDateInDB, toDateInDB, tillDateRequired");
					Date fromDateInDB = createDateObject(feesHistory.getFromYear(), feesHistory.getFromMonth());
					Date toDateInDB = createDateObject(feesHistory.getToYear(), feesHistory.getToMonth());
					Date tillDateRequired = createDateObject(tillEndYear, tillIntMonth);
					LOG.info("compare");
					if (tillDateRequired.after(toDateInDB)) {
						LOG.info("get difference between fromDateInDB and  toDateInDB");
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),feesHistory.getToYear(),feesHistory.getToMonth());
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
					} else if (tillDateRequired.equals(toDateInDB)) {
						LOG.info(" get difference between fromDateInDB and  toDateInDB/tillDateRequired");
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),feesHistory.getToYear(),feesHistory.getToMonth());
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					} else if (tillDateRequired.before(toDateInDB)) {
						LOG.info("get difference between fromDateInDB and tillDateRequired");
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),tillEndYear,tillIntMonth);
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					} else if (tillDateRequired.equals(fromDateInDB)) {
						LOG.info(" get difference between fromDateInDB and tillDateRequired for only 1 month");
						int nofMonths = 1;
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					}

				}
				else if (feesHistory.getFromYear() <= tillEndYear && tillEndYear >= feesHistory.getToYear()) {
					LOG.info("calculation till  " + tillIntMonth + "/" + tillEndYear);
					LOG.info("create three instances of dates ,fromDateInDB, toDateInDB, tillDateRequired");
					Date fromDateInDB = createDateObject(feesHistory.getFromYear(), feesHistory.getFromMonth());
					Date toDateInDB = createDateObject(feesHistory.getToYear(), feesHistory.getToMonth());
					Date tillDateRequired = createDateObject(tillEndYear, tillIntMonth);
					if (tillDateRequired.after(toDateInDB)) {
						LOG.info("get difference between fromDateInDB and  toDateInDB");
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),feesHistory.getToYear(),feesHistory.getToMonth());
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);

					} else if (tillDateRequired.equals(toDateInDB)) {
						LOG.info(" get difference between fromDateInDB and  toDateInDB/tillDateRequired");
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),feesHistory.getToYear(),feesHistory.getToMonth());
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					} else if (tillDateRequired.before(toDateInDB)) {
						LOG.info("get difference between fromDateInDB and tillDateRequired");
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),tillEndYear,tillIntMonth);
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					} else if (tillDateRequired.equals(fromDateInDB)) {
						LOG.info(" get difference between fromDateInDB and tillDateRequired for only 1 month");
						int nofMonths = 1;
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					}

				}

			} else {
				if (feesHistory.getFromYear() <= tillEndYear) {
					LOG.info("create two instances of dates fromDateInDB , tillDateRequired");
					Date fromDateInDB = createDateObject(feesHistory.getFromYear(), feesHistory.getFromMonth());
					Date tillDateRequired = createDateObject(tillEndYear, tillIntMonth);
					if (tillDateRequired.after(fromDateInDB)) {
						int nofMonths = getNoOfMonthsBetween(feesHistory.getFromYear(), feesHistory.getFromMonth(),tillEndYear,tillIntMonth);
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					} else if (tillDateRequired.equals(fromDateInDB)) {
						int nofMonths = 1;
						proposedAmount = proposedAmount + (feesHistory.getFees() * nofMonths);
						break;
					} else if (tillDateRequired.before(fromDateInDB)) {
						// THIS CASE CANNOT BE THERE
						proposedAmount= 0;
						break;
					}

				}

			}
		}
		LOG.info("getProposedAmount calculated finally " + proposedAmount);
		return proposedAmount;

	}

	/**
	 * used for recomputing back dues when user clicks to enter discount details
	 * 
	 * @param user
	 * @param month
	 * @param year
	 * @return
	 */
	@Override
	public String calculateRecomputedAmount(User user, String month, String year) {
		LocalDate date = LocalDate.of(Integer.valueOf(year), Month.valueOf(month), 1);
		String bckDues = getBackDues(user.getId());
		int skippedMonthValue = getSkippedMonthBackDues(user.getId(), date.getYear(), date.getMonth().toString());
		LOG.info("skippedMonthValue " + skippedMonthValue);
		int totalRevenue = skippedMonthValue  + Integer.valueOf(bckDues);
		bckDues = String.valueOf(totalRevenue);
		LOG.info("bckDues " + bckDues);
		return bckDues;
	}

	@Override
	public User getUserFromUserList(Integer id, List<User> userList) {
		User user = null;
		for (User temp : userList) {
			if (temp.getId().equals(id)) {
				user = temp;
				break;
			}
		}
		return user;
	}

	/**
	 * Search in DB whether user has paid fees for the month provided in params
	 * 
	 * @param id
	 * @param month
	 * @param year
	 * @return
	 */
	@Override
	public AllFeesDetails readProvidedMonthFeesDetails(Integer id, String month, Integer year) {
		AllFeesDetails allFeesDetails = allfeesDetailsDAO.readFeesDetails(id, month, year);
		return allFeesDetails;
	}

	@Override
	public void updateSkippedMonthFeesToZero(Integer id, Integer endYear, String month) {
		allfeesDetailsDAO.updateSkippedMonthFeesToZero(id, endYear, month);

	}

	@Override
	public String getBackDues(int id) {
		return allfeesDetailsDAO.getBackDues(id);
	}

	@Override
	public Integer isUserPaidFees(User user, String month, Integer year) {
		return allfeesDetailsDAO.isUserPaidFees(user, month, year);
	}

	@Override
	public List<AllFeesDetails> readFeesDetails(Integer id) {
		return allfeesDetailsDAO.readFeesDetails(id);
	}

	@Override
	public Integer getBackDuesAmountTobeCollected() {
		return allfeesDetailsDAO.getBackDuesAmountTobeCollected();
	}

	@Override
	public Integer getProposedAmountTobeCollected() {
		return allfeesDetailsDAO.getProposedAmountTobeCollected();
	}

	@Override
	public Integer saveFeesDetails(User user, AllFeesDetails feesDetails) {
		return allfeesDetailsDAO.saveFeesDetails(user, feesDetails);
	}

	@Override
	public Integer getDiscountProvidedThisMonth() {
		return allfeesDetailsDAO.getDiscountProvidedThisMonth();
	}

	@Override
	public HashMap<String, Integer> getYearlyRevenue(Integer year) {
		return allfeesDetailsDAO.getYearlyRevenue(year);
	}

	public String getFeesForDisconnectedUser(Integer id) {
		LOG.info("getFeesForDisconnectedUser ENTRY");
		Integer fees = 0;
		String backDues = getBackDues(id);
		fees = Integer.valueOf(backDues);

		List<DisconnectReconnectDetails> disconnectHistory = userServiceImpl.getDiscoRecoHistory(id);
		for (DisconnectReconnectDetails d : disconnectHistory) {
			Date dodc = d.getDateOfDisconnection();

			LOG.info("getFeesForDisconnectedUser" + fees);
			int totalAmount = getDisconnectedUsersBackDues(id, dodc);
			fees = totalAmount + Integer.valueOf(backDues);
		}
		LOG.info("getFeesForDisconnectedUser EXIT");
		return String.valueOf(fees);
	}



	
}
