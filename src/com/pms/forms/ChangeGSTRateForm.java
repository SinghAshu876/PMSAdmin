package com.pms.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.apache.log4j.Logger;

import com.pms.custom.components.ColoredJPanel;
import com.pms.custom.components.PMSJTextField;
import com.pms.document.filters.NumberTextFieldDocumentFilter;
import com.pms.service.impl.ApplicationPropsServiceImpl;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class ChangeGSTRateForm implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private JFrame changeGSTRateForm ;
	private int xCordinateOfLabel = 40;
	private int xCordinateOfTextBox = 100;
	private Integer yValue = 50;
	private JPanel panel ;
	private JTextField gstText ;
	private JTextField ncfText ;
	private JButton updateGSTButton;
	private JButton updateNCFButton; 
	private ApplicationPropsServiceImpl appProp = new ApplicationPropsServiceImpl();

	private Integer getIncrementedValue(int height, boolean increase) {

		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	public void init(JFrame parentFrame) {
		LOG.info("init ENTRY");
		parentFrame.setVisible(false);
		changeGSTRateForm = new JFrame("CHANGE GLOBAL RATE");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		changeGSTRateForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
		changeGSTRateForm.setSize(screenSize);
		changeGSTRateForm.setVisible(true);
		changeGSTRateForm.setResizable(false);
		changeGSTRateForm.setLocationRelativeTo(null);
		changeGSTRateForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new ColoredJPanel();
		changeGSTRateForm.add(panel);
		placeComponents(panel);
		changeGSTRateForm.getRootPane().setDefaultButton(updateGSTButton);
		Container.frameContainer.put("CHANGE-GST-RATE-FRAME", changeGSTRateForm);
		LOG.info("init EXIT");

	}

	private void placeComponents(JPanel panel) {
		LOG.info("placeComponents ENTRY");
		JLabel gstPercent = new JLabel("GST :");
		gstPercent.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), 300, 20);
		panel.add(gstPercent);

		gstText = new PMSJTextField(20);
		gstText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
		gstText.setText(appProp.fetchProperty(GST));

		((AbstractDocument) gstText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(gstText);

		updateGSTButton = new JButton("UPDATE GST");
		updateGSTButton.setBounds(180, getIncrementedValue(yValue, false), 140, COMPONENT_HEIGHT);
		updateGSTButton.addActionListener(new UpdateGSTButtonHandler());
		panel.add(updateGSTButton);
		
		JLabel ncfAmount = new JLabel("NCF :");
		ncfAmount.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), 300, 20);
		panel.add(ncfAmount);

		ncfText = new PMSJTextField(20);
		ncfText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
		ncfText.setText(appProp.fetchProperty(NCF));

		((AbstractDocument) ncfText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(ncfText);

		updateNCFButton = new JButton("UPDATE NCF");
		updateNCFButton.setBounds(180, getIncrementedValue(yValue, false), 140, COMPONENT_HEIGHT);
		updateNCFButton.addActionListener(new UpdateNCFButtonHandler());
		panel.add(updateNCFButton);
		

		JButton backButton = new JButton(BACK);
		backButton.setBounds(340, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);
		LOG.info("placeComponents EXIT");

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.info("BackButtonHandler ENTRY");
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("CHANGE-GST-RATE-FRAME")).setVisible(false);
			LOG.info("BackButtonHandler EXIT");
		}

	}
	
	private class UpdateNCFButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.info("UpdateNCFButtonHandler ENTRY");
			String gst = ncfText.getText();
			if (gst.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ENTER A VALUE IN MCF !");
				return;
			}
			int success = appProp.updateProperty(ncfText.getText() ,NCF);

			if (success == 1) {
				JOptionPane.showMessageDialog(null, "UPDATED NCF !");
				return;
			} else {
				JOptionPane.showMessageDialog(null, "PROBLEM SAVING NCF");
				return;
			}
		}

	}
	

	private class UpdateGSTButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.info("UpdateGSTButtonHandler ENTRY");
			String gst = gstText.getText();
			if (gst.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ENTER A VALUE IN GST !");
				return;
			}
			int success = appProp.updateProperty(gstText.getText(),GST);

			if (success == 1) {
				JOptionPane.showMessageDialog(null, "UPDATED GST !");
				return;
			} else {
				JOptionPane.showMessageDialog(null, "PROBLEM SAVING GST");
				return;
			}
		}

	}
}
