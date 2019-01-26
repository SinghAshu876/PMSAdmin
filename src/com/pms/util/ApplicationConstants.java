package com.pms.util;

/**
 * 
 * @author ashutosh.tct@gmail.com
 * 
 */
public interface ApplicationConstants {

	Integer COMPONENT_HEIGHT   		= 30;
	Integer WINDOW_HEIGHT      		= 500;
	Integer WINDOW_WIDTH       		= 700;
	Integer FONT_SIZE          		= 18;
	String EMPTY_STRING        		= "";
	String DASH                		= "_";
	String HYPHEN              		= "-";
	String MONTH_PATTERN       		= "MMMM";
	String YEAR_PATTERN        		= "YYYY";
	String DDMMYYYYHHMMSS      		= "dd_MM_YYYY_hh_mm_ss";
	String DATE_PICKER_PATTERN 		= "dd.MM.yyyy";
	String FONT_PATTERN        		= "MS UI Gothic";

	String DD_MM_YY            		= "dd/MM/yy";
	String FEES_PAID_DATE      		= "DATE";
	String PAID                		= "P-";
	String DISCOUNT            		= "D-";
	String DEV                      = "DEVELOPMENT";
	String ALKA                     = "ALKA";
	String BASTI                    = "BASTI";
	String DEV_BASTI                = "DEVELOPMENT-BASTI";
	String PAGE_NO                  = "PAGE NO -";
	String HD						= "HD";
	String SD						= "SD";
	
	String [] ENV                   = {EMPTY_STRING,ALKA,BASTI/*,DEV,DEV_BASTI*/};
 	String [] CHANNEL_TYPE          = {EMPTY_STRING,HD,SD};

	String PDF_SETTOP_BOX_HEADER [] 		= {"ID","NAME","CAFNO","SMART CARD NO","MOBILE", "QTR", "STR"};
	String PDF_SIMPLE_PRINT_HEADER []      	= {"NAME","CAFNO","SET TOP BOX","SEC","ID","STR","QTR","MOBILE","FEE","DUES","TOTAL","PAID","DUES"};
	String PDF_MONTH_DET_HEADER []          = {"YEAR","JAN","FEB","MAR","APR","MAY", "JUN", "JUL","AUG","SEP","OCT","NOV","DEC"};
	String PDF_YEARLY_REVENUE_HEADER []     = {"JAN","FEB","MAR","APR","MAY", "JUN", "JUL","AUG","SEP","OCT","NOV","DEC","YEARLY TOTAL"};
	String CSV_HEADER          		= "ID,NAME,CAFNO,SMART CARD NO,MOBILE NO, QTR NO, STREET";
	String SETTOP_BOX_CSV           = "set_top_box_%s.csv";
	String SETTOP_BOX_PDF           = "set_top_box_%s.pdf";
	String MONTHLY_DETAILS_PDF      = "monthly_details_%s.pdf";
	String YEARLY_REVENUE_PDF       = "yearly_revenue_%s.pdf";
	String SIMPLE_PRINT_PDF         = "simple_print_%s.pdf";
	String FILE_LOCATION        	= "FILE_LOCATION";
	String NEXT_LINE           		= "\n";
	String JAI_MATA_DI         		= "JAI MATA DI";
	String ALKA_VISHWA_DARSHAN 		= "ALKA VISHWA DARSHAN";
	String COMPANY_ADDRESS     		= "Sector 9 ,Bokaro Steel City -827009";
	String SUCCESS_MESSAGE     		= "SUCCESSFULLY GENERATED, PLEASE CHECK THE FILE ON DESKTOP";
	String FAILURE_MESSAGE     		= "COULD NOT GENERATE PDF FILE, CONTACT DEVELOPER!";
	String BACKUP_SUCC_MSG          = "SUCCESSFULLY BACK UP CREATED";
	String BIGFILESIZE_MSG          = "FILE SIZE GREATER THAN 10MB. CONTACT DEVELOPER";
	String WRONG_LOC_MSG            = "NOT ABLE TO LOCATE THE SCRIPT FILE. CONTACT DEVELOPER";
	String FILENAME                 = "FILENAME";
	String EMAIL_BODY               = "EMAIL_BODY";
	String EMAIL_SUBJECT            = "EMAIL_SUBJECT";
	String EMAIL_ATTACHMENT_PATH    = "EMAIL_ATTACHMENT_PATH";
	String EMAIL                    = "EMAIL";
	String USERNAME                 = "USERNAME";
	String PASSWORD                 = "PASSWORD";
	String SECOND_FACTOR_AUTH       = "2ND_FACTOR_AUTH";
	String NCF						= "NCF";
	String GST						= "GST";

	
	/**BUTTON TEXTS UI*/
	String BACK                     = "BACK"; 
	String GENERATE_PDF             = "GENERATE PDF"; 
	String GENERATE_CSV             = "GENERATE CSV";
	String ADD_BUTTON_LABEL 		= "ADD CHANNEL >>";
	String REMOVE_BUTTON_LABEL 		= "<< REMOVE CHANNEL";
	String CONFIRM_BUTTON_LABEL 	= "CONFIRM FEES FOR SELECTED CHANNELS ";
	String DEFAULT_SOURCE_CHOICE_LABEL = "AVAILABLE CHANNELS:";
	String DEFAULT_DEST_CHOICE_LABEL= "SELECTED CHANNELS:";
	String TOTAL_FEES_LABEL         = "TOTAL FEES FOR SELECTED CHANNELS:";

	String PORT		                = "PORT";
	String USER                		= "USER";
	String PASS                		= "PASS";
	String DB_NAME             		= "DB_NAME";
	String DB_URL              		= "DB_URL";
	String DB_LOC              		= "DB_LOC";
	
	String DB_DEV_PROP              ="db-dev.properties";
	String DB_DEV_BASTI_PROP 		="db-dev-basti.properties";
	String DB_ALKA_PROP				="db-alka.properties";
	String DB_BASTI_PROP 			="db-basti.properties";
	String RESOURCE_LOC             ="resources/";

}
