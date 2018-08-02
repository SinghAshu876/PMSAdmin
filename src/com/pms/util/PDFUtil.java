package com.pms.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.dao.ApplicationPropsDAO;

public class PDFUtil implements ApplicationConstants {
	
	private Logger LOG = Logger.getLogger(getClass());
	
	private ApplicationPropsDAO  appProp = new ApplicationPropsDAO();

	public boolean generatePDFFile(String[] headers, JTable jtable ,String fileName , String year, String month) {
		LOG.info("generatePDF ENTRY");
		boolean isSuccessfull = true;
		Document document = new Document(PageSize.A4.rotate());
		try {
			String pdfFile = String.format(appProp.fetchProperty(FILE_LOCATION).concat(fileName) ,new SimpleDateFormat(DDMMYYYYHHMMSS).format(System.currentTimeMillis()));
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
			HeaderFooterPageEvent headerFooterPageEvent = new HeaderFooterPageEvent(year,month);
			writer.setPageEvent(headerFooterPageEvent);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.COURIER, 25, Font.BOLD | Font.UNDERLINE);
			Font font3 = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
			Font font4 = new Font(Font.FontFamily.COURIER, 15, Font.ITALIC | Font.UNDERLINE);

			Paragraph jaiMataDi = new Paragraph(JAI_MATA_DI, font2);
			jaiMataDi.setAlignment(Element.ALIGN_CENTER);
			document.add(jaiMataDi);

			Paragraph companyName = new Paragraph(ALKA_VISHWA_DARSHAN, font1);
			companyName.setAlignment(Element.ALIGN_CENTER);
			document.add(companyName);

			Paragraph companyAddress = new Paragraph(COMPANY_ADDRESS, font1);
			companyAddress.setAlignment(Element.ALIGN_CENTER);
			document.add(companyAddress);
			
			Paragraph yearMonth = new Paragraph(month.concat(HYPHEN).concat(year), font4);
			yearMonth.setAlignment(Element.ALIGN_CENTER);
			document.add(yearMonth);

			PdfPTable table = new PdfPTable(headers.length);
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(20f); // Space before table
			table.setSpacingAfter(1f); // Space after table

			// Set Column widths
			float[] columnWidths = PMSUtility.getColumnWidths4PDF(headers, fileName);
			table.setWidths(columnWidths);

			for (int i = 0; i < headers.length; i++) {
				PdfPCell cell1 = new PdfPCell(new Paragraph(headers[i],font3));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);
			}

			for (int i = 0; i < jtable.getModel().getRowCount(); i++) {
				for (int columnIndex = 0; columnIndex < headers.length; columnIndex++) {
					Object value = jtable.getModel().getValueAt(i, columnIndex);
					PdfPCell cell1 = new PdfPCell(new Paragraph(value.toString()));
					cell1.setBorderColor(BaseColor.BLUE);
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell1);
				}
			}

			document.add(table);
			document.close();
			writer.close();
			LOG.info("generatePDF EXIT");
		} catch (Exception exception) {
			isSuccessfull = false;
			LOG.error(exception.getMessage());
			exception.printStackTrace();
		} 
		return isSuccessfull;
	}
	
	public class HeaderFooterPageEvent extends PdfPageEventHelper{
		
		private String year;
		private String month;
		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}


		
		HeaderFooterPageEvent(String year, String month){
			this.year = year;
			this.month= month;
		}
		
		public void onEndPage(PdfWriter writer, Document document){
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(getMonth().toUpperCase().concat(HYPHEN).concat(getYear())), 400, 30, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(PAGE_NO+document.getPageNumber()), 800, 30, 0);
			
			
		}
	}
}
