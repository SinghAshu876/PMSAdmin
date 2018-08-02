package com.pms.document.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class UpperCaseWithLimitDocumentFilter extends DocumentFilter {
 
	private int limit;

	public UpperCaseWithLimitDocumentFilter(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {

		fb.insertString(offset, text.toUpperCase(), attr);
	}

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		if (offset > limit - 1) {
			return;
		}
		fb.replace(offset, length, text.toUpperCase(), attrs);
	}
}
