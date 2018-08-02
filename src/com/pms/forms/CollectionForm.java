package com.pms.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.pms.custom.components.ColoredJPanel;
import com.pms.custom.components.PMSJTextField;
import com.pms.enums.util.StaticCodes;
import com.pms.service.impl.FeesServiceImpl;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.YearlyRevenuePrintTable;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class CollectionForm implements ApplicationConstants {

	private  Logger LOG = Logger.getLogger(getClass());
	private JFrame collectionFormFrame = null;
	private Integer yearComboBoxValue = PMSUtility.initialiseCurrentYear();
	private int xCordinateOfLabel = 40;
	private int xCordinateOfRHSLabel = 550;
	private int xCordinateOfRHSComponent = 900;
	private int txtBoxWidth = 220;
	private int xCordinateOfTextBox = 320;
	private Integer yValue = 50;
	private Integer yValueRHS = 50;
	private JPanel panel = null;
	private JTextField collectionAmountTxt = null;
	private JTextField backDuesAmountTxt = null;
	private JTextField noOfNewConnTxt = null;
	private JTextField newConnectionAmountTxt = null;
	private JTextField totalRevenueText = null;
	private JTextField discountText = null;
	
	private FeesServiceImpl feesServiceImpl = new FeesServiceImpl();
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	private Integer getIncrementedValue(int height, boolean increase) {

		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}
	
	private Integer getIncrementedValue4RHS(int height, boolean increase) {
		if (increase) {
			return yValueRHS = height + 40;
		} else
			return height;
	}

	public void init(JFrame parentFrame) {
		LOG.info("init ENTRY");
		parentFrame.setVisible(false);
		collectionFormFrame = new JFrame("COLLECTION REPORT");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		collectionFormFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		collectionFormFrame.setSize(screenSize);
		collectionFormFrame.setVisible(true);
		collectionFormFrame.setResizable(false);
		collectionFormFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new ColoredJPanel();
		collectionFormFrame.add(panel);
		placeComponents(panel);
		Container.frameContainer.put("COLLECTION-REPORT-FRAME", collectionFormFrame);
		LOG.info("init EXIT");

	}

	private void placeComponents(JPanel panel) {
		LOG.info("placeComponents ENTRY");
		Integer proposedAmount = feesServiceImpl.getProposedAmountTobeCollected();
		Integer newConnectionAmountText = userServiceImpl.getRevenueFromNewConnectionsThisMonth();
		Integer noOfNewConnections= userServiceImpl.getNoOfNewConnectionsThisMonth();
		Integer backDuesCollectionAmount = feesServiceImpl.getBackDuesAmountTobeCollected();
		Integer targetTotalAmount = proposedAmount + backDuesCollectionAmount;
		Integer totalRevenue = targetTotalAmount + newConnectionAmountText;
		Integer discountAmount = feesServiceImpl.getDiscountProvidedThisMonth();
		
		JLabel noOfNewConnectionLabel = new JLabel("NUMBER OF NEW CONNECTION IN THIS MONTH :");
		noOfNewConnectionLabel.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValueRHS, false), 400, 20);
		panel.add(noOfNewConnectionLabel);

		noOfNewConnTxt = new PMSJTextField(20);
		noOfNewConnTxt.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValueRHS, false), 100, COMPONENT_HEIGHT);
		noOfNewConnTxt.setText(String.valueOf(noOfNewConnections));
		noOfNewConnTxt.setDisabledTextColor(Color.BLACK);
		noOfNewConnTxt.setEnabled(false);
		noOfNewConnTxt.setEditable(false);
		panel.add(noOfNewConnTxt);

		JLabel totalAmnt = new JLabel("COLLECTION AMOUNT :");
		totalAmnt.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), 300, 20);
		panel.add(totalAmnt);

		collectionAmountTxt = new PMSJTextField(20);
		collectionAmountTxt.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), txtBoxWidth, COMPONENT_HEIGHT);
		collectionAmountTxt.setText(String.valueOf(proposedAmount));
		collectionAmountTxt.setDisabledTextColor(Color.BLACK);
		collectionAmountTxt.setEnabled(false);
		collectionAmountTxt.setEditable(false);
		panel.add(collectionAmountTxt);

		JLabel backDuesAmount = new JLabel("BACK DUES COLLECTION AMOUNT:");
		backDuesAmount.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), 300, 20);
		panel.add(backDuesAmount);

		backDuesAmountTxt = new PMSJTextField(20);
		backDuesAmountTxt.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), txtBoxWidth, COMPONENT_HEIGHT);
		backDuesAmountTxt.setText(String.valueOf(backDuesCollectionAmount));
		backDuesAmountTxt.setDisabledTextColor(Color.BLACK);
		backDuesAmountTxt.setEnabled(false);
		backDuesAmountTxt.setEditable(false);
		panel.add(backDuesAmountTxt);

		JLabel newConnectionAmountLabel = new JLabel("NEW CONNECTION CHARGE AMOUNT :");
		newConnectionAmountLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), 300, 20);
		panel.add(newConnectionAmountLabel);

		newConnectionAmountTxt = new PMSJTextField(20);
		newConnectionAmountTxt.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), txtBoxWidth, COMPONENT_HEIGHT);
		newConnectionAmountTxt.setText(String.valueOf(newConnectionAmountText));
		newConnectionAmountTxt.setDisabledTextColor(Color.BLACK);
		newConnectionAmountTxt.setEnabled(false);
		newConnectionAmountTxt.setEditable(false);
		panel.add(newConnectionAmountTxt);

		JLabel totalRevenueLabel = new JLabel("TOTAL REVENUE THIS MONTH:");
		totalRevenueLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), 300, 20);
		panel.add(totalRevenueLabel);

		totalRevenueText = new PMSJTextField(20);
		totalRevenueText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), txtBoxWidth, COMPONENT_HEIGHT);
		totalRevenueText.setText(String.valueOf(totalRevenue));
		totalRevenueText.setDisabledTextColor(Color.BLACK);
		totalRevenueText.setEnabled(false);
		totalRevenueText.setEditable(false);
		panel.add(totalRevenueText);
		
		JLabel discountLabel = new JLabel("DISCOUNT AWARDED THIS MONTH:");
		discountLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), 300, 20);
		panel.add(discountLabel);

		discountText = new PMSJTextField(20);
		discountText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), txtBoxWidth, COMPONENT_HEIGHT);
		discountText.setText(String.valueOf(discountAmount));
		discountText.setDisabledTextColor(Color.BLACK);
		discountText.setEnabled(false);
		discountText.setEditable(false);
		panel.add(discountText);
		
		JTextField dummyTextBox = new PMSJTextField(20);
		dummyTextBox.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, true), txtBoxWidth, COMPONENT_HEIGHT);
		dummyTextBox.setVisible(false);
		panel.add(dummyTextBox);
		

		JComboBox<Integer> feesPaidYear = new JComboBox<Integer>();
		feesPaidYear.setEditable(false);
		Integer[] years = StaticCodes.yearArray;
		for (int i = 0; i < years.length; i++) {
			feesPaidYear.addItem(years[i]);
		}
		feesPaidYear.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), txtBoxWidth, COMPONENT_HEIGHT);
		feesPaidYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox source = (JComboBox) e.getSource();
				yearComboBoxValue = (Integer) source.getSelectedItem();

			}
		});
		panel.add(feesPaidYear);
		
		JButton generateButton = new JButton("YEARLY REVENUE REPORT");
		generateButton.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), txtBoxWidth, COMPONENT_HEIGHT);
		generateButton.addActionListener(new GenerateButtonHandler());
		panel.add(generateButton);



		JButton backButton = new JButton("BACK");
		backButton.setBounds(550, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);
		LOG.info("placeComponents EXIT");
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("COLLECTION-REPORT-FRAME")).setVisible(false);
		}

	}


	private class GenerateButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			collectionFormFrame.setVisible(false);
			YearlyRevenuePrintTable simplePrintTable = new YearlyRevenuePrintTable(yearComboBoxValue);
			simplePrintTable.showInTabularForm();

		}

		
	}

}
