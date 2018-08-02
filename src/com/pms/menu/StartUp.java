package com.pms.menu;

import javax.swing.SwingUtilities;

//import org.apache.log4j.Logger;

import com.pms.forms.LoginForm;
import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class StartUp implements ApplicationConstants {

	public static void main(String[] args) throws InterruptedException {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginForm().init();
			}
		});

	}

}
