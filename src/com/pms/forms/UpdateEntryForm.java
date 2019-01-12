package com.pms.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.pms.custom.components.ColoredJPanel;
import com.pms.custom.components.PMSJTextField;
import com.pms.custom.components.PMSJXDatePicker;
import com.pms.document.filters.NumberTextFieldDocumentFilter;
import com.pms.document.filters.UpperCaseWithLimitDocumentFilter;
import com.pms.document.filters.UppercaseDocumentFilter;
import com.pms.entity.DisconnectReconnectDetails;
import com.pms.entity.FeesHistory;
import com.pms.entity.User;
import com.pms.enums.util.ActiveStatus;
import com.pms.enums.util.StaticCodes;
import com.pms.service.impl.FeesServiceImpl;
import com.pms.service.impl.UserServiceImpl;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.util.PMSUtility;
import com.pms.validator.UserValidator;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class UpdateEntryForm implements ApplicationConstants {

	private  Logger LOG = Logger.getLogger(getClass());
	
	private JFrame updateEntryForm = null;
	private JTextField slNOText = null;
	private JTextField customerNameText = null;
	private JTextField qrNoText = null;
	private JTextField streetText = null;
	private JTextField sectorText = null;
	private JTextField sectorText2 = null;
	private JTextField connectionChargeText = null;
	private JTextField backDuesText = null;
	private JTextField feeText = null;
	private JTextField mobNumberText = null;
	private JTextField setTopBoxNumberText = null;
	private JTextField cafNumberText = null;
	private JButton disconnectButton = null;
	private JButton updateuserButton = null;
	private JXDatePicker picker = null;
	private JLabel disconnectLabel = null;
	private JLabel reconnectLabel = null;
	private JTextField feeCodeText = null;
	private JXDatePicker disconnectDatePicker = null;
	private JXDatePicker reconnectDatePicker = null;
	private JButton reconnectButton = null;
	private int xCordinateOfLabel = 20;
	private int xCordinateOfTextBox = 200;
	private int componentWidth = 190;
	private int rhsComponentWidth = 170;
	private String temp = "";
	private int id;
	private JPanel panel = new ColoredJPanel();
	private User user;
	private Integer yValue = 50;
	private Integer yValue4RHS = 50;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private FeesServiceImpl feesServiceImpl = new FeesServiceImpl();
	private String parentFrameName = null;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UpdateEntryForm(Integer id) {
		this.id = id;
	}

	public void init(String frameName) {
		LOG.info("init ENTRY");
		parentFrameName = frameName;
		JFrame parentFrame = (JFrame) Container.frameContainer.get(frameName);
		parentFrame.setVisible(false);
		List<User> userDetails = userServiceImpl.readUsersBasedOnCondition("where id=" + getId());
		if (userDetails.isEmpty()) {
			JOptionPane.showMessageDialog(null, "USER DOES NOT EXIST OR HAS BEEN DELETED!");
			parentFrame.setVisible(true);
		} else {
			user = userDetails.get(0);
			updateEntryForm = new JFrame("UPDATE ENTRY");
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			updateEntryForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
			updateEntryForm.setSize(screenSize);
			updateEntryForm.setVisible(true);
			updateEntryForm.setResizable(false);
			updateEntryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			updateEntryForm.add(panel);
			placeComponents(panel);
			Container.frameContainer.put("UPDATE-ENTRY-FRAME", updateEntryForm);
		}
		LOG.info("init EXIT");
	}

	private void placeComponents(JPanel panel) {
		// addHeaderPanel(panel);

		LOG.info("placeComponents ENTRY");
		JLabel slNO = new JLabel("ID NO:");
		slNO.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(slNO);

		slNOText = new PMSJTextField(20);
		slNOText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		slNOText.setText(String.valueOf(user.getId()));
		slNOText.setEnabled(false);
		slNOText.setEditable(false);
		slNOText.setDisabledTextColor(Color.BLACK);
		panel.add(slNOText);

		JLabel customerName = new JLabel("CUSTOMER NAME:");
		customerName.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(customerName);

		customerNameText = new PMSJTextField(20);
		customerNameText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		customerNameText.setText(user.getCustomerName());
		((AbstractDocument) customerNameText.getDocument()).setDocumentFilter(new UpperCaseWithLimitDocumentFilter(20));
		panel.add(customerNameText);

		JLabel qrnoLabel = new JLabel("QR NO:");
		qrnoLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(qrnoLabel);

		qrNoText = new PMSJTextField(20);
		qrNoText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		qrNoText.setText(String.valueOf(user.getQrNo()));
		((AbstractDocument) qrNoText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(qrNoText);

		JLabel street = new JLabel("STREET:");
		street.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(street);

		streetText = new PMSJTextField(20);
		streetText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		streetText.setText(user.getStreet());
		panel.add(streetText);

		JLabel sector = new JLabel("SECTOR:");
		sector.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(sector);

		sectorText = new PMSJTextField(20);
		sectorText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth / 3, COMPONENT_HEIGHT);
		((AbstractDocument) sectorText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(1));
		sectorText2 = new PMSJTextField(20);
		sectorText2.setBounds(280, getIncrementedValue(yValue, false), componentWidth / 5, COMPONENT_HEIGHT);
		((AbstractDocument) sectorText2.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		if (user.getSector().contains(HYPHEN)) {
			String [] sectorArray = user.getSector().split(HYPHEN);
			if(sectorArray.length==2){
				sectorText.setText(user.getSector().split(HYPHEN)[0]);
				sectorText2.setText(user.getSector().split(HYPHEN)[1]);
			}
			else if (sectorArray.length==1){
				sectorText.setText(user.getSector().split(HYPHEN)[0]);
				sectorText2.setText(EMPTY_STRING);
			}
			
		} else {
			sectorText.setText(user.getSector());
			sectorText2.setText(EMPTY_STRING);
		}

		panel.add(sectorText);
		panel.add(sectorText2);

		JLabel doc = new JLabel("DATE OF CONNECTION:");
		doc.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(doc);

		picker = new PMSJXDatePicker();
		picker.setEditable(false);
		picker.setEnabled(false);
		picker.setDate(user.getDoc());
		picker.setFormats(new SimpleDateFormat(DATE_PICKER_PATTERN));
		picker.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(picker);

		JLabel connectionCharge = new JLabel("CONNECTION CHARGE:");
		connectionCharge.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(connectionCharge);

		connectionChargeText = new PMSJTextField(20);
		connectionChargeText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		connectionChargeText.setText(user.getConnectionCharge());
		((AbstractDocument) connectionChargeText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(connectionChargeText);

		JLabel backDues = new JLabel("BACK DUES:");
		backDues.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(backDues);

		backDuesText = new PMSJTextField(20);
		backDuesText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		backDuesText.setText(feesServiceImpl.getBackDues(id));
		((AbstractDocument) backDuesText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(backDuesText);

		JLabel fee = new JLabel("FEE:");
		fee.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(fee);

		feeText = new PMSJTextField(20);
		feeText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
		feeText.setText(String.valueOf(user.getFeesHistory().getFees()));
		((AbstractDocument) feeText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		feeText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				LinkedHashMap<Character, String> codes = StaticCodes.codeMap;
				if (codes.containsKey(arg0.getKeyChar()) && temp.length() < 4) {
					temp = temp + codes.get(arg0.getKeyChar());
					feeCodeText.setText(temp);

				}
				if (arg0.getKeyCode() == 8 && !temp.isEmpty()) {
					String deletedTemp = temp.substring(0, temp.length() - 1);
					temp = deletedTemp;
					feeCodeText.setText(temp);

				}

			}
		});
		panel.add(feeText);

		JLabel feeCodeLabel = new JLabel("FEE CODE:");
		feeCodeLabel.setBounds(260, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		panel.add(feeCodeLabel);

		feeCodeText = new PMSJTextField(20);
		feeCodeText.setEditable(false);
		feeCodeText.setBounds(340, getIncrementedValue(yValue, false), 50, COMPONENT_HEIGHT);
		String feeCode = PMSUtility.findFeeCode(user.getFeesHistory().getFees());
		feeCodeText.setText(feeCode);
		temp = feeCode;
		panel.add(feeCodeText);
		
		JButton configureFeeButton = new JButton("CONFIGURE FEE");
		configureFeeButton.setBounds(400, getIncrementedValue(yValue, false), 150, COMPONENT_HEIGHT);
		configureFeeButton.addActionListener(new ConfigureFeeButtonHandler());
		panel.add(configureFeeButton);

		JLabel mobileNumber = new JLabel("MOBILE NUMBER:");
		mobileNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(mobileNumber);

		mobNumberText = new PMSJTextField(20);
		mobNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		mobNumberText.setText(user.getMobileNumber());
		((AbstractDocument) mobNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(10));
		panel.add(mobNumberText);

		JLabel setTopBoxNumber = new JLabel("SET-TOP BOX NUMBER:");
		setTopBoxNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(setTopBoxNumber);

		setTopBoxNumberText = new PMSJTextField(20);
		setTopBoxNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		setTopBoxNumberText.setText(user.getSetTopBoxNumber());
		((AbstractDocument) setTopBoxNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(20));
		panel.add(setTopBoxNumberText);

		JLabel casNumber = new JLabel("CAF NUMBER:");
		casNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(casNumber);

		cafNumberText = new PMSJTextField(20);
		cafNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		cafNumberText.setText(user.getCafNumber());
		((AbstractDocument) cafNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(12));
		panel.add(cafNumberText);

		updateuserButton = new JButton("UPDATE USER");
		updateuserButton.setBounds(200, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		updateuserButton.addActionListener(new UpdateButtonHandler());
		panel.add(updateuserButton);

		JButton deleteButton = new JButton("DELETE");
		deleteButton.setBounds(400, getIncrementedValue(yValue, false), 100, COMPONENT_HEIGHT);
		deleteButton.addActionListener(new DeleteButtonHandler());
		panel.add(deleteButton);

		JButton backButton = new JButton("BACK");
		backButton.setBounds(520, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);

		if (user.getActive().equalsIgnoreCase(ActiveStatus.Y.name())) {
			createDisconnectComponents("DISCONNECT DATE :", "DISCONNECT", null, true);
			createReconnectComponents("RECONNECT DATE :", "RECONNECT", false);
		} else {
			createDisconnectComponents("DISCONNECT DATE :", "DISCONNECT", userServiceImpl.getDisconnectedDate(id), false);
			createReconnectComponents("RECONNECT DATE :", "RECONNECT", true);
			disableButtons();
		}
		LOG.info("placeComponents EXIT");
	}

	private void createDisconnectComponents(String label, String buttonLabel, Date dateOfDisconnection, boolean enabled) {
		disconnectLabel = new JLabel(label);
		disconnectLabel.setBounds(400, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		panel.add(disconnectLabel);

		disconnectDatePicker = new PMSJXDatePicker();
		disconnectDatePicker.setDate(dateOfDisconnection);
		disconnectDatePicker.setFormats(new SimpleDateFormat(DATE_PICKER_PATTERN));
		disconnectDatePicker.setEditable(enabled);
		disconnectDatePicker.setEnabled(enabled);
		disconnectDatePicker.setBounds(570, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		panel.add(disconnectDatePicker);

		disconnectButton = new JButton(buttonLabel);
		disconnectButton.setBounds(810, getIncrementedValue(yValue4RHS, false), 140, COMPONENT_HEIGHT);
		disconnectButton.setEnabled(enabled);
		disconnectButton.addActionListener(new DisconnectButtonHandler());
		panel.add(disconnectButton);
	}

	private void createReconnectComponents(String label, String buttonLabel, boolean visible) {
		reconnectLabel = new JLabel(label);
		reconnectLabel.setBounds(400, getIncrementedValue4RHS(yValue4RHS, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(reconnectLabel);

		reconnectDatePicker = new PMSJXDatePicker();
		reconnectDatePicker.setFormats(new SimpleDateFormat(DATE_PICKER_PATTERN));
		reconnectDatePicker.setBounds(570, getIncrementedValue4RHS(yValue4RHS, false), rhsComponentWidth, COMPONENT_HEIGHT);
		reconnectDatePicker.setEditable(visible);
		reconnectDatePicker.setEnabled(visible);
		panel.add(reconnectDatePicker);

		reconnectButton = new JButton(buttonLabel);
		reconnectButton.setBounds(810, getIncrementedValue(yValue4RHS, false), 140, COMPONENT_HEIGHT);
		reconnectButton.setEnabled(visible);
		reconnectButton.addActionListener(new ReconnectButtonHandler());
		panel.add(reconnectButton);
	}

	private void disableButtons() {
		updateuserButton.setEnabled(false);
	}

	private void disableReconnectButtons() {
		reconnectButton.setEnabled(false);
		reconnectDatePicker.setEditable(false);
		reconnectDatePicker.setEnabled(false);
		disconnectButton.setEnabled(true);
		disconnectDatePicker.setEditable(true);
		disconnectDatePicker.setEnabled(true);
	}

	private void enableReconnectButtons() {
		reconnectButton.setEnabled(true);
		reconnectDatePicker.setEditable(true);
		reconnectDatePicker.setEnabled(true);
		disconnectButton.setEnabled(false);
		disconnectDatePicker.setEditable(false);
		disconnectDatePicker.setEnabled(false);

	}

	private void enableButtons() {
		updateuserButton.setEnabled(true);
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (parentFrameName.equals("MAIN-MENU-FRAME")) {
				((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
				((JFrame) Container.frameContainer.get("UPDATE-ENTRY-FRAME")).setVisible(false);
			} else {
				((JFrame) Container.frameContainer.get("SEARCH-RESULTS-FRAME")).setVisible(true);
				((JFrame) Container.frameContainer.get("UPDATE-ENTRY-FRAME")).setVisible(false);
			}

		}

	}

	private class DeleteButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int response = JOptionPane.showConfirmDialog(null, "DO YOU WANT TO DELETE?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				return;
			} else if (response == JOptionPane.YES_OPTION) {
				int successValue = new UserServiceImpl().deleteUser(getId());
				if (successValue == 1) {
					JOptionPane.showMessageDialog(null, "SUCCESSFULLY DELETED THE USER !");
					((JFrame) Container.frameContainer.get("UPDATE-ENTRY-FRAME")).setVisible(false);
					((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
					return;
				} else {
					JOptionPane.showMessageDialog(null, "PROBLEM DELETEING THE USER !");
					return;
				}
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}

		}

	}

	/*
	 * private void updateBackDues(Integer id, FeesDetails feesDetails) {
	 * UserDAO userDao = new UserDAO(); User user = userDao.readUserById(id); if
	 * (user != null && user.getFee() != null && feesDetails != null &&
	 * feesDetails.getFees() != null) { Integer feesEntered =
	 * Integer.valueOf(feesDetails.getFees()); Integer feesDefined =
	 * Integer.valueOf(user.getFee()); Integer newBackDues = 0; Integer
	 * existingBackDues = Integer.valueOf(user.getBackDues()); if (feesEntered
	 * <= feesDefined) { System.out.println("feesEntered <= feesDefined");
	 * newBackDues = feesDefined - feesEntered;
	 * user.setBackDues(String.valueOf(existingBackDues + newBackDues));
	 * userDao.update(user); } else if (feesEntered > feesDefined) {
	 * System.out.println("feesEntered > feesDefined"); newBackDues =
	 * feesEntered - feesDefined; if (existingBackDues == 0) {
	 * user.setBackDues(String.valueOf(0 - newBackDues)); } else {
	 * user.setBackDues(String.valueOf(newBackDues - existingBackDues)); }
	 * 
	 * userDao.update(user); } }
	 * 
	 * }
	 */
	
	private class ConfigureFeeButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}

	private class DisconnectButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (disconnectDatePicker == null || disconnectDatePicker.getDate() == null) {
				JOptionPane.showMessageDialog(null, "PLEASE SELECT A DATE !");
				return;
			}
			java.util.Date disconnectReconnectDate = disconnectDatePicker.getDate();
			if (disconnectReconnectDate.before(user.getDoc())) {
				JOptionPane.showMessageDialog(null, "DISCONNECT DATE SHOULD BE AFTER DATE OF CONNECTION !");
				return;
			}
			List<DisconnectReconnectDetails> discoRecoHistory = userServiceImpl.getDiscoRecoHistory(user.getId());
			if(!discoRecoHistory.isEmpty() && discoRecoHistory.get(0).getDateOfDisconnection()!=null && discoRecoHistory.get(0).getDateOfReconnection()!=null){
				JOptionPane.showMessageDialog(null, "USER CAN BE DISCONNECTED-RECONNECTED ONLY ONCE.KINDLY DELETE THIS USER AND CREATE A NEW ONE ");
				return;
			}
			
			int successValue = new UserServiceImpl().disconnectReconnectUser(PMSUtility.convertToSqlDate(disconnectReconnectDate), user, ActiveStatus.N.name());
			if (successValue == 1) {
				JOptionPane.showMessageDialog(null, "DISCONNECTED THE CONNECTION ON :" + disconnectReconnectDate);
				disableButtons();
				enableReconnectButtons();
				user.setActive(ActiveStatus.N.name());
				return;
			} else {
				JOptionPane.showMessageDialog(null, "PROBLEM UPDATING THE USER");
				return;
			}
		}

	}

	private class ReconnectButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			java.util.Date reconnectDate = reconnectDatePicker.getDate();
			java.util.Date today = new java.util.Date();
			if (reconnectDatePicker == null || reconnectDatePicker.getDate() == null) {
				LOG.info("No Date Selected!, Updating today`s Date");
				reconnectDate = today;
			}
			
			List<DisconnectReconnectDetails> discoRecoHistory = userServiceImpl.getDiscoRecoHistory(user.getId());
			DisconnectReconnectDetails disconnectReconnectDetails = discoRecoHistory.get(0);
			Date dodc = disconnectReconnectDetails.getDateOfDisconnection();
			/**LOGIC : RECONNECTION SHOULD BE ALWAYS GREATER THAN OR EQUAL TO DISCONNECTED DATE*/
			String message  = null;
			if(!reconnectDate.before(dodc)){
				message = "RECONNECTED THE CONNECTION ON";
				reconnectAndEnable(reconnectDate,message);
				return;				
			}
			else if(reconnectDate.before(dodc)){
				message = "SINCE RECONECTION DATE BEFORE DODC ,SO RECONNECTED THE CONNECTION ON ";
				reconnectAndEnable(dodc,message);
				LOG.info("RECONNECTION SHOULD BE ALWAYS GREATER THAN OR EQUAL TO DISCONNECTED DATE");
			    return;
			}

		}

		private void reconnectAndEnable(java.util.Date reconnectDate, String message) {
			int successValue = userServiceImpl.disconnectReconnectUser(PMSUtility.convertToSqlDate(reconnectDate), user, ActiveStatus.Y.name());
			if (successValue == 1) {
				JOptionPane.showMessageDialog(null, message +" :" + reconnectDate);
				enableButtons();
				disableReconnectButtons();
				user.setActive(ActiveStatus.Y.name());
				return;
			}
			else {
				JOptionPane.showMessageDialog(null, "PROBLEM RECONNECTING  THE USER");
				return;
			}
			
		}

	}

	private class UpdateButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			user.setConnectionCharge(connectionChargeText.getText());
			user.setQrNo(Integer.valueOf(qrNoText.getText()));
			user.setConnectionCharge(connectionChargeText.getText());
			user.setCustomerName(customerNameText.getText());
            user.setFeesHistory(setFeeHistoryParams(feeText.getText(), id));
			user.setSector(sectorText.getText() + (null == sectorText2.getText() ? "" : "-" + sectorText2.getText()));
			user.setStreet(streetText.getText());
			user.setMobileNumber(mobNumberText.getText());
			user.setSetTopBoxNumber(setTopBoxNumberText.getText());
			user.setCafNumber(cafNumberText.getText());
			user.setBackDues(backDuesText.getText());
			/** Validate */
			UserValidator validator = new UserValidator();
			HashMap<String, String> validatorHashMap = validator.validate(user);
			if (validatorHashMap.size() == 0) {
				int successValue = userServiceImpl.update(user);
				if (successValue == 1) {
					int success = userServiceImpl.updateFees(user);
					if (success == 1) {
						JOptionPane.showMessageDialog(null, "UPDATED THE USER DETAILS !");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "PROBLEM UPDATING USER ENTRY !");
					return;
				}
			} else {

				JOptionPane.showMessageDialog(null, validatorHashMap.toString());
				return;
			}
			

		}
		
		private FeesHistory setFeeHistoryParams(String text,  Integer id) {
			java.util.Date today = new java.util.Date();
			FeesHistory feesHistory = new FeesHistory();
			feesHistory.setId(id);
			feesHistory.setFromDate(null);
			feesHistory.setToDate(today);
			feesHistory.setFees(Integer.parseInt(text));
			return feesHistory;
		}
	}

}
