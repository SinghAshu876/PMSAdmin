package com.pms.document.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class NumberTextFieldDocumentFilter extends DocumentFilter {

	private int limit;
	


	public NumberTextFieldDocumentFilter(int limit) {
		super();
		this.limit = limit;
	}

	Pattern regEx = Pattern.compile("\\d+");

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		if (text.equals("")) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			Matcher matcher = regEx.matcher(text);
			if (!matcher.matches() || offset > limit - 1) {
				return;
			}
			super.replace(fb, offset, length, text, attrs);
		}
	}
}
