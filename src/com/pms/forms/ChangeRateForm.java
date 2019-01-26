package com.pms.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.ArrayList;
//import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//import com.pms.enums.util.StaticCodes;
import com.pms.service.impl.UserServiceImpl;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class ChangeRateForm implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private JFrame changeRateFrame = null;
	private int xCordinateOfLabel = 40;
	private int xCordinateOfTextBox = 100;
	private Integer yValue = 50;
	private JPanel panel = null;
	private JTextField feeText = null;
	//private JTextField feeCodeText = null;
	//private String temp = "";
	private JButton updateAll = null;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	private Integer getIncrementedValue(int height, boolean increase) {

		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	public void init(JFrame parentFrame) {
		LOG.info("init ENTRY");
		parentFrame.setVisible(false);
		changeRateFrame = new JFrame("CHANGE RATE");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		changeRateFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		changeRateFrame.setSize(screenSize);
		changeRateFrame.setVisible(true);
		changeRateFrame.setResizable(false);
		changeRateFrame.setLocationRelativeTo(null);
		changeRateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new ColoredJPanel();
		changeRateFrame.add(panel);
		placeComponents(panel);
		changeRateFrame.getRootPane().setDefaultButton(updateAll);
		Container.frameContainer.put("CHANGE-RATE-FRAME", changeRateFrame);
		LOG.info("init EXIT");

	}

	private void placeComponents(JPanel panel) {
		LOG.info("placeComponents ENTRY");
		JLabel totalAmnt = new JLabel("FEE :");
		totalAmnt.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), 300, 20);
		panel.add(totalAmnt);

		feeText = new PMSJTextField(20);
		feeText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
/*		feeText.addKeyListener(new KeyListener() {

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
		});*/

		((AbstractDocument) feeText.getDocument()).setDocumentFilter(new NumberTextFieldDocumentFilter(4));
		panel.add(feeText);

		/*JLabel feeCodeLabel = new JLabel("FEE CODE:");
		feeCodeLabel.setBounds(180, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		panel.add(feeCodeLabel);

		feeCodeText = new PMSJTextField(20);
		feeCodeText.setEditable(false);
		feeCodeText.setBounds(270, getIncrementedValue(yValue, false), 60, COMPONENT_HEIGHT);
		panel.add(feeCodeText);*/

		updateAll = new JButton("UPDATE ALL");
		updateAll.setBounds(350, getIncrementedValue(yValue, false), 140, COMPONENT_HEIGHT);
		updateAll.addActionListener(new UpdateAllButtonHandler());
		panel.add(updateAll);

		JButton backButton = new JButton(BACK);
		backButton.setBounds(500, getIncrementedValue(yValue, false), 80, COMPONENT_HEIGHT);
		backButton.addActionListener(new BackButtonHandler());
		panel.add(backButton);
		LOG.info("placeComponents EXIT");

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.info("BackButtonHandler ENTRY");
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("CHANGE-RATE-FRAME")).setVisible(false);
			LOG.info("BackButtonHandler EXIT");
		}

	}

	private class UpdateAllButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.info("UpdateAllButtonHandler ENTRY");
			String fee = feeText.getText();
			if (fee.isEmpty()) {
				JOptionPane.showMessageDialog(null, "ENTER A VALUE IN FEE !");
				return;
			}
			Map<Integer, Integer> successValueMap = userServiceImpl.updateAllUsersFees(feeText.getText());
			List<Integer> unsuccessfullIdList = new ArrayList<Integer>();
			for (Map.Entry<Integer, Integer> iterator : successValueMap.entrySet()) {
				if (iterator.getValue().intValue() != 1) {
					unsuccessfullIdList.add(iterator.getKey());
				}

			}
			if (unsuccessfullIdList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "UPDATED ALL THE FEES  DETAILS !");
				return;
			} else {
				JOptionPane.showMessageDialog(null, "PROBLEM SAVING USER ENTRIES " + unsuccessfullIdList + "PLEASE TRY FROM UPDATE SCREEN");
				return;
			}
		}

	}
}
