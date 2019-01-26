package com.pms.forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.pms.custom.components.ColoredJPanel;
import com.pms.custom.components.PMSJPasswordField;
import com.pms.custom.components.PMSJTextField;
import com.pms.dao.ApplicationUsersDAO;
import com.pms.document.filters.AlphaNumericTextFieldDocumentFilter;
import com.pms.entity.ApplicationUsers;
import com.pms.menu.MainMenu;
import com.pms.server.DBServerStartup;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.util.PropertyUtil;

public class LoginForm implements ApplicationConstants {

	private JFrame loginForm ;
	private JTextField userNameText ;
	private JPasswordField passwordText ;
	private int xCordinateOfLabel = 40;
	private int xCordinateOfTextBox = 230;
	private int componentWidth = 210;
	private JButton loginButton ;
	private String applicationComboBoxValue ;

	public void init() {
		loginForm = new JFrame("LOGIN FORM");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		loginForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
		loginForm.setSize(screenSize);

		loginForm.setVisible(true);
		loginForm.setResizable(false);
		loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new ColoredJPanel();
		loginForm.add(panel);
		placeComponents(panel);
		loginForm.getRootPane().setDefaultButton(loginButton);
		Container.frameContainer.put("LOGIN-FRAME", loginForm);
	}

	private Integer yValue = 50;

	private Integer getIncrementedValue(int height, boolean increase) {
		if (increase) {
			return yValue = height + 40;
		} else
			return height;
	}

	private void placeComponents(JPanel panel) {
		JLabel userNameLabel = new JLabel("USER NAME:");
		userNameLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		panel.add(userNameLabel);

		userNameText = new PMSJTextField(20, true);
		userNameText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) userNameText.getDocument()).setDocumentFilter(new AlphaNumericTextFieldDocumentFilter(20));
		panel.add(userNameText);

		JLabel passwordLabel = new JLabel("PASSWORD:");
		passwordLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(passwordLabel);

		passwordText = new PMSJPasswordField(20);
		passwordText.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		((AbstractDocument) passwordText.getDocument()).setDocumentFilter(new AlphaNumericTextFieldDocumentFilter(20));
		panel.add(passwordText);

		JLabel applicationLabel = new JLabel("APPLICATION:");
		applicationLabel.setBounds(xCordinateOfLabel, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		panel.add(applicationLabel);

		JComboBox<String> application = new JComboBox<String>();
		application.setEditable(false);
		String[] env = ENV;
		for (int i = 0; i < env.length; i++) {
			application.addItem(env[i]);
		}

		application.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, false), componentWidth, COMPONENT_HEIGHT);
		application.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox source = (JComboBox) e.getSource();
				applicationComboBoxValue = (String) source.getSelectedItem();
				new PropertyUtil().loadProperties(applicationComboBoxValue);
			}
		});
		panel.add(application);

		loginButton = new JButton("LOGIN ");
		loginButton.setBounds(xCordinateOfTextBox, getIncrementedValue(yValue, true), componentWidth, COMPONENT_HEIGHT);
		loginButton.addActionListener(new LoginButtonHandler());

		panel.add(loginButton);
	}

	private class LoginButtonHandler implements ActionListener {
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if (userNameText.getText().equals(EMPTY_STRING) || passwordText.getText().equals(EMPTY_STRING) || (applicationComboBoxValue == null || applicationComboBoxValue.equals(EMPTY_STRING))) {
				JOptionPane.showMessageDialog(null, "PLEASE ENTER USERNAME AND PASSWORD AND APPLICATION");
				return;
			}
			initDB();
			List<ApplicationUsers> applicationsUsers = new ApplicationUsersDAO().readLoginDetails(userNameText.getText(), passwordText.getText());
			if (applicationsUsers.isEmpty()) {
				JOptionPane.showMessageDialog(null, "USER NAME OR PASSWORD IS WRONG");
			} else {
				JOptionPane.showMessageDialog(null, "WELCOME " + applicationsUsers.get(0).getUserAlias());
				new MainMenu().init();
				loginForm.setVisible(false);
			}
		}

	}

	private void initDB() {
		try {
			Thread dbServerStartupThread = new DBServerStartup().getThread();
			dbServerStartupThread.start();
			dbServerStartupThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
