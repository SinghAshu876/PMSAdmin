package com.pms.custom.components;

import java.awt.Color;
import java.awt.Font;

import org.jdesktop.swingx.JXDatePicker;

import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PMSJXDatePicker extends JXDatePicker implements ApplicationConstants {

	private static final long serialVersionUID = -2876307541970980891L;

	public PMSJXDatePicker() {
		super();
		Font f = new Font(FONT_PATTERN, Font.BOLD, FONT_SIZE);
		this.setFont(f);
		this.getEditor().setDisabledTextColor(Color.BLACK);
		this.getEditor().setForeground(Color.BLACK.brighter());
	}
}
