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

import com.pms.entity.User;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.model.ArchivedUsersTableModel;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 *
 */
public class ArchivedUsersTable {

	private  Logger LOG = Logger.getLogger(getClass());

	private JTable table = null;
	public JFrame archivedUsersFrame = null;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	public void init(JFrame parentFrame) {
		parentFrame.setVisible(false);
		ArchivedUsersTableModel model = findArchivedUsers();
		prepareTable("ARCHIVED/DELETED USERS", model);
	}

	public void prepareTable(String name, AbstractTableModel model) {
		table = new JTable(model);
		archivedUsersFrame = new JFrame(name);
		archivedUsersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		archivedUsersFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		archivedUsersFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		archivedUsersFrame.setSize(screenSize);
		archivedUsersFrame.setVisible(true);

		JPanel jPanel = new JPanel();
		JButton backButton = new JButton("BACK");
		jPanel.add(backButton);
		backButton.addActionListener(new BackButtonHandler());
		archivedUsersFrame.add(jPanel, BorderLayout.SOUTH);
		Container.frameContainer.put("ARCHIVED-USERS-FRAME", archivedUsersFrame);
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			archivedUsersFrame.setVisible(false);
		}

	}

	private ArchivedUsersTableModel findArchivedUsers() {
		LOG.info("get archived user");
		List<User> userList = new ArrayList<User>(0);
		userList = userServiceImpl.getArchivedUsersList();
		ArchivedUsersTableModel model = new ArchivedUsersTableModel(userList);
		return model;
	}
}
