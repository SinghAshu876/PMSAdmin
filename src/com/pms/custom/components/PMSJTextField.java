package com.pms.custom.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PMSJTextField extends JTextField implements ApplicationConstants {

	private static final long serialVersionUID = 4147541847834401978L;

	public PMSJTextField() {
		super();
	}

	public PMSJTextField(int x) {
		super(x);
		Font f = new Font(FONT_PATTERN, Font.BOLD, FONT_SIZE);
		this.setFont(f);
		this.setForeground(Color.BLACK.brighter());
	}
	
	public PMSJTextField(int x, boolean dummy) {
		this(x);
		/**Disable cut copy paste functionality*/
		this.setTransferHandler(null);
	}
		

}
