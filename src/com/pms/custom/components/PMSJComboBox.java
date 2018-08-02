package com.pms.custom.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;

import com.pms.util.ApplicationConstants;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class PMSJComboBox<T extends Object> extends JComboBox<String> implements ApplicationConstants {

	private static final long serialVersionUID = 3316583740727435358L;
	
	public PMSJComboBox() {		
		super();
		Font f = new Font(FONT_PATTERN, Font.BOLD, FONT_SIZE);
		this.setFont(f);
		this.setForeground(Color.BLACK.brighter());

	}

}
