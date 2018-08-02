package com.pms.custom.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;

import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PMSJPasswordField extends JPasswordField implements ApplicationConstants {

	private static final long serialVersionUID = -8487131392360055404L;

	public PMSJPasswordField() {
		super();
	}

	public PMSJPasswordField(int x) {
		super(x);
		Font f = new Font(FONT_PATTERN, Font.BOLD, FONT_SIZE);
		this.setFont(f);
		this.setForeground(Color.BLACK.brighter());
		/**Disable cut copy paste functionality*/
		this.setTransferHandler(null);
	}

}
