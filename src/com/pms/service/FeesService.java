package com.pms.service;

import java.util.HashMap;
import java.util.List;

import com.pms.entity.AllFeesDetails;
import com.pms.entity.User;

public interface FeesService {
	public int getSkippedMonthBackDues(Integer id, Integer endYear, String month);

	public String calculateRecomputedAmount(User user, String month, String year);

	public User getUserFromUserList(Integer id, List<User> userList);

	public AllFeesDetails readProvidedMonthFeesDetails(Integer id, String month, Integer year);
	
	public void updateSkippedMonthFeesToZero(Integer id, Integer endYear, String month) ;
	public String getBackDues(int id);
	public Integer isUserPaidFees(User user, String month, Integer year);
	public List<AllFeesDetails> readFeesDetails(Integer id);
	public Integer getBackDuesAmountTobeCollected();
	public Integer getProposedAmountTobeCollected() ;
	public Integer getDiscountProvidedThisMonth() ;
	public Integer saveFeesDetails(User user, AllFeesDetails feesDetails);
	public HashMap<String,Integer> getYearlyRevenue(Integer year);
}
