package com.pms.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.pms.dao.ApplicationPropsDAO;

public class EmailUtil implements ApplicationConstants {

	private Logger LOG = Logger.getLogger(getClass());
	
	private ApplicationPropsDAO  appProp = new ApplicationPropsDAO();

	public String sendMailWithAttachment() {
		String msgStatus = "";
		LOG.info("sendMailWithAttachment ENTRY");
		String to = appProp.fetchProperty(EMAIL);
        String from = to;
		final String username = appProp.fetchProperty(USERNAME);
		final String password = appProp.fetchProperty(PASSWORD);

		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		//session.setDebug(true);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(appProp.fetchProperty(EMAIL_SUBJECT));

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(appProp.fetchProperty(EMAIL_BODY));

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			
			File backUpFile = new File(appProp.fetchProperty(EMAIL_ATTACHMENT_PATH));
			
			if(!backUpFile.exists()){
				LOG.info(WRONG_LOC_MSG);
				return WRONG_LOC_MSG;
			}
			
			if(isFileSizeMoreThan10Mb(backUpFile)){
				LOG.info(BIGFILESIZE_MSG);
				return BIGFILESIZE_MSG;
			}
			
			DataSource source = new FileDataSource(backUpFile);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(appProp.fetchProperty(FILENAME));

			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			msgStatus = BACKUP_SUCC_MSG;

			LOG.info("sendMailWithAttachment EXIT");

		} catch (MessagingException e) {
			msgStatus = e.getMessage();

		}
		return msgStatus;

	}
	
	private boolean isFileSizeMoreThan10Mb(File backUpFile){
		boolean isFileSizeMoreThan10Mb = false;
		long size = backUpFile.length();
		long fileSizeInMB =  size/(1024*1024);
		LOG.info("isFileSizeMoreThan10Mb " + fileSizeInMB);
		if(fileSizeInMB > 10){
			isFileSizeMoreThan10Mb = true;
		}
		return isFileSizeMoreThan10Mb;
	}
}