package com.pms.table.button.renderer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SearchResultsTableButtonRenderer extends JButton implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private String buttonText = "DETAILS";

	public SearchResultsTableButtonRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText(buttonText);
		return this;
	}

}
