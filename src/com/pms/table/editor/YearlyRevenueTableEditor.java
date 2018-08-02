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
public class YearlyRevenueTableEditor implements ApplicationConstants{

	private  Logger LOG = Logger.getLogger(getClass());
	private JTable table = null;
	private JFrame yearlyRevenueFrame = null;
	private Integer year = null;

	protected void prepareTable(String name, AbstractTableModel model, Integer year) {
		LOG.info("prepareTable ENTRY");
		this.year = year;
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		table.getColumnModel().getColumn(7).setPreferredWidth(200);
		table.getColumnModel().getColumn(8).setPreferredWidth(200);
		table.getColumnModel().getColumn(9).setPreferredWidth(200);
		table.getColumnModel().getColumn(10).setPreferredWidth(200);
		table.getColumnModel().getColumn(11).setPreferredWidth(200);
		table.getColumnModel().getColumn(12).setPreferredWidth(300);
		table.setRowHeight(40);
		table.setFont(new Font("Arial", Font.BOLD, 25));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 25));

		yearlyRevenueFrame = new JFrame(name);
		yearlyRevenueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		yearlyRevenueFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		yearlyRevenueFrame.setSize(1300, 700);
		yearlyRevenueFrame.setVisible(true);
		yearlyRevenueFrame.setLocationRelativeTo(null);

		JPanel jPanel = new JPanel();
		JButton printButton = new JButton(GENERATE_PDF);
		JButton backButton = new JButton(BACK);
		jPanel.add(printButton);
		jPanel.add(backButton);
		printButton.addActionListener(new PrintButtonHandler());
		backButton.addActionListener(new BackButtonHandler());
		yearlyRevenueFrame.add(jPanel, BorderLayout.SOUTH);
		Container.frameContainer.put("YEARLY-REVENUE-FRAME", yearlyRevenueFrame);
		LOG.info("prepareTable EXIT");
	}

	private class PrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//new PrinterUtil().print(table, year);
			Date today = new Date();
			boolean success = new PDFUtil().generatePDFFile(PDF_YEARLY_REVENUE_HEADER,table ,YEARLY_REVENUE_PDF ,String.valueOf(year) ,PMSUtility.getMonth(today));
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
			((JFrame) Container.frameContainer.get("COLLECTION-REPORT-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("YEARLY-REVENUE-FRAME")).setVisible(false);
		}

	}


}
