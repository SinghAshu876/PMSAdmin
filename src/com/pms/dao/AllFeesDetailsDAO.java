package com.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.AllFeesDetails;
import com.pms.entity.User;
import com.pms.util.DBConstants;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class AllFeesDetailsDAO implements DBConstants{
	
	private Logger LOG = Logger.getLogger(getClass());

	public Integer saveFeesDetails(User user, AllFeesDetails feesDetails) {
		LOG.info("saveFeesDetails ENTRY");
		Integer returnValue = 0;
		if (isUserPaidFees(user, feesDetails.getMonth(), feesDetails.getYear()) != null) {
			returnValue = updateFeesDetails(feesDetails);
		} else {
			returnValue = insertFeesDetails(feesDetails);
		}
		LOG.info("saveFeesDetails EXIT");
		return returnValue;
	}

	private int insertFeesDetails(AllFeesDetails feesDetails) {
		LOG.info("insertFeesDetails ENTRY");
		int success = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = INSERT_ALL_FEES_DETAILS_QUERY;
		LOG.info(" insertFeesDetails SQL EXECUTED"+sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, feesDetails.getId());
			preparedStatement.setInt(2, feesDetails.getYear());
			preparedStatement.setString(3, feesDetails.getMonth());
			preparedStatement.setDate(4, feesDetails.getFeesInsertionDate());
			preparedStatement.setInt(5, Integer.valueOf(feesDetails.getFeesPaid()));
			preparedStatement.setInt(6, feesDetails.getDiscount());
			preparedStatement.setInt(7, PMSUtility.getMonthSequence(feesDetails.getMonth()));
			success = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY INSERTED ALL_FEES_DETAILS INTO TABLE");

		} catch (SQLException e) {
			LOG.error("Db problem in insertFeesDetails",e);
			
		}
		LOG.info("insertFeesDetails EXIT");
		return success;
	}

	private Integer updateFeesDetails(AllFeesDetails feesDetails) {
		LOG.info("updateFeesDetails ENTRY");
		Integer id = feesDetails.getId();
		Integer year = feesDetails.getYear();
		String month = feesDetails.getMonth();
		Integer discount = feesDetails.getDiscount();
		String feesPaid = feesDetails.getFeesPaid();
		Integer feesPaidInt = Integer.valueOf(feesPaid);
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = "update ALL_FEES_DETAILS set fees_paid=" + feesPaidInt + ",discount=" + discount + ",fees_insertion_date=DATE '" + feesDetails.getFeesInsertionDate() + "' where id=" + id + "and year=" + year + "and month =UPPER('" + month + "')";
		LOG.info("updateFeesDetails SQL EXECUTED"+sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			returnValue = preparedStatement.executeUpdate();
			LOG.info("SUCCESSFULLY UPDATED ALL_FEES_DETAILS INTO TABLE");
		} catch (SQLException e) {
			LOG.error("Db problem in updateFeesDetails",e);
		}
		LOG.info("updateFeesDetails EXIT");
		return returnValue;
	}

	public Integer getAmountCollectedCurrentMonth(String month, String year) {
		LOG.info("getAmountCollectedCurrentMonth ENTRY");
		Integer sumcurrentMonth = -1;
		Connection connection = JDBCConnection.getConnection();
		String sql = "select sum(" + month + ") as sum from  fees_details where year='" + year + "' and id in (select u.id from user  u where active='Y')";
		LOG.info("getAmountCollectedCurrentMonth SQL EXECUTED" +sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				sumcurrentMonth = rs.getInt("sum");
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getAmountCollectedCurrentMonth",e);
			
		}
		LOG.info("getAmountCollectedCurrentMonth EXIT");
		return sumcurrentMonth;
	}

	public Integer getProposedAmountTobeCollected() {
		LOG.info("getProposedAmountTobeCollected ENTRY");
		Integer sumcurrentMonth = -1;
		Connection connection = JDBCConnection.getConnection();
		String sql = AMT_2B_COLLECTED_QUERY;
		LOG.info("getProposedAmountTobeCollected SQL EXECUTED" +sql);
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				sumcurrentMonth = rs.getInt("TOTALSUM");
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getProposedAmountTobeCollected", e);
			
		}
		LOG.info("getProposedAmountTobeCollected EXIT");
		return sumcurrentMonth;

	}

	public Integer getBackDuesAmountTobeCollected() {
		LOG.info("getBackDuesAmountTobeCollected ENTRY");
		Integer sumcurrentMonth = -1;
		Connection connection = JDBCConnection.getConnection();
		String sql = BACKDUES_AMOUNT_QUERY;
		try  (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				sumcurrentMonth = rs.getInt("BACKDUESSUM");
			}
		} catch (SQLException e) {
			LOG.error("Db problem in getBackDuesAmountTobeCollected",e);
			
		}
		LOG.info("getBackDuesAmountTobeCollected EXIT");
		return sumcurrentMonth;

	}

	public List<AllFeesDetails> readFeesDetails(Integer id) {
		LOG.info("readFeesDetails ENTRY");
		List<AllFeesDetails> feesDetailsList = new ArrayList<AllFeesDetails>();
		Connection connection = JDBCConnection.getConnection();
		String sql = READ_FEESDETAILS_QUERY;
		LOG.info("readFeesDetails SQL "+ sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					AllFeesDetails allFeesDetails = new AllFeesDetails();
					allFeesDetails.setId(rs.getInt(ID));
					allFeesDetails.setYear(rs.getInt(YEAR));
					allFeesDetails.setMonth(rs.getString(MONTH));
					allFeesDetails.setDiscount(rs.getInt(DISCOUNT));
					allFeesDetails.setFeesInsertionDate(rs.getDate(FEES_INSERTION_DATE));
					allFeesDetails.setFeesPaid(rs.getString(FEES_PAID));
					allFeesDetails.setMonthSequence(rs.getInt(MONTH_SEQUENCE));
					feesDetailsList.add(allFeesDetails);
				}
			}
		}catch (SQLException e) {
			LOG.error("Db problem in readFeesDetails",e);
		}
		LOG.info("readFeesDetails EXIT");
		return feesDetailsList;
	}

	public Integer delete(Integer id) throws SQLException {
		LOG.info("delete ENTRY");
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = DELETE_ALL_FEES_DETAILS_QUERY;
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

	/**
	 * this query will fetch , if the user paid fees
	 * 
	 * @param user
	 * @param month
	 * @param year
	 * @return
	 */
	public Integer isUserPaidFees(User user, String month, Integer year) {
		LOG.info("isUserPaidFees ENTRY");
		Integer feesPaid = null;
		Connection connection = JDBCConnection.getConnection();
		Integer id = user.getId();
		String sql = IS_USER_PAID_FEES_QUERY;
		LOG.info("isUserPaidFees "+sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, month);
			preparedStatement.setInt(3, year);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					feesPaid = rs.getInt(FEES_PAID);
				}
			}
		}catch (Exception e) {
			LOG.error("PROBLEM RETRIEVING  isUserPaidFees ", e);
			
		}
		LOG.info("isUserPaidFees EXIT");
		return feesPaid;
	}

	public String getBackDues(int id) {
		LOG.info("getBackDues ENTRY");
		Integer monthlyBackDues = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = BACKDUES_QUERY;
		LOG.info("getBackDues sql :" + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					monthlyBackDues = rs.getInt("TOTALSUM");
				}
			}
		} catch (Exception e) {
			LOG.error("PROBLEM RETRIEVING  BACK_DUES ", e);
			
		}
		LOG.info(" monthlyBackDues :" + monthlyBackDues);
		LOG.info("getBackDues EXIT ");
		return String.valueOf(monthlyBackDues);
	}

	public Integer getSumOfFeesReceived(Integer id) {
		LOG.info("getSumOfFeesReceived ENTRY");
		Connection connection = JDBCConnection.getConnection();
		int totalSumCollected = 0;
		String sql = SUMOFFEES_RECEIVED_QUERY;
		LOG.info("getSumOfFeesReceived sql :" + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					totalSumCollected = rs.getInt("COLLECTED_AMOUNT");
				}
			}
		}catch (Exception e) {
			LOG.error("PROBLEM RETRIEVING  getSumOfFeesCollected ", e);
		
		}
		LOG.info("getSumOfFeesReceived :"+totalSumCollected);
		LOG.info("getSumOfFeesReceived EXIT");
		return totalSumCollected;
	}

	/**
	 * all skipped months -1 will be updated to zero
	 * 
	 * @param id
	 * @param year
	 * @param month
	 * @return
	 */
	public void updateSkippedMonthFeesToZero(Integer id, Integer endYear, String month) {
		LOG.info("updateSkippedMonthFeesToZero ENTRY");
		Connection connection = JDBCConnection.getConnection();
		User user = new UserDAO().readUserById(id);
		int startYear = PMSUtility.getYear(user.getDoc());
		int startMonth = PMSUtility.getMonthSequence(PMSUtility.getMonth(user.getDoc()));
		if (startMonth == PMSUtility.getMonthSequence(month) && startYear == endYear) {
			return;
		}
		int endMonth = PMSUtility.getMonthSequence(month) - 1;

		if (endYear >= startYear) {
			for (; endYear >= startYear; startMonth++) {
				String sql = "select count(1) as valueExist from all_fees_details where year =" + startYear + "and month_sequence=" + startMonth + "and id =" + id;
				LOG.info("updateSkippedMonthFeesToZero sql :" + sql);
				try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
					while (rs.next()) {
						if (rs.getInt("valueExist") == 0) {
							AllFeesDetails feesDetails = new AllFeesDetails();
							feesDetails.setDiscount(0);
							feesDetails.setFeesInsertionDate(PMSUtility.convertToSqlDate(new Date()));
							feesDetails.setFeesPaid("0");
							feesDetails.setId(user.getId());
							feesDetails.setMonth(PMSUtility.getMonthStringFromValue(startMonth));
							feesDetails.setYear(startYear);
							saveFeesDetails(user, feesDetails);
						}
					}
					LOG.info("SUCCESSFULLY RETRIEVED getSkippedMonthDetails ");
				} catch (Exception e) {
					LOG.info("PROBLEM RETRIEVING  getSkippedMonthDetails ");
				}
				if (startMonth == 12) {
					startMonth = 0;
					startYear++;
				}
				if (endYear == startYear && endMonth == startMonth) {
					break;
				}

			}
		} else {
			LOG.info("Contact developer");
			throw new IllegalArgumentException("What the hell ! How is that possible ");
		}
		LOG.info("updateSkippedMonthFeesToZero EXIT");
	}

	public AllFeesDetails readFeesDetails(Integer id, String month, Integer year) {
		LOG.info("readFeesDetails ENTRY");
		AllFeesDetails allFeesDetails = null;
		Connection connection = JDBCConnection.getConnection();
		String sql = READFEES_DETAILS_QUERY;
		LOG.info("readFeesDetails Query:" + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, month);
			preparedStatement.setInt(3, year);		
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					allFeesDetails = new AllFeesDetails();
					allFeesDetails.setId(rs.getInt(ID));
					allFeesDetails.setYear(rs.getInt(YEAR));
					allFeesDetails.setMonth(rs.getString(MONTH));
					allFeesDetails.setDiscount(rs.getInt(DISCOUNT));
					allFeesDetails.setFeesInsertionDate(rs.getDate(FEES_INSERTION_DATE));
					allFeesDetails.setFeesPaid(rs.getString(FEES_PAID));
					allFeesDetails.setMonthSequence(rs.getInt(MONTH_SEQUENCE));

				}
			}
		}catch (SQLException e) {
			LOG.error("problem in readFeesDetails ", e);
		
		}
		LOG.info("readFeesDetails EXIT");
		return allFeesDetails;
	}

	/**
	 * This is for initial back dues update operation from UPDATE USER SCREEN
	 * 
	 * @param id
	 * @param year
	 * @param month
	 * @param amount
	 * @return
	 */
	public Integer updateBackDues(Integer id, Integer amount) {
		LOG.info("updateBackDues ENTRY");
		Integer returnValue = 0;
		Connection connection = JDBCConnection.getConnection();
		String sql = UPDATE_BACKDUES_QUERY;
		LOG.info("updateBackDues SQL :"+ sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, amount);
			preparedStatement.setInt(2, id);
			returnValue = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOG.error("Db problem in updateBackDues", e);
	
		}
		LOG.info("updateBackDues EXIT");
		return returnValue;

	}

	public Integer getDiscountProvidedThisMonth() {
		LOG.info("getDiscountProvidedThisMonth ENTRY");
        Integer discountAmount =0;
        Date today = new Date();
        String month = PMSUtility.getMonth(today);
        Integer year = PMSUtility.getYear(today);
		Connection connection = JDBCConnection.getConnection();
		String sql = DISCOUNT_THIS_MONTH_QUERY;
		LOG.info("getDiscountProvidedThisMonth Query:" + sql);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, year);
			preparedStatement.setString(2, month);
			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					discountAmount = rs.getInt("dicount");
				}
			}
		}catch (SQLException e) {
			LOG.error("problem in getDiscountProvidedThisMonth ", e);

		}
		LOG.info("getDiscountProvidedThisMonth EXIT");
		return discountAmount;
	
	}

	public  HashMap<String,Integer>  getYearlyRevenue(Integer year) {
		LOG.info("getYearlyRevenue ENTRY");
		HashMap<String,Integer> revenueMap= new HashMap<String,Integer>(0);
		String month = null;
		for(int startMonth=1;startMonth<13;startMonth++){
			month = PMSUtility.getMonthStringFromValue(startMonth);
			Connection connection = JDBCConnection.getConnection();
			String sql = YEARLY_REVENUE_QUERY;
			LOG.info("getYearlyRevenue Query:" + sql);
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, year);
				preparedStatement.setString(2, month);
				try (ResultSet rs = preparedStatement.executeQuery()) {
					while (rs.next()) {
						revenueMap.put(month, rs.getInt(FEES_PAID));
					}
				}
			}catch (SQLException e) {
				LOG.error("problem in getYearlyRevenue", e);

			}
		}
		
		LOG.info("getYearlyRevenue EXIT");
		return revenueMap;
	}

}
