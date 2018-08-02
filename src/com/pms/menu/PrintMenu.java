package com.pms.menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.pms.custom.components.ColoredJPanel;
import com.pms.enums.util.Month;
import com.pms.enums.util.StaticCodes;
import com.pms.table.SectorPrintTable;
import com.pms.table.SetTopBoxPrintTable;
import com.pms.table.SimplePrintTable;
import com.pms.table.StreetPrintTable;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PrintMenu implements ApplicationConstants {

	private  Logger LOG = Logger.getLogger(getClass());
	public JFrame printMenuFrame = null;
	private Integer yearComboBoxValue = PMSUtility.initialiseCurrentYear();
	private String monthComboBoxValue = null;
	private int xCordinateOfLabel = 40;
	private int componentWidth = 240;
	private Integer yValue = 50;

	private Integer getIncrementedValue(int height, boolean increase) {
		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	public void init(JFrame parentFrame) {
		LOG.info("init ENTRY");
		parentFrame.setVisible(false);
		printMenuFrame = new JFrame("PRINT MENU");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		printMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		printMenuFrame.setSize(screenSize);
		printMenuFrame.setVisible(true);
		printMenuFrame.setResizable(false);
		printMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new ColoredJPanel();
		printMenuFrame.add(panel);
		placeComponents(panel);
		Container.frameContainer.put("PRINT-MENU-FRAME", printMenuFrame);
		LOG.info("init EXIT");
	}

	private void placeComponents(JPanel panel) {
		// addHeaderPanel(panel);
		LOG.info("placeComponents ENTRY");
		JComboBox<String> feesPaidMonth = new JComboBox<String>();
		feesPaidMonth.setEditable(false);
		Month[] monthsArray = Month.values();
		for (int i = 0; i < monthsArray.length; i++) {
			feesPaidMonth.addItem(monthsArray[i].name().toString());
		}
		feesPaidMonth.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), componentWidth / 2, COMPONENT_HEIGHT);
		feesPaidMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox source = (JComboBox) e.getSource();
				monthComboBoxValue = (String) source.getSelectedItem();

			}
		});
		panel.add(feesPaidMonth);
		JComboBox<Integer> feesPaidYear = new JComboBox<Integer>();
		feesPaidYear.setEditable(false);
		Integer[] years = StaticCodes.yearArray;
		for (int i = 0; i < years.length; i++) {
			feesPaidYear.addItem(years[i]);
		}
		feesPaidYear.setBounds(xCordinateOfLabel + 140, getIncrementedValue(yValue, false), 100, COMPONENT_HEIGHT);
		feesPaidYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox source = (JComboBox) e.getSource();
				yearComboBoxValue = (Integer) source.getSelectedItem();

			}
		});
		panel.add(feesPaidYear);

		JButton simplePrintButton = new JButton("SIMPLE PRINT");
		simplePrintButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		simplePrintButton.addActionListener(new SimplePrintButtonHandler());
		panel.add(simplePrintButton);

		JButton printBySectorButton = new JButton("PRINT BY SECTOR");
		printBySectorButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		printBySectorButton.addActionListener(new SectorPrintButtonHandler());
		panel.add(printBySectorButton);

		JButton printByStreet = new JButton("PRINT BY STREET");
		printByStreet.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		printByStreet.addActionListener(new StreetPrintButtonHandler());
		panel.add(printByStreet);

		JButton printBySetTop = new JButton("PRINT BY SET TOP BOX");
		printBySetTop.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		printBySetTop.addActionListener(new SetTopBoxPrintButtonHandler());
		panel.add(printBySetTop);

		JButton backButton = new JButton("BACK");
		backButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);

		/*
		 * JCheckBox jcb = new JCheckBox("SET TOP BOX", true);
		 * jcb.setBounds(350, 50, 160, 40); jcb.setBackground(Color.CYAN);
		 * panel.add(jcb);
		 */

		/*
		 * JCheckBox jcb2 = new JCheckBox("NAME", true); jcb2.setBounds(350, 70,
		 * 160, 20); jcb2.setBackground(Color.CYAN); panel.add(jcb2);
		 * 
		 * JCheckBox jcb3 = new JCheckBox("QTR", true); jcb3.setBounds(350, 90,
		 * 160, 20); jcb3.setBackground(Color.CYAN); panel.add(jcb3);
		 * 
		 * JCheckBox jcb4 = new JCheckBox("SECTOR", true); jcb4.setBounds(350,
		 * 110, 160, 20); jcb4.setBackground(Color.CYAN); panel.add(jcb4);
		 * 
		 * JCheckBox jcb5 = new JCheckBox("STREET", true); jcb5.setBounds(350,
		 * 130, 160, 20); jcb5.setBackground(Color.CYAN); panel.add(jcb5);
		 * 
		 * JCheckBox jcb6 = new JCheckBox("MOBILE", true); jcb6.setBounds(350,
		 * 150, 160, 20); jcb6.setBackground(Color.CYAN); panel.add(jcb6);
		 * 
		 * JCheckBox jcb7 = new JCheckBox("SET TOP BOX", true);
		 * jcb7.setBounds(350, 170, 160, 20); jcb7.setBackground(Color.CYAN);
		 * panel.add(jcb7);
		 * 
		 * JCheckBox jcb8 = new JCheckBox("CAS NUMBER", true);
		 * jcb8.setBounds(350, 190, 160, 20); jcb8.setBackground(Color.CYAN);
		 * panel.add(jcb8);
		 * 
		 * JCheckBox jcb9 = new JCheckBox("SECTOR", true); jcb9.setBounds(350,
		 * 210, 160, 20); jcb9.setBackground(Color.CYAN); panel.add(jcb9);
		 */
		LOG.info("placeComponents EXIT");

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("PRINT-MENU-FRAME")).setVisible(false);
		}

	}

	private class SimplePrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (monthComboBoxValue == null || monthComboBoxValue.equalsIgnoreCase("Month")) {
				JOptionPane.showMessageDialog(null, "PLEASE SELECT MONTH !");
			} else {
				printMenuFrame.setVisible(false);
				SimplePrintTable simplePrintTable = new SimplePrintTable(monthComboBoxValue, yearComboBoxValue);
				simplePrintTable.showInTabularForm();
			}

		}

	}

	private class SetTopBoxPrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SetTopBoxPrintTable simplePrintTable = new SetTopBoxPrintTable();
			simplePrintTable.showInTabularForm();
		}
	}

	private class StreetPrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (monthComboBoxValue == null || monthComboBoxValue.equalsIgnoreCase("Month")) {
				JOptionPane.showMessageDialog(null, "PLEASE SELECT MONTH !");
			} else {

				Object result = JOptionPane.showInputDialog(printMenuFrame, "Enter Street:");
				LOG.info(result);
				if (result != null) {
					StreetPrintTable simplePrintTable = new StreetPrintTable((String) result, monthComboBoxValue, yearComboBoxValue);
					simplePrintTable.showInTabularForm();
				}

			}
		}

	}

	private class SectorPrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (monthComboBoxValue == null || monthComboBoxValue.equalsIgnoreCase("Month")) {
				JOptionPane.showMessageDialog(null, "Please select Month !");
				return;
			} else {

				Object result = JOptionPane.showInputDialog(printMenuFrame, "Enter Sector:");
				LOG.info(result);
				if (result != null) {
					SectorPrintTable simplePrintTable = new SectorPrintTable((String) result, monthComboBoxValue, yearComboBoxValue);
					simplePrintTable.showInTabularForm();
				}
			}

		}

	}

}
