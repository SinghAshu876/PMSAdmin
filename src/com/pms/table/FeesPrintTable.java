package com.pms.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.AllFeesDetails;
import com.pms.to.FeesDetailsDummy;
import com.pms.service.impl.FeesServiceImpl;
import com.pms.table.editor.MonthlyDetailsPrintTableEditor;
import com.pms.table.model.FeesTableModel;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class FeesPrintTable extends MonthlyDetailsPrintTableEditor {

	private  Logger LOG = Logger.getLogger(getClass());
	private Integer id;
	private FeesServiceImpl feesServiceImpl = new FeesServiceImpl();

	public FeesPrintTable(Integer id) {
		this.id = id;
	}

	public void showInTabularForm() {

        LOG.info("showInTabularForm ENTRY");
		List<AllFeesDetails> feesDetailsList = feesServiceImpl.readFeesDetails(getId());
		LinkedHashMap<Integer, List<FeesDetailsDummy>> feesDetailsDummyMap = new LinkedHashMap<Integer, List<FeesDetailsDummy>>();
		for (AllFeesDetails allfees : feesDetailsList) {
			if (feesDetailsDummyMap.containsKey(allfees.getYear())) {
				FeesDetailsDummy feesDetailsDummy = new FeesDetailsDummy();
				feesDetailsDummy.setDiscount(Integer.valueOf(allfees.getDiscount()));
				feesDetailsDummy.setFeesPaid(Integer.valueOf(allfees.getFeesPaid()));
				feesDetailsDummy.setPostingDate(allfees.getFeesInsertionDate());
				feesDetailsDummy.setMonth(allfees.getMonth());
				feesDetailsDummyMap.get(allfees.getYear()).add(feesDetailsDummy);
			} else {
				List<FeesDetailsDummy> tempList = new ArrayList<FeesDetailsDummy>();
				FeesDetailsDummy feesDetailsDummy = new FeesDetailsDummy();
				feesDetailsDummy.setFeesPaid(Integer.valueOf(allfees.getFeesPaid()));
				feesDetailsDummy.setDiscount(Integer.valueOf(allfees.getDiscount()));
				feesDetailsDummy.setPostingDate(allfees.getFeesInsertionDate());
				feesDetailsDummy.setMonth(allfees.getMonth());
				tempList.add(feesDetailsDummy);
				feesDetailsDummyMap.put(allfees.getYear(), tempList);
			}

		}

		FeesTableModel model = new FeesTableModel(feesDetailsDummyMap);
		super.prepareTable("FEES PRINT", model);
		 LOG.info("showInTabularForm EXIT");

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
