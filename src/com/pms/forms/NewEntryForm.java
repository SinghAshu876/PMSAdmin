package com.pms.forms;

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
import com.pms.custom.components.DualListBoxJPanel;
import com.pms.custom.components.PMSJTextField;
import com.pms.custom.components.PMSJXDatePicker;
import com.pms.document.filters.NumberTextFieldDocumentFilter;
import com.pms.document.filters.UpperCaseWithLimitDocumentFilter;
import com.pms.document.filters.UppercaseDocumentFilter;
import com.pms.entity.ChannelDetails;
import com.pms.entity.FeesHistory;
import com.pms.entity.User;
import com.pms.enums.util.StaticCodes;
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
public class NewEntryForm implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private JFrame newEntryForm;
	private JTextField slNOText;
	private JTextField customerNameText;
	private JTextField qrNoText;
	private JTextField streetText;
	private JTextField sectorText;
	private JTextField sectorText2;
	private JTextField connectionChargeText;
	private JTextField backDuesText;
	private JTextField feeText;
	private JTextField feeCodeText;
	private JTextField mobNumberText;
	private JTextField setTopBoxNumberText;
	private JTextField cafNumberText;
	private JXDatePicker picker;
	private List<JTextField> JTextFieldList = new ArrayList<JTextField>();
	private int xCordinateOfLabel = 40;
	private int xCordinateOfTextBox = 230;
	private int componentWidth = 210;
	private String temp = "";
	private JButton saveButton = null;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private Object classInstance;
	private List<ChannelDetails> selectedChannelsList;

	public void init(JFrame parentFrame) {
		LOG.info("init ENTRY");
		classInstance = this;
		parentFrame.setVisible(false);
		newEntryForm = new JFrame("NEW ENTRY");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		newEntryForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
		newEntryForm.setSize(screenSize);

		newEntryForm.setVisible(true);
		newEntryForm.setResizable(false);
		newEntryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new ColoredJPanel();
		newEntryForm.add(panel);
		placeComponents(panel);
		// newEntryForm.getRootPane().setDefaultButton(saveButton);
		Container.frameContainer.put("NEW-ENTRY-FRAME", newEntryForm);
		LOG.info("init EXIT");

	}

	private Integer yValue = 50;

	private Integer getIncrementedValue(int height, boolean increase) {
		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	private void placeComponents(JPanel panel) {
		// addHeaderPanel(panel);
		LOG.info("placeComponents ENTRY");
		JLabel slNO = new JLabel("ID NO:");
		slNO.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(slNO);

		slNOText = new PMSJTextField(20);
		slNOText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		slNOText.setText(userServiceImpl.getMaxSequence());
		slNOText.setDisabledTextColor(Color.BLACK);
		slNOText.setEnabled(false);
		slNOText.setEditable(false);
		panel.add(slNOText);

		JLabel customerName = new JLabel("CUSTOMER NAME:");
		customerName.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(customerName);

		customerNameText = new PMSJTextField(20);
		customerNameText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth,
				COMPONENT_HEIGHT);
		JTextFieldList.add(customerNameText);
		((AbstractDocument) customerNameText.getDocument()).setDocumentFilter(new UpperCaseWithLimitDocumentFilter(20));
		panel.add(customerNameText);

		JLabel qrnoLabel = new JLabel("QR NO:");
		qrnoLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(qrnoLabel);

		qrNoText = new PMSJTextField(20);
		qrNoText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		JTextFieldList.add(qrNoText);
		((AbstractDocument) qrNoText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(qrNoText);

		JLabel street = new JLabel("STREET:");
		street.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(street);

		streetText = new PMSJTextField(20);
		streetText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) streetText.getDocument()).setDocumentFilter(new UpperCaseWithLimitDocumentFilter(25));
		JTextFieldList.add(streetText);
		panel.add(streetText);

		JLabel sector = new JLabel("SECTOR:");
		sector.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(sector);

		sectorText = new PMSJTextField(20);
		sectorText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth / 5,
				COMPONENT_HEIGHT);
		((AbstractDocument) sectorText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(1));
		JTextFieldList.add(sectorText);
		panel.add(sectorText);

		sectorText2 = new PMSJTextField(20);
		sectorText2.setBounds(280, getIncrementedValue(yValue, false), componentWidth / 5, COMPONENT_HEIGHT);
		JTextFieldList.add(sectorText2);
		((AbstractDocument) sectorText2.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		panel.add(sectorText2);

		JLabel doc = new JLabel("DATE OF CONNECTION:");
		doc.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(doc);

		picker = new PMSJXDatePicker();
		picker.setDate(Calendar.getInstance().getTime());
		picker.setFormats(new SimpleDateFormat(DATE_PICKER_PATTERN));
		picker.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(picker);

		JLabel connectionCharge = new JLabel("CONNECTION CHARGE:");
		connectionCharge.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth,
				COMPONENT_HEIGHT);
		panel.add(connectionCharge);

		connectionChargeText = new PMSJTextField(20);
		connectionChargeText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth,
				COMPONENT_HEIGHT);
		((AbstractDocument) connectionChargeText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		JTextFieldList.add(connectionChargeText);
		panel.add(connectionChargeText);

		JLabel backDues = new JLabel("BACK DUES:");
		backDues.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(backDues);

		backDuesText = new PMSJTextField(20);
		backDuesText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth,
				COMPONENT_HEIGHT);
		((AbstractDocument) backDuesText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		JTextFieldList.add(backDuesText);
		panel.add(backDuesText);

		JLabel fee = new JLabel("FEE:");
		fee.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(fee);

		feeText = new PMSJTextField(20);
		feeText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
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

		JTextFieldList.add(feeText);
		((AbstractDocument) feeText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(feeText);

		JLabel feeCodeLabel = new JLabel("FEE CODE:");
		feeCodeLabel.setBounds(300, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		panel.add(feeCodeLabel);

		feeCodeText = new PMSJTextField(20);
		feeCodeText.setEditable(false);
		feeCodeText.setBounds(380, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
		JTextFieldList.add(feeCodeText);
		panel.add(feeCodeText);

		JButton configureFeeButton = new JButton("CONFIGURE FEE");
		configureFeeButton.setBounds(450, getIncrementedValue(yValue, false), 150, COMPONENT_HEIGHT);
		configureFeeButton.addActionListener(new ConfigureFeeButtonHandler());
		panel.add(configureFeeButton);

		JLabel mobileNumber = new JLabel("MOBILE NUMBER:");
		mobileNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(mobileNumber);

		mobNumberText = new PMSJTextField(20);
		mobNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth,
				COMPONENT_HEIGHT);
		((AbstractDocument) mobNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(10));
		JTextFieldList.add(mobNumberText);
		panel.add(mobNumberText);

		JLabel setTopBoxNumber = new JLabel("SET-TOP BOX NUMBER:");
		setTopBoxNumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth,
				COMPONENT_HEIGHT);
		panel.add(setTopBoxNumber);

		setTopBoxNumberText = new PMSJTextField(20);
		setTopBoxNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth,
				COMPONENT_HEIGHT);
		((AbstractDocument) setTopBoxNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(20));
		JTextFieldList.add(setTopBoxNumberText);
		panel.add(setTopBoxNumberText);

		JLabel casnumber = new JLabel("CAF NUMBER:");
		casnumber.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(casnumber);

		cafNumberText = new PMSJTextField(20);
		cafNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth,
				COMPONENT_HEIGHT);
		((AbstractDocument) cafNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(12));
		JTextFieldList.add(cafNumberText);
		panel.add(cafNumberText);

		saveButton = new JButton("SAVE");
		saveButton.setBounds(110, getIncrementedValue(yValue, true), 80, COMPONENT_HEIGHT);
		saveButton.addActionListener(new SaveButtonHandler());

		panel.add(saveButton);

		JButton clearButton = new JButton("CLEAR");
		clearButton.setBounds(200, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		clearButton.addActionListener(new ClearButtonHandler());
		panel.add(clearButton);

		JButton backButton = new JButton("BACK");
		backButton.setBounds(290, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);
		LOG.info("placeComponents EXIT");
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("NEW-ENTRY-FRAME")).setVisible(false);
		}

	}

	private class ClearButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (final JTextField tf : JTextFieldList) {
				tf.setText(EMPTY_STRING);
			}
		}

	}

	private class ConfigureFeeButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<ChannelDetails> channelDetailsList = userServiceImpl.getChannelDetails();
			new DualListBoxJPanel(channelDetailsList,null, classInstance);
		}

	}

	private class SaveButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().beep();
			User user = new User();
			user.setId(Integer.valueOf(slNOText.getText()));
			user.setConnectionCharge(connectionChargeText.getText());
			try {
				user.setQrNo(Integer.valueOf(qrNoText.getText()));
			} catch (NumberFormatException exception) {
				user.setQrNo(null);
			}

			user.setConnectionCharge(connectionChargeText.getText());
			user.setCustomerName(customerNameText.getText());
			user.setDoc(picker.getDate());
			user.setFeesHistory(
					setFeeHistoryParams(feeText.getText(), picker.getDate(), Integer.valueOf(slNOText.getText())));
			user.setSector(sectorText.getText()
					+ (null == sectorText2.getText() ? EMPTY_STRING : HYPHEN + sectorText2.getText()));
			user.setStreet(streetText.getText());
			user.setMobileNumber(mobNumberText.getText());
			user.setSetTopBoxNumber(setTopBoxNumberText.getText());
			user.setCafNumber(cafNumberText.getText());
			user.setBackDues(backDuesText.getText());
			user.setDocValidate(picker.getEditor().getText());
			user.setChannelsList(getSelectedChannels());
			/** Validate */
			UserValidator validator = new UserValidator();
			HashMap<String, String> validatorHashMap = validator.validate(user);
			if (validatorHashMap.size() == 0) {
				int successValue = userServiceImpl.save(user);
				if (successValue == 1) {
					JOptionPane.showMessageDialog(null, "SAVED THE USER ENTRY :" + customerNameText.getText());
					for (JTextField tf : JTextFieldList) {
						tf.setText(EMPTY_STRING);
					}
					slNOText.setText(userServiceImpl.getMaxSequence());
					temp = EMPTY_STRING;
				} else {
					JOptionPane.showMessageDialog(null, "PROBLEM SAVING USER ENTRY");
				}
			} else {

				JOptionPane.showMessageDialog(null, validatorHashMap.toString());

			}

		}

		private FeesHistory setFeeHistoryParams(String text, Date date, Integer id) {
			FeesHistory feesHistory = new FeesHistory();
			feesHistory.setId(id);
			feesHistory.setFromDate(PMSUtility.convertToSqlDate(date));
			feesHistory.setFees(Integer.parseInt(text));
			return feesHistory;
		}

	}

	public List<ChannelDetails> getSelectedChannels() {
		return selectedChannelsList;
	}

	public void setSelectedChannels(List<ChannelDetails> selectedChannelsList) {
		this.selectedChannelsList = selectedChannelsList;
	}

	public JTextField getFeeText() {
		return feeText;
	}

	public void setFeeText(JTextField feeText) {
		this.feeText = feeText;
	}

	public JFrame getFrame() {
		return newEntryForm;
	}

	public void setFrame(JFrame newEntryForm) {
		this.newEntryForm = newEntryForm;
	}

}
