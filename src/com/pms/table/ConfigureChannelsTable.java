package com.pms.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.pms.entity.ChannelDetails;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.model.ChannelDetailsTableModel;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class ConfigureChannelsTable {

	private  Logger LOG = Logger.getLogger(getClass());

	private JTable table = null;
	public JFrame configureChannelsFrame = null;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	public void init(JFrame parentFrame) {
		parentFrame.setVisible(false);
		ChannelDetailsTableModel model = findAllChannels();
		prepareTable("CONFIGURE CHANNEL", model);
	}

	public void prepareTable(String name, AbstractTableModel model) {
		table = new JTable(model);
		configureChannelsFrame = new JFrame(name);
		configureChannelsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		configureChannelsFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		configureChannelsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		configureChannelsFrame.setSize(screenSize);
		configureChannelsFrame.setVisible(true);

		JPanel jPanel = new JPanel();
		JButton backButton = new JButton("BACK");
		jPanel.add(backButton);
		backButton.addActionListener(new BackButtonHandler());
		configureChannelsFrame.add(jPanel, BorderLayout.SOUTH);
		Container.frameContainer.put("ARCHIVED-USERS-FRAME", configureChannelsFrame);
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			configureChannelsFrame.setVisible(false);
		}

	}

	private ChannelDetailsTableModel findAllChannels() {
		LOG.info("find all channels");
		List<ChannelDetails> channelDetailsList = new ArrayList<ChannelDetails>(0);
		channelDetailsList = userServiceImpl.getChannelDetails();
		ChannelDetailsTableModel model = new ChannelDetailsTableModel(channelDetailsList);
		return model;
	}
}
