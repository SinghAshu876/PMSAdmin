package com.pms.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.AbstractDocument;

import org.apache.log4j.Logger;

import com.pms.custom.components.PMSJComboBox;
import com.pms.custom.components.PMSJTextField;
import com.pms.document.filters.UpperCaseWithLimitDocumentFilter;
import com.pms.entity.ChannelDetails;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.model.AlaCarteChannelDetailsTableModel;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class AlaCarteChannelsTable implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private JTable table ;
	public JFrame configureChannelsFrame;
	private int componentWidth = 210;
	private int xCordinateOfTextBox = 120;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private JTextField channelIdText = new PMSJTextField();
	private JTextField channelNameText = new PMSJTextField();
	private JTextField channelPriceText = new PMSJTextField();
	private JButton addButton ;
	private JButton updateButton ;
	private JButton deleteButton ;
	private String channelTypeComboBoxValue ;
	private PMSJComboBox<String> channelTypeComboBox;

	public void init(JFrame parentFrame) {
		parentFrame.setVisible(false);
		List<ChannelDetails> channelDetailsList = findAllChannels();
		AlaCarteChannelDetailsTableModel model = new AlaCarteChannelDetailsTableModel(channelDetailsList);
		prepareTable("CONFIGURE ALA-CARTE CHANNELS", model);
	}

	public void prepareTable(String name, AbstractTableModel model) {
		table = new JTable(model);
		Font font = new Font("", 1, 22);
		table.setFont(font);
		table.setRowHeight(30);
		table.addMouseListener(new OnClickMouseListener());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight() / 2);

		configureChannelsFrame = new JFrame(name);
		configureChannelsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		configureChannelsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		configureChannelsFrame.setLayout(null);
		configureChannelsFrame.add(jScrollPane);
		configureChannelsFrame.setVisible(true);

		JLabel channelIdLabel = new JLabel("CHANNEL ID :");
		channelIdLabel.setBounds(10, 400, 100, COMPONENT_HEIGHT);
		configureChannelsFrame.add(channelIdLabel);

		JLabel channelNameLabel = new JLabel("CHANNEL NAME :");
		channelNameLabel.setBounds(10, 440, 100, COMPONENT_HEIGHT);
		configureChannelsFrame.add(channelNameLabel);

		JLabel channelPriceLabel = new JLabel("CHANNEL PRICE :");
		channelPriceLabel.setBounds(10, 480, 100, COMPONENT_HEIGHT);
		configureChannelsFrame.add(channelPriceLabel);
		
		JLabel channelTypeLabel = new JLabel("CHANNEL TYPE :");
		channelTypeLabel.setBounds(10, 520, 100, COMPONENT_HEIGHT);
		configureChannelsFrame.add(channelTypeLabel);

		channelIdText = new PMSJTextField(20);
		channelIdText.setBounds(xCordinateOfTextBox, 400, componentWidth, COMPONENT_HEIGHT);
		channelIdText.setDisabledTextColor(Color.BLACK);
		channelIdText.setEnabled(false);
		channelIdText.setEditable(false);
		configureChannelsFrame.add(channelIdText);

		channelNameText = new PMSJTextField(20);
		channelNameText.setBounds(xCordinateOfTextBox, 440, componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) channelNameText.getDocument()).setDocumentFilter(new UpperCaseWithLimitDocumentFilter(30));
		configureChannelsFrame.add(channelNameText);

		channelPriceText = new PMSJTextField(20);
		channelPriceText.setBounds(xCordinateOfTextBox, 480, componentWidth, COMPONENT_HEIGHT);
		//((AbstractDocument) channelPriceText.getDocument()).setDocumentFilter(new DecimalTextFieldDocumentFilter(5));
		configureChannelsFrame.add(channelPriceText);
		
		channelTypeComboBox = new PMSJComboBox<String>();
		channelTypeComboBox.setEditable(false);
		String[] channelType = CHANNEL_TYPE;
		for (int i = 0; i < channelType.length; i++) {
			channelTypeComboBox.addItem(channelType[i]);
		}

		channelTypeComboBox.setBounds(xCordinateOfTextBox, 520, componentWidth, COMPONENT_HEIGHT);
		channelTypeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox source = (JComboBox) e.getSource();
				channelTypeComboBoxValue = (String) source.getSelectedItem();
				
			}
		});
		configureChannelsFrame.add(channelTypeComboBox);

		addButton = new JButton("ADD CHANNEL");
		addButton.setBounds(400, 400, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(addButton);
		addButton.addActionListener(new AddButtonHandler());
		addButton.setEnabled(true);

		updateButton = new JButton("UPDATE CHANNEL");
		updateButton.setBounds(400, 440, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(updateButton);
		updateButton.addActionListener(new UpdateButtonHandler());
		updateButton.setEnabled(false);

        deleteButton = new JButton("DELETE CHANNEL");
		deleteButton.setBounds(400, 480, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(deleteButton);
		deleteButton.addActionListener(new DeleteButtonHandler());
		deleteButton.setEnabled(false);

		JButton backButton = new JButton("BACK");
		backButton.setBounds(400, 520, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(backButton);
		backButton.addActionListener(new BackButtonHandler());
		Container.frameContainer.put("CONFIGURE-ALA-CARTE-CHANNELS-FRAME", configureChannelsFrame);
	}

	private class OnClickMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int i = table.getSelectedRow();
			channelIdText.setText(table.getValueAt(i, 0).toString());
			channelNameText.setText(table.getValueAt(i, 1).toString());
			channelPriceText.setText(table.getValueAt(i, 2).toString());
			channelTypeComboBox.setSelectedItem(table.getValueAt(i, 3).toString());
			addButton.setEnabled(false);
			updateButton.setEnabled(true);
			deleteButton.setEnabled(true);

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			configureChannelsFrame.setVisible(false);
		}

	}

	private class AddButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			int channelId = userServiceImpl.getNextChannelId();
			
			if (channelNameText.getText() == null || channelNameText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "CHANNEL NAME CANNOT BE BLANK");
				return;

			}
			if (channelPriceText.getText() == null || channelPriceText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "CHANNEL PRICE CANNOT BE BLANK");
				return;
			}
			if(!isValidAmount(channelPriceText.getText())) {
				JOptionPane.showMessageDialog(null, "PLEASE ENTER VALID PRICE");
				return;
			}

			String chnlName = channelNameText.getText();
			double chnlPrice = Double.valueOf(channelPriceText.getText());

			ChannelDetails channelDetails = new ChannelDetails();
			channelDetails.setChannelId(channelId);
			channelDetails.setChannelName(chnlName);
			channelDetails.setChannelPrice(chnlPrice);
			channelDetails.setChannelType(channelTypeComboBoxValue == null ?SD :channelTypeComboBoxValue);

			int response = addChannel(channelDetails);
			LOG.info("response " + response);

			if (response == -1) {
				JOptionPane.showMessageDialog(null, "PROBLEM OCCURED WHILE SAVING CHANNEL ! CONTACT DEVELOPER");
				return;
			} else {
				refreshTable();				
			}
		}

		

	}

	private class UpdateButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (channelNameText.getText() == null || channelNameText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "CHANNEL NAME CANNOT BE BLANK");
				return;

			}
			if (channelPriceText.getText() == null || channelPriceText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "CHANNEL PRICE CANNOT BE BLANK");
				return;

			}
			
			if(!isValidAmount(channelPriceText.getText())) {
				JOptionPane.showMessageDialog(null, "PLEASE ENTER VALID PRICE");
				return;
			}

			int channelId = Integer.valueOf(channelIdText.getText());
			String chnlName = channelNameText.getText();
			double chnlPrice = Double.valueOf(channelPriceText.getText());

			ChannelDetails channelDetails = new ChannelDetails();
			channelDetails.setChannelId(channelId);
			channelDetails.setChannelName(chnlName);
			channelDetails.setChannelPrice(chnlPrice);
			channelDetails.setChannelType(channelTypeComboBoxValue == null ?SD :channelTypeComboBoxValue);
			int response = updateChannel(channelDetails);
			LOG.info("response " + response);
			if (response == -1) {
				JOptionPane.showMessageDialog(null, "PROBLEM OCCURED WHILE UPDATING CHANNEL ! CONTACT DEVELOPER");
				return;
			} else {
				refreshTable();
			}

		}

	}

	private class DeleteButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (channelIdText.getText() == null || channelIdText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "CHANNEL ID CANNOT BE BLANK");
				return;
			}

			int channelId = Integer.valueOf(channelIdText.getText());
			int response = deleteChannel(channelId);
			LOG.info("response " + response);
			if (response == -1) {
				JOptionPane.showMessageDialog(null, "PROBLEM OCCURED WHILE DELETING CHANNEL ! CONTACT DEVELOPER");
				return;
			} else {
				refreshTable();
			}

		}

	}

	private List<ChannelDetails> findAllChannels() {
		LOG.info("find all channels");
		List<ChannelDetails> channelDetailsList = new ArrayList<ChannelDetails>(0);
		channelDetailsList = userServiceImpl.getAllChannelDetails();

		return channelDetailsList;
	}

	private void refreshTable() {
		List<ChannelDetails> updateChannelDetails = findAllChannels();
		AlaCarteChannelDetailsTableModel model = new AlaCarteChannelDetailsTableModel(updateChannelDetails);
		table.setModel(model);
		channelNameText.setText(EMPTY_STRING);
		channelPriceText.setText(EMPTY_STRING);
		channelIdText.setText(EMPTY_STRING);
		channelTypeComboBox.setSelectedItem(CHANNEL_TYPE[0]);
		addButton.setEnabled(true);
		updateButton.setEnabled(false);
		deleteButton.setEnabled(false);

	}
	
	private boolean isValidAmount(String text) {
		boolean isValidAmount = true;
		try {
			 Double.valueOf(text);
		}
		catch (NumberFormatException e) {
			isValidAmount =  false;
		}
		return isValidAmount;
		
		
	}

	private int addChannel(ChannelDetails channelDetails) {
		return userServiceImpl.addChannelDetails(channelDetails);
	}

	private int updateChannel(ChannelDetails channelDetails) {
		return userServiceImpl.updateChannelDetails(channelDetails);
	}

	private int deleteChannel(int channelId) {
		return userServiceImpl.deleteChannelDetails(channelId);
	}

}
