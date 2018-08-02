package com.pms.table.button.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.pms.entity.DisconnectReconnectDetails;
import com.pms.entity.User;
import com.pms.enums.util.ActiveStatus;
import com.pms.service.impl.UserServiceImpl;
import com.pms.util.PMSUtility;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class DisconnectedUsersTableButtonEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	

	protected JButton button;
	private String buttonText = "RECONNECT";
	private String label;
	private Integer id;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private boolean isPushed;

	public DisconnectedUsersTableButtonEditor(JCheckBox checkBox) {
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
		if (isPushed && id != null) {
			User user = userServiceImpl.readUserById(id);
			if(user.getActive().equals(ActiveStatus.N.name())){
				List<DisconnectReconnectDetails> discoRecoHistory = userServiceImpl.getDiscoRecoHistory(user.getId());
				DisconnectReconnectDetails disconnectReconnectDetails = discoRecoHistory.get(0);
				Date dodc = disconnectReconnectDetails.getDateOfDisconnection();
				Date today = new Date();
				/**LOGIC : RECONNECTION SHOULD BE ALWAYS GREATER THAN OR EQUAL TO DISCONNECTED DATE*/
				if( dodc!=null && !today.before(dodc)){					
					int successValue = userServiceImpl.disconnectReconnectUser(PMSUtility.convertToSqlDate(today), user, ActiveStatus.Y.name());
					if (successValue == 1) {
						JOptionPane.showMessageDialog(null, "RECONNECTED THE CONNECTION ON :" + today);
					}
					
				}
				else{
					int successValue = userServiceImpl.disconnectReconnectUser(PMSUtility.convertToSqlDate(dodc), user, ActiveStatus.Y.name());
					if (successValue == 1) {
						JOptionPane.showMessageDialog(null, "SINCE RECONECTION DATE BEFORE DODC ,SO RECONNECTED THE CONNECTION ON :" + dodc);
					}
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "USER HAS BEEN ALREADY RECONNECTED");
			}
			
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
