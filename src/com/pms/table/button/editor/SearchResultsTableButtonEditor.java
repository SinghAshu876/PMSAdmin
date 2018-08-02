package com.pms.table.button.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.pms.forms.UpdateEntryForm;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SearchResultsTableButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;

	protected JButton button;
	private String buttonText = "DETAILS";
	private String label;
	private Integer id;

	private boolean isPushed;

	public SearchResultsTableButtonEditor(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
			id = (Integer) table.getValueAt(row, 0);

		}
		label = buttonText;
		button.setText(label);
		isPushed = true;
		return button;
	}

	public Object getCellEditorValue() {
		if (isPushed && id !=null) {
			UpdateEntryForm updateEntryForm = new UpdateEntryForm(id);
			updateEntryForm.init("SEARCH-RESULTS-FRAME");
		}
		isPushed = false;
		return new String(label);
	}

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
