package com.pms.forms;

import static com.pms.util.PMSUtility.convertToSqlDate;
import static com.pms.util.PMSUtility.findFeeCode;
import static com.pms.util.PMSUtility.getMonth;
import static com.pms.util.PMSUtility.getMonthYearValue;
import static com.pms.util.PMSUtility.getYear;
import static com.pms.util.PMSUtility.getYearString;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.AbstractDocument;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.pms.custom.components.ColoredJPanel;
import com.pms.custom.components.PMSJComboBox;
import com.pms.custom.components.PMSJTextField;
import com.pms.custom.components.PMSJXDatePicker;
import com.pms.document.filters.NumberTextFieldDocumentFilter;
import com.pms.entity.AllFeesDetails;
import com.pms.entity.FeesHistory;
import com.pms.entity.User;
import com.pms.enums.util.StaticCodes;
import com.pms.service.impl.FeesServiceImpl;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.FeesPrintTable;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.validator.FeesValidator;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class MonthlyPaidForm implements ApplicationConstants {
	private Logger LOG = Logger.getLogger(getClass());

	private PMSJTextField yearLabelText = null;
	private PMSJTextField monthLabelText = null;
	private JFrame monthlyPaidFormFrame = null;
	private JXDatePicker postingDatePicker = null;
	private JTextField slNOText = null;
	private JTextField searchText = null;
	private JTextField customerNameText = null;
	private JTextField qrNoText = null;
	private JTextField streetText = null;
	private JTextField sectorText = null;
	private JTextField sectorText2 = null;
	private JTextField backDuesText = null;
	private JTextField mobNumberText = null;
	private JTextField setTopBoxNumberText = null;
	private JTextField cafNumberText = null;
	private JTextField discountText = null;
	private JButton saveMonthlyFeesButton = null;
	private JButton monthlyFeesReportButton = null;
	private JXDatePicker picker = null;
	private JTextField feeCodeText = null;
	private int headerFooterWidth = 170;
	private int xCordinateOfLabel = 20;
	private int xCordinateOfTextBox = 200;
	private int componentWidth = 190;
	private int rhsComponentWidth = 190;
	private int xCordinateOfRHSLabel = 590;
	private int xCordinateOfRHSComponent = 770;
	private String payableTemp = EMPTY_STRING;
	private Integer id;
	private JTextField feesPayableText = null;
	private JTextField feesPaidText = null;
	private JPanel panel = new ColoredJPanel();
	private User user = null;
	private Integer yValue = 30;
	private Integer yValue4RHS = 70;
	private String zeroFees = "0";
	private JLabel inactiveUserLabel = null;
	private PMSJComboBox<String> userComboBox = new PMSJComboBox<String>();
	private String userComboBoxvalue = null;
	List<User> userList = new ArrayList<User>();
	private Date today = new Date();
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private FeesServiceImpl feesServiceImpl = new FeesServiceImpl();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer getIncrementedValue(int height, boolean increase) {
		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	private Integer getIncrementedValue4RHS(int height, boolean increase) {
		if (increase) {
			return yValue4RHS = height + 40;
		} else
			return height;
	}

	public void init() {
		LOG.info("init ENTRY");
		monthlyPaidFormFrame = new JFrame("MONTHLY PAID ");
		JFrame parentFrame = (JFrame) Container.frameContainer.get("MAIN-MENU-FRAME");
		parentFrame.setVisible(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		monthlyPaidFormFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		monthlyPaidFormFrame.setSize(screenSize);
		monthlyPaidFormFrame.setVisible(true);
		monthlyPaidFormFrame.setResizable(false);
		monthlyPaidFormFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		monthlyPaidFormFrame.add(panel);
		userList = userServiceImpl.readUsers();
		placeComponents(panel);
		Container.frameContainer.put("MONTHLY-PAID-FRAME", monthlyPaidFormFrame);
		LOG.info("init EXIT");
	}

	private void placeComponents(JPanel panel) {
		LOG.info("placeComponents ENTRY");
		BasicArrowButton leftArrowButton = new BasicArrowButton(BasicArrowButton.WEST);
		leftArrowButton.setBounds(xCordinateOfTextBox, 20, componentWidth, COMPONENT_HEIGHT);
		leftArrowButton.addActionListener(new LeftArrowButtonHandler());
		panel.add(leftArrowButton);

		monthLabelText = new PMSJTextField(20);
		monthLabelText.setBounds(400, 20, headerFooterWidth, COMPONENT_HEIGHT);
		monthLabelText.setText(null);
		monthLabelText.setEnabled(false);
		monthLabelText.setEditable(false);
		monthLabelText.setDisabledTextColor(Color.BLACK);
		panel.add(monthLabelText);

		yearLabelText = new PMSJTextField(20);
		yearLabelText.setBounds(xCordinateOfRHSLabel, 20, headerFooterWidth, COMPONENT_HEIGHT);
		yearLabelText.setText(null);
		yearLabelText.setEnabled(false);
		yearLabelText.setEditable(false);
		yearLabelText.setDisabledTextColor(Color.BLACK);
		panel.add(yearLabelText);

		BasicArrowButton rightArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
		rightArrowButton.setBounds(xCordinateOfRHSComponent, 20, rhsComponentWidth, COMPONENT_HEIGHT);
		rightArrowButton.addActionListener(new RightArrowButtonHandler());
		panel.add(rightArrowButton);

		userComboBox.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), 370, COMPONENT_HEIGHT);
		for (User user : userList) {
			userComboBox.addItem(user.getId()+ DASH + user.getStreet() + DASH + user.getCustomerName() + DASH + user.getQrNo());
		}
		userComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox source = (JComboBox) e.getSource();
				userComboBoxvalue = (String) source.getSelectedItem();
				String idArray[] = userComboBoxvalue.split(DASH);
				Integer id = Integer.valueOf(idArray[0]);
				user = feesServiceImpl.getUserFromUserList(id, userList);
				searchText.setText(String.valueOf(id));
				updateValuesInTextBoxes(user);

			}
		});
		panel.add(userComboBox);

		JLabel searchLabel = new JLabel("SEARCH:");
		searchLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(searchLabel);

		searchText = new PMSJTextField(20);
		searchText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) searchText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(6));
		searchText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					try {
						fireQuery(Integer.valueOf(searchText.getText()));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "PLEASE ENTER A VALID ID !");
						return;
					}

				}
			}

		});
		panel.add(searchText);

		JLabel slNO = new JLabel("ID NO:");
		slNO.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(slNO);

		slNOText = new PMSJTextField(20);
		slNOText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		slNOText.setText(null);
		slNOText.setEnabled(false);
		slNOText.setEditable(false);
		slNOText.setDisabledTextColor(Color.BLACK);
		panel.add(slNOText);

		JLabel customerName = new JLabel("CUSTOMER NAME:");
		customerName.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(customerName);

		customerNameText = new PMSJTextField(20);
		customerNameText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		customerNameText.setText(null);
		customerNameText.setEnabled(false);
		customerNameText.setEditable(false);
		customerNameText.setDisabledTextColor(Color.BLACK);
		panel.add(customerNameText);

		JLabel qrnoLabel = new JLabel("QR NO:");
		qrnoLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(qrnoLabel);

		qrNoText = new PMSJTextField(20);
		qrNoText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		qrNoText.setText(null);
		qrNoText.setEnabled(false);
		qrNoText.setEditable(false);
		qrNoText.setDisabledTextColor(Color.BLACK);
		panel.add(qrNoText);

		JLabel street = new JLabel("STREET:");
		street.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(street);

		streetText = new PMSJTextField(20);
		streetText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		streetText.setEnabled(false);
		streetText.setEditable(false);
		streetText.setDisabledTextColor(Color.BLACK);
		panel.add(streetText);

		JLabel sector = new JLabel("SECTOR:");
		sector.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(sector);

		sectorText = new PMSJTextField(20);
		sectorText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth / 3, COMPONENT_HEIGHT);
		sectorText.setEnabled(false);
		sectorText.setEditable(false);
		sectorText.setDisabledTextColor(Color.BLACK);
		sectorText2 = new PMSJTextField(20);
		sectorText2.setBounds(280, getIncrementedValue(yValue, false), componentWidth / 5, COMPONENT_HEIGHT);
		sectorText2.setEnabled(false);
		sectorText2.setEditable(false);
		sectorText2.setDisabledTextColor(Color.BLACK);
		panel.add(sectorText);
		panel.add(sectorText2);

		JLabel doc = new JLabel("DATE OF CONNECTION:");
		doc.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(doc);

		picker = new PMSJXDatePicker();
		picker.setEditable(false);
		picker.setEnabled(false);
		picker.setFormats(new SimpleDateFormat(DATE_PICKER_PATTERN));
		picker.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(picker);

		JLabel mobileNumber = new JLabel("MOBILE NUMBER:");
		mobileNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(mobileNumber);

		mobNumberText = new PMSJTextField(20);
		mobNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		mobNumberText.setEnabled(false);
		mobNumberText.setEditable(false);
		mobNumberText.setDisabledTextColor(Color.BLACK);
		panel.add(mobNumberText);

		JLabel setTopBoxNumber = new JLabel("SET-TOP BOX NUMBER:");
		setTopBoxNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(setTopBoxNumber);

		setTopBoxNumberText = new PMSJTextField(20);
		setTopBoxNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		setTopBoxNumberText.setEnabled(false);
		setTopBoxNumberText.setEditable(false);
		setTopBoxNumberText.setDisabledTextColor(Color.BLACK);
		panel.add(setTopBoxNumberText);

		JLabel cafNumbers = new JLabel("CAF NUMBER:");
		cafNumbers.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(cafNumbers);

		cafNumberText = new PMSJTextField(20);
		cafNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		cafNumberText.setEnabled(false);
		cafNumberText.setEditable(false);
		cafNumberText.setDisabledTextColor(Color.BLACK);
		panel.add(cafNumberText);

		JButton backButton = new JButton("BACK");
		backButton.setBounds(400, getIncrementedValue(yValue, true), headerFooterWidth, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);

		monthlyFeesReportButton = new JButton("MONTHLY DETAILS");
		monthlyFeesReportButton.setBounds(590, getIncrementedValue(yValue, false), headerFooterWidth, COMPONENT_HEIGHT);
		monthlyFeesReportButton.addActionListener(new MonthlyReportButtonHandler());
		panel.add(monthlyFeesReportButton);

		/** Right hand side component starts */

		JLabel postingDate = new JLabel("POSTING DATE:");
		postingDate.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		panel.add(postingDate);

		postingDatePicker = new PMSJXDatePicker();
		postingDatePicker.setDate(Calendar.getInstance().getTime());
		postingDatePicker.setFormats(new SimpleDateFormat(DATE_PICKER_PATTERN));
		postingDatePicker.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		panel.add(postingDatePicker);

		JLabel backDues = new JLabel("BACK DUES:");
		backDues.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValue4RHS, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(backDues);

		backDuesText = new PMSJTextField(20);
		backDuesText.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) backDuesText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(10));
		backDuesText.setDisabledTextColor(Color.BLACK);
		panel.add(backDuesText);

		JLabel feeCodeLabel = new JLabel("FEE CODE:");
		feeCodeLabel.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValue4RHS, true), rhsComponentWidth, COMPONENT_HEIGHT);
		panel.add(feeCodeLabel);

		feeCodeText = new PMSJTextField(20);
		feeCodeText.setEditable(false);
		feeCodeText.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		panel.add(feeCodeText);

		JLabel discount = new JLabel("DISCOUNT:");
		discount.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValue4RHS, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(discount);

		discountText = new PMSJTextField(20);
		discountText.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) discountText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(5));
		discountText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (StaticCodes.codeMap.keySet().contains(arg0.getKeyChar())) {
					payableTemp = payableTemp + arg0.getKeyChar();
					String fee = feesServiceImpl.calculateRecomputedAmount(user, monthLabelText.getText(), yearLabelText.getText());
					Integer integerFee = Integer.valueOf(fee);
					feesPayableText.setText(String.valueOf(integerFee - Integer.valueOf(payableTemp)));

				}
				if (arg0.getKeyCode() == 8 && !payableTemp.isEmpty()) {
					discountText.setText(zeroFees);
					payableTemp = EMPTY_STRING;
					String fee = feesServiceImpl.calculateRecomputedAmount(user, monthLabelText.getText(), yearLabelText.getText());
					feesPayableText.setText(fee);

				}

			}

		});

		panel.add(discountText);

		JLabel feesPayableLabel = new JLabel("TOTAL PAYABLE:");
		feesPayableLabel.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValue4RHS, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(feesPayableLabel);

		feesPayableText = new PMSJTextField(20);
		feesPayableText.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) feesPayableText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(10));
		feesPayableText.setDisabledTextColor(Color.BLACK);
		panel.add(feesPayableText);

		JLabel feesPaidLabel = new JLabel("FEES PAID:");
		feesPaidLabel.setBounds(xCordinateOfRHSLabel, getIncrementedValue4RHS(yValue4RHS, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(feesPaidLabel);

		feesPaidText = new PMSJTextField(20);
		feesPaidText.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) feesPaidText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(10));
		panel.add(feesPaidText);

		saveMonthlyFeesButton = new JButton("SAVE FEES");
		saveMonthlyFeesButton.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, true), rhsComponentWidth, COMPONENT_HEIGHT);
		saveMonthlyFeesButton.addActionListener(new SaveMonthlyFeesButtonHandler());
		panel.add(saveMonthlyFeesButton);

		inactiveUserLabel = new JLabel("INACTIVE USER", JLabel.CENTER);
		inactiveUserLabel.setForeground(Color.RED);
		inactiveUserLabel.setBounds(xCordinateOfRHSComponent, getIncrementedValue4RHS(yValue4RHS, true), rhsComponentWidth, COMPONENT_HEIGHT);
		inactiveUserLabel.setVisible(false);
		panel.add(inactiveUserLabel);
		LOG.info("placeComponents EXIT");

	}

	private class SaveMonthlyFeesButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().beep();
			LOG.info("SaveMonthlyFeesButtonHandler ENTRY");
			AllFeesDetails feesDetails = new AllFeesDetails();
			feesDetails.setId(getId());
			Date postingDate = postingDatePicker.getDate();
			if (postingDatePicker.getDate() == null) {
				JOptionPane.showMessageDialog(null, "PLEASE SELECT POSTING DATE !");
				return;
			}
			if (yearLabelText.getText().equals(EMPTY_STRING) || monthLabelText.getText().equals(EMPTY_STRING)) {
				JOptionPane.showMessageDialog(null, "PLEASE SELECT POSTING MONTH !");
				return;
			}
			Integer year = Integer.valueOf(yearLabelText.getText());
			String month = monthLabelText.getText();
			feesDetails.setYear(year);
			feesDetails.setMonth(month);
			feesDetails.setFeesPaid(feesPaidText.getText());
			feesDetails.setFeesInsertionDate(convertToSqlDate(postingDate));
			feesDetails.setDiscount(discountText.getText().isEmpty() ? 0 : Integer.valueOf(discountText.getText()));

			/** Validate */
			FeesValidator validator = new FeesValidator();
			HashMap<String, String> validatorHashMap = validator.validate(feesDetails);
			if (validatorHashMap.size() == 0) {
				int successValue = feesServiceImpl.saveFeesDetails(user, feesDetails);
				feesServiceImpl.updateSkippedMonthFeesToZero(user.getId(), year, month);
				if (successValue == 1) {
					JOptionPane.showMessageDialog(null, "SAVED THE FEES FOR MONTH :" + monthLabelText.getText());
					searchText.requestFocusInWindow();
					searchText.setText(EMPTY_STRING);
					return;
				} else {
					JOptionPane.showMessageDialog(null, "PROBLEM SAVING MONTH ENTRY");
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, validatorHashMap.toString());
				return;
			}

		}
	}

	private class LeftArrowButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!monthLabelText.getText().equals(EMPTY_STRING) && !yearLabelText.getText().equals(EMPTY_STRING)) {
				String month = monthLabelText.getText();
				String year = yearLabelText.getText();
				Date previousMonthDate = getMonthYearValue(month, year, -1);
				String previousMonthString = new SimpleDateFormat("MM").format(previousMonthDate);
				String userConnectionMonth = new SimpleDateFormat("MM").format(user.getDoc());
				String userConnectionYear = new SimpleDateFormat(YEAR_PATTERN).format(user.getDoc());
				if (previousMonthDate.before(user.getDoc())) {
					if (!(previousMonthString.equalsIgnoreCase(userConnectionMonth) && getYear(previousMonthDate).equals(Integer.valueOf(userConnectionYear)))) {
						JOptionPane.showMessageDialog(null, "NO POSTING DATA AVAILABLE !");
						return;
					}

				}
				if (getMonth(today).equals(monthLabelText.getText()) && getYearString(today).equals(yearLabelText.getText())) {
					postingDatePicker.setDate(today);
				} else if (previousMonthString.equalsIgnoreCase(userConnectionMonth) && getYear(previousMonthDate).equals(Integer.valueOf(userConnectionYear))) {
					postingDatePicker.setDate(user.getDoc());
				} else {
					postingDatePicker.setDate(previousMonthDate);
				}
				fillMonthYearValues(previousMonthDate);
				/** FOR ACTIVE USERS */
				if (userServiceImpl.isUserActive(user.getId())) {
					updatePreviousMonthValues(month, year, user.getId(), previousMonthDate);
				}
				/**
				 * ----------------------------------------------- FOR INACTIVE
				 * USERS SCREEN HAS ALREADY LOADED, NO need to re calculate
				 * again, for each month !
				 * -----------------------------------------------
				 **/

			}
		}

	}

	private class RightArrowButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!monthLabelText.getText().equals(EMPTY_STRING) && !yearLabelText.getText().equals(EMPTY_STRING)) {
				String month = monthLabelText.getText();
				String year = yearLabelText.getText();
				Date newMonthDate = getMonthYearValue(month, year, 1);
				fillMonthYearValues(newMonthDate);
				/** FOR ACTIVE USERS */
				if (userServiceImpl.isUserActive(user.getId())) {
					updateNextMonthValues(month, year, user.getId(), newMonthDate);
				}
				/**
				 * ----------------------------------------------- FOR INACTIVE
				 * USERS SCREEN HAS ALREADY LOADED, NO need to re calculate
				 * again, for each month !
				 * -----------------------------------------------
				 **/

			}
		}

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("MONTHLY-PAID-FRAME")).setVisible(false);
		}

	}

	private class MonthlyReportButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (user != null && user.getId() != null) {
				monthlyPaidFormFrame.setVisible(false);
				FeesPrintTable feesPrintTable = new FeesPrintTable(user.getId());
				feesPrintTable.showInTabularForm();
			}

		}

	}

	private void updatePreviousMonthValues(String month, String year, Integer id, Date previousMonthDate) {
		LOG.info("updatePreviousMonthValues ENTRY");
		AllFeesDetails allFeesDetails = feesServiceImpl.readProvidedMonthFeesDetails(id, getMonth(previousMonthDate), getYear(previousMonthDate));
		if (allFeesDetails != null) {
			fillTextBoxesWithFeesDetails(allFeesDetails);
		} else {
			String backDues = feesServiceImpl.getBackDues(id);
			int skippedMonthValue = feesServiceImpl.getSkippedMonthBackDues(id, getYear(previousMonthDate), getMonth(previousMonthDate));
			int totalRevenue = skippedMonthValue + Integer.valueOf(backDues);
			String totalBackDuesText = String.valueOf(totalRevenue);
		
			Integer feeThisMonth = getFeesDuringYearMonth(id, getYear(previousMonthDate), getMonth(previousMonthDate));				

			backDuesText.setText(String.valueOf(totalRevenue -feeThisMonth));
			feesPayableText.setText(totalBackDuesText);
			discountText.setText(EMPTY_STRING);
			feesPaidText.setText(EMPTY_STRING);
			LOG.info("previous month" + previousMonthDate);
		}
		LOG.info("updatePreviousMonthValues EXIT");

	}

	private void fillMonthYearValues(Date newMonthDate) {
		monthLabelText.setText(getMonth(newMonthDate));
		yearLabelText.setText(String.valueOf(getYear(newMonthDate)));
	}

	private void updateNextMonthValues(String month, String year, Integer id, Date nextMonth) {
		LOG.info("updateNextMonthValues ENTRY");

		/**
		 * LOGIC : If user paid fees then get its details from DB else calculate
		 */
		AllFeesDetails allFeesDetails = feesServiceImpl.readProvidedMonthFeesDetails(id, getMonth(nextMonth), getYear(nextMonth));
		if (allFeesDetails != null) {
			fillTextBoxesWithFeesDetails(allFeesDetails);
		} else {
			String backDues = feesServiceImpl.getBackDues(id);
			int skippedMonthValue = feesServiceImpl.getSkippedMonthBackDues(id, getYear(nextMonth), getMonth(nextMonth));
			int totalRevenue = skippedMonthValue + Integer.valueOf(backDues);
			String totalRevenueText = String.valueOf(totalRevenue);
			Integer feeThisMonth = getFeesDuringYearMonth(id, getYear(nextMonth), getMonth(nextMonth));				
			backDuesText.setText(String.valueOf(totalRevenue -feeThisMonth));
			discountText.setText(EMPTY_STRING);
			feesPayableText.setText(totalRevenueText);
			feesPaidText.setText(EMPTY_STRING);
		}

		postingDatePicker.setDate(nextMonth);
		LOG.info("updateNextMonthValues EXIT");
	}

	private void fillTextBoxesWithFeesDetails(AllFeesDetails allFeesDetails) {
		LOG.info("fillTextBoxesWithFeesDetails ENTRY");
		backDuesText.setText(zeroFees);
		discountText.setText(String.valueOf(allFeesDetails.getDiscount()));
		feesPayableText.setText(zeroFees);
		feesPaidText.setText(String.valueOf(allFeesDetails.getFeesPaid()));
		LOG.info("fillTextBoxesWithFeesDetails EXIT");
	}

	private void fireQuery(Integer id) {
		LOG.info("fireQuery ENTRY");
		List<User> userDetails = userServiceImpl.readUsersBasedOnCondition("where id=" + id);

		if (userDetails.isEmpty()) {
			JOptionPane.showMessageDialog(null, "USER DOES NOT EXIST OR HAS BEEN DELETED!");
		} else {
			user = userDetails.get(0);
			updateValuesInTextBoxes(user);
		}
		LOG.info("fireQuery EXIT");
	}

	private void updateValuesInTextBoxes(User user) {
		LOG.info("updateValuesInTextBoxes ENTRY");
		String thisMonth = getMonth(today);
		Integer thisYear = getYear(today);
		this.id = user.getId();
		slNOText.setText(String.valueOf(user.getId()));
		customerNameText.setText(user.getCustomerName());
		qrNoText.setText(String.valueOf(user.getQrNo()));
		if (user.getSector() != null && user.getSector().contains(HYPHEN)) {
			String[] sectorArray = user.getSector().split(HYPHEN);
			if (sectorArray.length == 2) {
				sectorText.setText(user.getSector().split(HYPHEN)[0]);
				sectorText2.setText(user.getSector().split(HYPHEN)[1]);
			} else if (sectorArray.length == 1) {
				sectorText.setText(user.getSector().split(HYPHEN)[0]);
				sectorText2.setText(EMPTY_STRING);
			}

		} else {
			sectorText.setText(user.getSector());
			sectorText2.setText(EMPTY_STRING);
		}

		monthLabelText.setText(thisMonth);
		yearLabelText.setText(String.valueOf(thisYear));
		streetText.setText(user.getStreet());
		if (userServiceImpl.isUserActive(user.getId())) {
			inactiveUserLabel.setVisible(false);
			AllFeesDetails allFeesDetails = feesServiceImpl.readProvidedMonthFeesDetails(getId(), thisMonth, thisYear);
			if (allFeesDetails != null) {
				backDuesText.setText(zeroFees);
				discountText.setText(String.valueOf(allFeesDetails.getDiscount()));
				feesPayableText.setText(zeroFees);
				feesPaidText.setText(String.valueOf(allFeesDetails.getFeesPaid()));
			} else {
				String backDues = feesServiceImpl.getBackDues(getId());
				int initialBackDuesAmount = Integer.valueOf(backDues);
				int totalAmount = feesServiceImpl.getSkippedMonthBackDues(getId(), thisYear, thisMonth) + initialBackDuesAmount;
				String totalAmountText = String.valueOf(totalAmount);
				Integer feeThisMonth = getFeesDuringYearMonth(getId(), thisYear, thisMonth);				
				backDuesText.setText(String.valueOf(totalAmount -feeThisMonth));
				feesPayableText.setText(totalAmountText);
			}
		} else {
			/** Disconnected users logic for calculating fees */
			String value = feesServiceImpl.getFeesForDisconnectedUser(user.getId());
			backDuesText.setText(zeroFees);
			discountText.setText(zeroFees);
			feesPayableText.setText(value);
			feesPaidText.setText(zeroFees);
			inactiveUserLabel.setVisible(true);

		}

		cafNumberText.setText(user.getCafNumber());
		setTopBoxNumberText.setText(user.getSetTopBoxNumber());
		mobNumberText.setText(user.getMobileNumber());
		picker.setDate(user.getDoc());
		postingDatePicker.setDate(today);
		String feeCode = findFeeCode(user.getFeesHistory().getFees());
		feeCodeText.setText(feeCode);
		Calendar calendar = postingDatePicker.getMonthView().getCalendar();
		calendar.setTime(new Date(user.getDoc().getTime()));
		postingDatePicker.getMonthView().setLowerBound(calendar.getTime());

		LOG.info("updateValuesInTextBoxes EXIT");
	}



	private Integer getFeesDuringYearMonth(Integer id, Integer year, String month) {

		FeesHistory feesHistory = userServiceImpl.findFeesDuringYearMonth(id, year, month);
		Integer fee = (feesHistory == null ? 0 : feesHistory.getFees());
		return fee;
	}
}
