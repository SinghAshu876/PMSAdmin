package com.pms.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.pms.entity.User;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.button.editor.DisconnectedUsersTableButtonEditor;
import com.pms.table.button.renderer.DisconnectedUsersTableButtonRenderer;
import com.pms.table.model.DisconnectedUsersTableModel;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class DisconnectedUsersTable {
	
	private  Logger LOG = Logger.getLogger(getClass());

	private JTable table = null;
	public JFrame disconnectedUsersFrame = null;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	public void init(JFrame parentFrame) {
		parentFrame.setVisible(false);
		DisconnectedUsersTableModel model = findInactiveUsers();
		prepareTable("DISCONNECTED USERS", model);
	}

	public void prepareTable(String name, AbstractTableModel model) {
		table = new JTable(model);
		table.getColumn("RECONNECT").setCellRenderer(new DisconnectedUsersTableButtonRenderer());
		table.getColumn("RECONNECT").setCellEditor(new DisconnectedUsersTableButtonEditor(new JCheckBox()));
		disconnectedUsersFrame = new JFrame(name);
		disconnectedUsersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disconnectedUsersFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		disconnectedUsersFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		disconnectedUsersFrame.setSize(screenSize);
		disconnectedUsersFrame.setVisible(true);

		JPanel jPanel = new JPanel();
		JButton backButton = new JButton("BACK");
		jPanel.add(backButton);
		backButton.addActionListener(new BackButtonHandler());
		disconnectedUsersFrame.add(jPanel, BorderLayout.SOUTH);
		Container.frameContainer.put("DISCONNECTED-USERS-FRAME", disconnectedUsersFrame);
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MAIN-MENU-FRAME")).setVisible(true);
			disconnectedUsersFrame.setVisible(false);
		}

	}

	private DisconnectedUsersTableModel findInactiveUsers() {
		List<User> userList = new ArrayList<User>(0);
		String sql = "where active='N'";
		LOG.info("findInactiveUsers sql :" + sql);
		userList = userServiceImpl.readUsersBasedOnCondition(sql);
		DisconnectedUsersTableModel model = new DisconnectedUsersTableModel(userList);
		return model;
	}

}
