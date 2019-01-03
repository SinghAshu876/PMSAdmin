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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.pms.custom.components.PMSJTextField;
import com.pms.entity.ChannelDetails;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.model.ChannelDetailsTableModel;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class ConfigureChannelsTable implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());

	private JTable table = null;
	public JFrame configureChannelsFrame = null;
	private int componentWidth = 210;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private JTextField channelIdText = new PMSJTextField();
	private JTextField channelName = new PMSJTextField();
	private JTextField channelPrice = new PMSJTextField();

	public void init(JFrame parentFrame) {
		parentFrame.setVisible(false);
		List<ChannelDetails> channelDetailsList = findAllChannels();
		ChannelDetailsTableModel model = new ChannelDetailsTableModel(channelDetailsList);
		prepareTable("CONFIGURE CHANNEL", model);
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

		channelIdText = new PMSJTextField(20);
		channelIdText.setBounds(120, 400, componentWidth, COMPONENT_HEIGHT);
		channelIdText.setDisabledTextColor(Color.BLACK);
		channelIdText.setEnabled(false);
		channelIdText.setEditable(false);
		configureChannelsFrame.add(channelIdText);

		channelName = new PMSJTextField();
		channelName.setBounds(120, 440, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(channelName);

		channelPrice = new PMSJTextField();
		channelPrice.setBounds(120, 480, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(channelPrice);

		JButton addButton = new JButton("ADD CHANNEL");
		addButton.setBounds(400, 400, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(addButton);
		addButton.addActionListener(new AddButtonHandler());

		JButton updateButton = new JButton("UPDATE CHANNEL");
		updateButton.setBounds(400, 440, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(updateButton);
		updateButton.addActionListener(new UpdateButtonHandler());

		JButton deleteButton = new JButton("DELETE CHANNEL");
		deleteButton.setBounds(400, 480, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(deleteButton);
		deleteButton.addActionListener(new DeleteButtonHandler());

		JButton backButton = new JButton("BACK");
		backButton.setBounds(400, 520, componentWidth, COMPONENT_HEIGHT);
		configureChannelsFrame.add(backButton);
		backButton.addActionListener(new BackButtonHandler());
		Container.frameContainer.put("ARCHIVED-USERS-FRAME", configureChannelsFrame);
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

		}

	}

	private class UpdateButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

	private class OnClickMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			int i = table.getSelectedRow();

			channelIdText.setText(table.getValueAt(i, 0).toString());
			channelName.setText(table.getValueAt(i, 1).toString());
			channelPrice.setText(table.getValueAt(i, 2).toString());

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

	private class DeleteButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int channelId = Integer.valueOf(channelIdText.getText());
			int response = deleteChannel(channelId);
			LOG.info("response " + response);
			List<ChannelDetails> updateChannelDetails = findAllChannels();
			ChannelDetailsTableModel model = new ChannelDetailsTableModel(updateChannelDetails);
			table.setModel(model);
		}

	}

	private List<ChannelDetails> findAllChannels() {
		LOG.info("find all channels");
		List<ChannelDetails> channelDetailsList = new ArrayList<ChannelDetails>(0);
		channelDetailsList = userServiceImpl.getChannelDetails();

		return channelDetailsList;
	}

	private void addChannel() {

	}

	private void updateChannel(ChannelDetails channelDetails) {

	}

	private int deleteChannel(int channelId) {
		return userServiceImpl.deleteChannelDetails(channelId);
	}
}
