package com.pms.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.pms.entity.User;
import com.pms.forms.SearchForm;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.button.editor.SearchResultsTableButtonEditor;
import com.pms.table.button.renderer.SearchResultsTableButtonRenderer;
import com.pms.table.model.SearchResultsTableModel;
import com.pms.util.Container;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SearchResultsTable {

	private  Logger LOG = Logger.getLogger(getClass());
	
	private JTable table = null;
	public JFrame searchResultsFrame = null;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();

	public void getUsersBasedOnCondition(LinkedHashMap<String, String> conditions) {
		SearchResultsTableModel model = prepareQuery(conditions);
		prepareTable("SEARCH RESULTS", model);
	}

	public void prepareTable(String name, AbstractTableModel model) {
		table = new JTable(model);
		table.getColumn("DETAILS").setCellRenderer(new SearchResultsTableButtonRenderer());
		table.getColumn("DETAILS").setCellEditor(new SearchResultsTableButtonEditor(new JCheckBox()));
		searchResultsFrame = new JFrame(name);
		searchResultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		searchResultsFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		searchResultsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		searchResultsFrame.setSize(screenSize);
		searchResultsFrame.setVisible(true);

		JPanel jPanel = new JPanel();
		JButton backButton = new JButton("BACK");
		jPanel.add(backButton);
		backButton.addActionListener(new BackButtonHandler());
		searchResultsFrame.add(jPanel, BorderLayout.SOUTH);
		Container.frameContainer.put("SEARCH-RESULTS-FRAME", searchResultsFrame);
	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SearchForm.searchFormFrame.setVisible(true);
			searchResultsFrame.setVisible(false);
		}

	}

	private SearchResultsTableModel prepareQuery(LinkedHashMap<String, String> conditions) {
		List<User> userList = new ArrayList<User>();
		String sql = null;
		int count = 0;
		for (Map.Entry<String, String> reader : conditions.entrySet()) {
			if (count == 0) {
				sql = "where ";
			} else {
				sql = sql + "and ";
			}
			sql = sql + reader.getKey() + " like '%" + reader.getValue() + "%'";
			count = count + 1;
		}
		LOG.info("prepareQuery sql :" + sql);
		if (sql != null)
			userList = userServiceImpl.readUsersBasedOnCondition(sql);
		SearchResultsTableModel model = new SearchResultsTableModel(userList);
		return model;
	}

}
