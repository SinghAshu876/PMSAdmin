package com.pms.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.apache.log4j.Logger;

import com.pms.custom.components.ColoredJPanel;
import com.pms.custom.components.PMSJTextField;
import com.pms.document.filters.AlphaNumericTextFieldDocumentFilter;
import com.pms.document.filters.NumberTextFieldDocumentFilter;
import com.pms.document.filters.UppercaseDocumentFilter;
import com.pms.table.SearchResultsTable;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SearchForm implements ApplicationConstants {

	private  Logger LOG = Logger.getLogger(getClass());
	
	public static JFrame searchFormFrame = null;
	private JTextField customerNameText = new PMSJTextField(20);;
	private JTextField qrNoText = new PMSJTextField(20);;
	private JTextField mobileNumberText = new PMSJTextField(20);;
	private JTextField sectorText = new PMSJTextField(20);
	private JTextField idText = new PMSJTextField(20);
	private JTextField setTopBoxText = new PMSJTextField(20);;
	private int xCordinateOfLabel = 40;
	private int xCordinateOfTextBox = 250;
	private int componentWidth = 200;
	private List<JTextField> JTextFieldList = new ArrayList<JTextField>();
	private Integer yValue = 50;
	private JButton searchButton = null;
	private SearchResultsTable searchResultsTable = new SearchResultsTable();

	private Integer getIncrementedValue(int height, boolean increase) {
		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	public void init(JFrame parentFrame) {
		LOG.info("init ENTRY");
		parentFrame.setVisible(false);
		searchFormFrame = new JFrame("SEARCH FORM");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		searchFormFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		searchFormFrame.setSize(screenSize);
		searchFormFrame.setVisible(true);
		searchFormFrame.setResizable(false);
		searchFormFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new ColoredJPanel();
		searchFormFrame.add(panel);
		placeComponents(panel);
		searchFormFrame.getRootPane().setDefaultButton(searchButton);
		Container.frameContainer.put("SEARCH-ENTRY-FRAME", searchFormFrame);
		LOG.info("init EXIT");
	}

	private void placeComponents(JPanel panel) {

		// addHeaderPanel(panel);
		LOG.info("placeComponents ENTRY");
		JLabel customerName = new JLabel("SEARCH BY NAME:");
		customerName.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(customerName);

		customerNameText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) customerNameText.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		JTextFieldList.add(customerNameText);
		panel.add(customerNameText);

		JLabel qrnoLabel = new JLabel("SEARCH BY QR NO:");
		qrnoLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(qrnoLabel);

		qrNoText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) qrNoText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		JTextFieldList.add(qrNoText);
		panel.add(qrNoText);

		JLabel mobileNumberLabel = new JLabel("SEARCH BY MOBILE NO:");
		mobileNumberLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(mobileNumberLabel);

		mobileNumberText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) mobileNumberText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(10));
		JTextFieldList.add(mobileNumberText);
		panel.add(mobileNumberText);

		JLabel sector = new JLabel("SEARCH BY SECTOR:");
		sector.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(sector);

		sectorText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) sectorText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(1));
		JTextFieldList.add(sectorText);
		panel.add(sectorText);

		JLabel setTopBoxLabel = new JLabel("SEARCH BY SET TOP BOX NO:");
		setTopBoxLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(setTopBoxLabel);

		setTopBoxText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) setTopBoxText.getDocument()).setDocumentFilter(new AlphaNumericTextFieldDocumentFilter(20));
		JTextFieldList.add(setTopBoxText);
		panel.add(setTopBoxText);

		JLabel idLabel = new JLabel("SEARCH BY ID:");
		idLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(idLabel);

		idText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) idText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(5));
		JTextFieldList.add(idText);
		panel.add(idText);

		searchButton = new JButton("SEARCH");
		searchButton.setBounds(90, getIncrementedValue(yValue, true), 100, COMPONENT_HEIGHT);
		searchButton.addActionListener(new SearchButtonHandler());
		panel.add(searchButton);

		JButton clearButton = new JButton("CLEAR");
		clearButton.setBounds(200, getIncrementedValue(yValue, false), 100, COMPONENT_HEIGHT);
		clearButton.addActionListener(new ClearButtonHandler());
		panel.add(clearButton);

		JButton backButton = new JButton("BACK");
		backButton.setBounds(310, getIncrementedValue(yValue, false), 100, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);
		
		LOG.info("placeComponents EXIT");

	}

	private class SearchButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			searchFormFrame.setVisible(false);
			LinkedHashMap<String, String> userConditions = new LinkedHashMap<String, String>();
			if (!customerNameText.getText().isEmpty()) {
				userConditions.put("customername", customerNameText.getText());
			}
			if (!sectorText.getText().isEmpty()) {
				userConditions.put("sector", sectorText.getText());
			}
			if (!qrNoText.getText().isEmpty()) {
				userConditions.put("QRNO", qrNoText.getText());
			}
			if (!mobileNumberText.getText().isEmpty()) {
				userConditions.put("MOBNUMBER", mobileNumberText.getText());
			}
			if (!idText.getText().isEmpty()) {
				userConditions.put("ID", idText.getText());
			}
			if (!setTopBoxText.getText().isEmpty()) {
				userConditions.put("SETTOPBOX", setTopBoxText.getText());
			}
			searchResultsTable.getUsersBasedOnCondition(userConditions);
		}

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("SEARCH-ENTRY-FRAME")).setVisible(false);
		}

	}

	private class ClearButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (JTextField tf : JTextFieldList) {
				tf.setText("");
			}
		}
	}
}
