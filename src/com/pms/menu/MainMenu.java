package com.pms.menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.pms.custom.components.ColoredJPanel;
import com.pms.forms.ChangeGSTRateForm;
import com.pms.forms.CollectionForm;
import com.pms.forms.MonthlyPaidForm;
import com.pms.forms.NewEntryForm;
import com.pms.forms.SearchForm;
import com.pms.forms.UpdateEntryForm;
import com.pms.service.impl.ApplicationPropsServiceImpl;
import com.pms.table.ArchivedUsersTable;
import com.pms.table.ConfigureChannelsTable;
import com.pms.table.DisconnectedUsersTable;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.util.EmailUtil;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class MainMenu  implements ApplicationConstants {

	private  Logger LOG = Logger.getLogger(getClass());
	
	private int xCordinateOfLabel = 550;
	private int componentWidth = 200;
	private Integer yValue = 50;
	private ApplicationPropsServiceImpl appProp = new ApplicationPropsServiceImpl();

	private Integer getIncrementedValue(int height, boolean increase) {
		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	public JFrame mainMenuFrame = null;

	public void init() {
		LOG.info("init ENTRY");
		mainMenuFrame = new JFrame("MAIN MENU");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainMenuFrame.setSize(screenSize);
		mainMenuFrame.setVisible(true);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new ColoredJPanel();
		mainMenuFrame.add(panel);
		placeComponents(panel);
		Container.frameContainer.put("MAIN-MENU-FRAME", mainMenuFrame);
		LOG.info("init EXIT");

	}

	private void placeComponents(JPanel panel) {
		LOG.info("placeComponents ENTRY");
		JButton printButtonHandler = new JButton("PRINT");
		printButtonHandler.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		printButtonHandler.addActionListener(new PrintButtonHandler());
		panel.add(printButtonHandler);

		JButton newEntryButton = new JButton("NEW ENTRY");
		newEntryButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		newEntryButton.addActionListener(new NewEntryButtonHandler());
		panel.add(newEntryButton);

		JButton searchButton = new JButton("SEARCH");
		searchButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		searchButton.addActionListener(new SearchButtonHandler());
		panel.add(searchButton);

		JButton searchByIdButton = new JButton("ID SEARCH");
		searchByIdButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		searchByIdButton.addActionListener(new SearchByIDButtonHandler());
		panel.add(searchByIdButton);

		JButton collectionButton = new JButton("COLLECTION");
		collectionButton.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		collectionButton.addActionListener(new CollectionButtonHandler());
		panel.add(collectionButton);

		/*JButton changeRate = new JButton("CHANGE RATE");
		changeRate.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		changeRate.addActionListener(new ChangeRateButtonHandler());
		panel.add(changeRate);*/
		
		JButton changeGSTRate = new JButton("CHANGE GST RATE");
		changeGSTRate.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		changeGSTRate.addActionListener(new ChangeGSTRateButtonHandler());
		panel.add(changeGSTRate);

		JButton monthlyPaid = new JButton("MONTHLY PAID");
		monthlyPaid.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		monthlyPaid.addActionListener(new MonthlyPaidButtonHandler());
		panel.add(monthlyPaid);

		JButton disconnectedUsers = new JButton("DISCONNECTED USERS");
		disconnectedUsers.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		disconnectedUsers.addActionListener(new DisconnectedUsersButtonHandler());
		panel.add(disconnectedUsers);

		JButton backUPData = new JButton("BACK UP DATA");
		backUPData.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		backUPData.addActionListener(new BackUpDataButtonHandler());
		panel.add(backUPData);
		
		JButton deletedUsers = new JButton("ARCHIVED / DELETED USERS");
		deletedUsers.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		deletedUsers.addActionListener(new ArchivedUsersButtonHandler());
		panel.add(deletedUsers);
		
		JButton configureChannels = new JButton("CONFIGURE CHANNELS");
		configureChannels.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		configureChannels.addActionListener(new ConfigureChannelsButtonHandler());
		panel.add(configureChannels);
		
		LOG.info("placeComponents EXIT");

	}

	private class DisconnectedUsersButtonHandler implements ActionListener {

		@Override
		
		public void actionPerformed(ActionEvent e) {
			new DisconnectedUsersTable().init(mainMenuFrame);
		}
	}

	private class BackUpDataButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().beep();
			String message  = new EmailUtil().sendMailWithAttachment();		
			JOptionPane.showMessageDialog(null, message);
		}
	}
	
	private class ArchivedUsersButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().beep();
			new ArchivedUsersTable().init(mainMenuFrame);
		}
	}
	
	private class ConfigureChannelsButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().beep();
			new ConfigureChannelsTable().init(mainMenuFrame);
			
		}
	}

	private class PrintButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new PrintMenu().init(mainMenuFrame);

		}

	}

	private class NewEntryButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new NewEntryForm().init(mainMenuFrame);

		}

	}

	private class SearchButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new SearchForm().init(mainMenuFrame);

		}

	}

	private class SearchByIDButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			Object result = JOptionPane.showInputDialog(mainMenuFrame, "ENTER ID:");
			String inputValue = (String) result;
			if (result != null) {
				try {
					Integer id = Integer.valueOf(inputValue);
					UpdateEntryForm updateEntryForm = new UpdateEntryForm(id);
					updateEntryForm.init("MAIN-MENU-FRAME");
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null, "PLEASE ENTER A VALID NUMBER");
				}
			}

		}
	}

	private class CollectionButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object result = JOptionPane.showInputDialog(mainMenuFrame, "ENTER PASSWORD:");
			String inputValue = (String) result;
			if (inputValue != null && appProp.fetchProperty(SECOND_FACTOR_AUTH).equals(inputValue)) {
				new CollectionForm().init(mainMenuFrame);
			}
			else{
				JOptionPane.showMessageDialog(null, "YOU DO NOT HAVE ACCESS TO THIS MENU");
			}
		}
	}

	/*private class ChangeRateButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new ChangeRateForm().init(mainMenuFrame);

		}

	}*/
	
	private class ChangeGSTRateButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new ChangeGSTRateForm().init(mainMenuFrame);

		}

	}

	private class MonthlyPaidButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new MonthlyPaidForm().init();
		}

	}
}