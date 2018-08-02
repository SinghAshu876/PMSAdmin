package com.pms.table.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.pms.table.renderer.MultiLineCellRenderer;
import com.pms.util.ApplicationConstants;
import com.pms.util.Container;
import com.pms.util.PDFUtil;
import com.pms.util.PMSUtility;
//import com.pms.util.PrinterUtil;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class MonthlyDetailsPrintTableEditor implements ApplicationConstants{

	private Logger LOG = Logger.getLogger(getClass());
	private JTable table = null;
	private JFrame printFrame = null;

	protected void prepareTable(String name, AbstractTableModel model) {
		LOG.info("prepareTable ENTRY");
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		for (int i = 1; i < 13; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(200);
			table.getColumnModel().getColumn(i).setCellRenderer(new MultiLineCellRenderer());
		}

		table.setRowHeight(100);
		table.setFont(new Font("Arial", Font.BOLD, 25));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 25));

		printFrame = new JFrame(name);
		printFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		printFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		printFrame.setSize(1300, 700);
		printFrame.setVisible(true);
		printFrame.setLocationRelativeTo(null);

		JPanel jPanel = new JPanel();
		JButton printButton = new JButton(GENERATE_PDF);
		JButton backButton = new JButton(BACK);
		jPanel.add(printButton);
		jPanel.add(backButton);
		printButton.addActionListener(new PrintButtonHandler());
		backButton.addActionListener(new BackButtonHandler());
		printFrame.add(jPanel, BorderLayout.SOUTH);
		Container.frameContainer.put("PRINT-MONTHLY-REPORTS-FRAME", printFrame);
		LOG.info("prepareTable EXIT");
	}

	private class PrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//new PrinterUtil().print(table);	
			Date today = new Date();
			boolean success = new PDFUtil().generatePDFFile(PDF_MONTH_DET_HEADER,table ,MONTHLY_DETAILS_PDF ,PMSUtility.getYearString(today) ,PMSUtility.getMonth(today));
			if (success) {
				JOptionPane.showMessageDialog(null, SUCCESS_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, FAILURE_MESSAGE);
			}

		
		}

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("MONTHLY-PAID-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("PRINT-MONTHLY-REPORTS-FRAME")).setVisible(false);
		}

	}

}
