package com.pms.util;

public interface DBConstants {

	/**DB TABLE CONSTANTS*/
	String ID ="ID";
	String YEAR ="YEAR";
	String MONTH = "MONTH";
	String FEES_INSERTION_DATE ="FEES_INSERTION_DATE";
	String FEES_PAID  ="FEES_PAID";
	String DISCOUNT   ="DISCOUNT";
	String MONTH_SEQUENCE ="MONTH_SEQUENCE";
	
	String KEY ="KEY";
	String KEY_VALUE ="KEY_VALUE";

	String USERNAME ="USERNAME";
	String PASSWORD ="PASSWORD";
	String USER_ALIAS ="USER_ALIAS";
	String USER_GROUP ="USER_GROUP";
	
	String USERID="USERID";
	String DODC="DODC";
	String DORC="DORC";
	
	String FROM_DATE ="FROM_DATE";
	String TO_DATE ="TO_DATE";
	String FEES ="FEES";
	
	String CUSTOMERNAME ="CUSTOMERNAME";
	String STREET ="STREET";
	String SECTOR ="SECTOR";
	String DOC   ="DOC";
	String MOBNUMBER="MOBNUMBER";
	String SETTOPBOX="SETTOPBOX";
	String ACTIVE="ACTIVE";
	String CONNECTIONCHARGE="CONNECTIONCHARGE";
	String CASNUMBER="CASNUMBER";
	String QRNO="QRNO";
	String BACK_DUES="BACK_DUES";
	
	/**SQL QUERY CONSTANTS*/
	String APP_PROPS_QUERY="SELECT KEY_VALUE FROM APPLICATION_PROPS WHERE KEY=?";
	String APP_USERS_QUERY="SELECT * FROM APPLICATION_USERS WHERE USERNAME=? AND PASSWORD=?";
	String DISCONNECT_QUERY="INSERT INTO DISCO_RECO_DETAILS VALUES(?,?,?,?)";
	String ID_QUERY="SELECT ID FROM DISCO_RECO_DETAILS WHERE USERID=? AND DORC IS NULL";
	String DISCONNECTED_DATE_QUERY="SELECT DODC FROM DISCO_RECO_DETAILS WHERE USERID=? AND DORC IS NULL ORDER BY DODC DESC";
	String DELETE_DISCO_RECO_QUERY="DELETE FROM DISCO_RECO_DETAILS WHERE USERID =?";
	String DISCO_RECO_HISTORY_QUERY="SELECT * FROM DISCO_RECO_DETAILS WHERE USERID=? ORDER BY DODC DESC";
	String INSERT_ALL_FEES_DETAILS_QUERY="INSERT INTO ALL_FEES_DETAILS VALUES(?,?,?,?,?,?,?)";
	String BACKDUES_AMOUNT_QUERY="SELECT SUM(BACK_DUES) AS BACKDUESSUM FROM USER";
	String READ_FEESDETAILS_QUERY="SELECT * FROM ALL_FEES_DETAILS WHERE ID=?";
	String DELETE_ALL_FEES_DETAILS_QUERY="DELETE FROM ALL_FEES_DETAILS WHERE ID =?";
	String IS_USER_PAID_FEES_QUERY="SELECT FEES_PAID FROM ALL_FEES_DETAILS WHERE ID= ? AND FEES_INSERTION_DATE IS NOT NULL AND MONTH=? AND YEAR=?";
	String BACKDUES_QUERY="SELECT BACK_DUES AS TOTALSUM FROM USER WHERE ID =?";
	String SUMOFFEES_RECEIVED_QUERY="SELECT SUM(FEES_PAID)+SUM(DISCOUNT) AS COLLECTED_AMOUNT FROM ALL_FEES_DETAILS WHERE ID=?";
	String READFEES_DETAILS_QUERY = "SELECT * FROM ALL_FEES_DETAILS WHERE ID=? AND MONTH=? AND YEAR=?";
	String UPDATE_BACKDUES_QUERY="UPDATE USER SET BACK_DUES=? WHERE ID=?";
	String DISCOUNT_THIS_MONTH_QUERY="SELECT SUM(DISCOUNT) AS DICOUNT FROM ALL_FEES_DETAILS WHERE YEAR= ? AND MONTH=?";
	String YEARLY_REVENUE_QUERY="SELECT SUM(FEES_PAID) AS FEES_PAID FROM ALL_FEES_DETAILS WHERE YEAR=? AND MONTH=?";
	String AMT_2B_COLLECTED_QUERY="SELECT  SUM(F.FEES) AS TOTALSUM FROM USER U , FEES_HISTORY F WHERE U.ACTIVE='Y' AND U.ID=F.ID AND F.TO_DATE IS NULL";
	String FEES_HIST_QUERY="SELECT * FROM FEES_HISTORY  WHERE ID= ? ORDER BY FROM_DATE ASC";
	String LIST_FEES_HIST_QUERY="SELECT EXTRACT(YEAR FROM FROM_DATE) as FROM_YEAR ,EXTRACT(MONTH FROM FROM_DATE) as FROM_MONTH,EXTRACT(YEAR FROM TO_DATE) as TO_YEAR,EXTRACT(MONTH FROM TO_DATE) as TO_MONTH,fees FROM FEES_HISTORY  WHERE ID= ? ORDER BY FROM_DATE ASC";
	String UPDATE_FEES_HIST_QUERY="UPDATE FEES_HISTORY SET FEES=? WHERE ID=? and TO_DATE IS NULL";
	String UPDATE_TODATE_QUERY="UPDATE FEES_HISTORY SET TO_DATE=? WHERE ID=? and TO_DATE IS NULL";
	String SAVE_FEES_HIST_QUERY="INSERT INTO FEES_HISTORY VALUES(?,?,?,?)";
	String DELETE_FEES_HIST_QUERY="DELETE FROM FEES_HISTORY WHERE ID =?";
	String LATEST_FEES_QUERY="SELECT * FROM FEES_HISTORY WHERE ID= ? AND TO_DATE IS NULL ORDER BY FROM_DATE";
	String SAVE_USER_QUERY="INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	String UPDATE_USER_QUERY="UPDATE USER SET CUSTOMERNAME=?,QRNO=?,STREET=?,SECTOR=?,CONNECTIONCHARGE=?,MOBNUMBER=?,SETTOPBOX=?,CASNUMBER=?, BACK_DUES=? WHERE ID= ?";
	String DELETE_USER_QUERY="DELETE FROM USER WHERE ID =?";
	String DISCONNECT_RECONNECT_USER_QUERY="UPDATE USER SET ACTIVE=?  WHERE ID= ?";
	String READUSERS_QUERY="SELECT * FROM USER ORDER BY QRNO ASC";
	String READUSERS_CONNECT_DATE_QUERY="SELECT * FROM USER WHERE ACTIVE='Y' ORDER BY QRNO ASC";
	String READ_USER_BYID_QUERY="SELECT * FROM USER WHERE ID= ?";
	String NEXT_VALUE_QUERY="CALL NEXT VALUE FOR PUBLIC.PMS_SEQUENCE";
	String MAX_VALUE_QUERY="SELECT MAX(ID) +1 AS ID FROM USER";
	String NEWCONNECTIONS_MONTHLY_QUERY="SELECT COUNT(*) AS NEWCONNECTION FROM USER WHERE UPPER(TO_CHAR(DOC,'MONTH'))=? AND TO_CHAR(DOC,'YYYY')=?";
	String REVENUE_NEWCONNECTIONS_MONTHLY_QUERY="SELECT SUM(CONNECTIONCHARGE) AS REVENUEFROMNEWCONNECTIONS FROM USER WHERE UPPER(TO_CHAR(DOC,'MONTH'))=? AND TO_CHAR(DOC,'YYYY')=?";
}
