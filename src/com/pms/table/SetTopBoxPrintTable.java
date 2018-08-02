package com.pms.table;

import java.util.List;

import org.apache.log4j.Logger;

import com.pms.entity.User;
import com.pms.service.impl.UserServiceImpl;
import com.pms.table.editor.SetTopBoxPrintTableEditor;
import com.pms.table.model.SetTopBoxTableModel;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SetTopBoxPrintTable extends SetTopBoxPrintTableEditor {
	
	private  Logger LOG = Logger.getLogger(getClass());

	public void showInTabularForm() {
		 LOG.info("showInTabularForm ENTRY");
		List<User> userList = new UserServiceImpl().readUsers();
		SetTopBoxTableModel model = new SetTopBoxTableModel(userList);
		super.prepareTable("SET TOP BOX PRINT", model);
		 LOG.info("showInTabularForm EXIT");

	}
}
