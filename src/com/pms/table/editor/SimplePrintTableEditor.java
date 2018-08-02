package com.pms.table.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
//import com.pms.util.PrinterUtil;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public class SimplePrintTableEditor implements ApplicationConstants{

	private  Logger LOG = Logger.getLogger(getClass());
	private JTable table = null;
	private JFrame printFrame = null;
	private Integer year = null;
	private String month = null;

	protected void prepareTable(String name, AbstractTableModel model, Integer year, String month) {
		LOG.info("prepareTable ENTRY");
		this.year = year;
		this.month = month;
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(400);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(55);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(55);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(180);
		table.getColumnModel().getColumn(8).setPreferredWidth(70);
		table.getColumnModel().getColumn(9).setPreferredWidth(90);
		table.getColumnModel().getColumn(10).setPreferredWidth(90);
		table.getColumnModel().getColumn(11).setPreferredWidth(90);
		table.getColumnModel().getColumn(12).setPreferredWidth(90);
		table.setRowHeight(40);
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
		Container.frameContainer.put("PRINT-RESULTS-FRAME", printFrame);
		LOG.info("prepareTable EXIT");
	}

	private class PrintButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean success = new PDFUtil().generatePDFFile(PDF_SIMPLE_PRINT_HEADER, table, SIMPLE_PRINT_PDF, String.valueOf(year), month);
			if (success) {
				JOptionPane.showMessageDialog(null, SUCCESS_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, FAILURE_MESSAGE);
			}
			//new PrinterUtil().print(table, year, month);
		}

	}

	private class BackButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			((JFrame) Container.frameContainer.get("PRINT-MENU-FRAME")).setVisible(true);
			((JFrame) Container.frameContainer.get("PRINT-RESULTS-FRAME")).setVisible(false);
		}

	}
	
}
