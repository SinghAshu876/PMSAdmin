--SEQUENCES
CREATE SEQUENCE PUBLIC.PMS_SEQUENCE AS INTEGER START WITH 1;
--23/07/2018--
CREATE SEQUENCE PUBLIC.PMS_APPUSERS_SEQUENCE AS INTEGER START WITH 2;
--07/01/2019--
CREATE SEQUENCE PUBLIC.PMS_CHANNEL_DTLS_SEQUENCE AS INTEGER START WITH 1;
--TABLES
CREATE MEMORY TABLE PUBLIC.USER(ID INTEGER PRIMARY KEY,CUSTOMERNAME VARCHAR(200),STREET VARCHAR(200),SECTOR VARCHAR(200),DOC DATE,MOBNUMBER VARCHAR(200),SETTOPBOX VARCHAR(200),ACTIVE VARCHAR(1),CONNECTIONCHARGE INTEGER,CASNUMBER VARCHAR(20),QRNO INTEGER,BACK_DUES INTEGER);
--07/01/2019--
CREATE MEMORY TABLE PUBLIC.USER_ARCHIVE(ID INTEGER PRIMARY KEY,CUSTOMERNAME VARCHAR(200),STREET VARCHAR(200),SECTOR VARCHAR(200),DOC DATE,MOBNUMBER VARCHAR(200),SETTOPBOX VARCHAR(200),CASNUMBER VARCHAR(20),QRNO INTEGER);
CREATE MEMORY TABLE PUBLIC.DISCO_RECO_DETAILS(ID VARCHAR(200) PRIMARY KEY,USERID INTEGER,DODC DATE,DORC DATE,CONSTRAINT FK_DISCO_RECO_USER FOREIGN KEY(USERID) REFERENCES PUBLIC.USER(ID));
CREATE MEMORY TABLE PUBLIC.ALL_FEES_DETAILS(ID INTEGER,YEAR INTEGER,MONTH VARCHAR(200),FEES_INSERTION_DATE DATE,FEES_PAID INTEGER,DISCOUNT INTEGER,MONTH_SEQUENCE INTEGER,PRIMARY KEY(ID,YEAR, MONTH),CONSTRAINT FK_ALL_FEES_DETAILS_USER FOREIGN KEY(ID) REFERENCES PUBLIC.USER(ID));
CREATE MEMORY TABLE PUBLIC.FEES_HISTORY(ID INTEGER,FROM_DATE DATE,TO_DATE DATE, FEES INTEGER,CONSTRAINT FK_FEES_HISTORY_USER FOREIGN KEY(ID) REFERENCES PUBLIC.USER(ID));
--27/01/2019--
CREATE MEMORY TABLE PUBLIC.CHANNEL_DETAILS(CHANNEL_ID INTEGER PRIMARY KEY,CHANNEL_NAME VARCHAR(200),CHANNEL_PRICE DECIMAL(5,2) , CHANNEL_TYPE VARCHAR(2));
--07/01/2019--
CREATE MEMORY TABLE PUBLIC.USER_CHANNEL_DETAILS(ID VARCHAR(200) PRIMARY KEY, USERID INTEGER,CHANNELID INTEGER,CONSTRAINT FK_USER_CHANNEL_DETAILS_USERID FOREIGN KEY(USERID) REFERENCES PUBLIC.USER(ID),CONSTRAINT FK_USER_CHANNEL_DETAILS_CHANNELID FOREIGN KEY(CHANNELID) REFERENCES PUBLIC.PUBLIC.CHANNEL_DETAILS(CHANNEL_ID));
CREATE MEMORY TABLE PUBLIC.APPLICATION_USERS(ID INTEGER,USERNAME VARCHAR(200),PASSWORD VARCHAR(200), USER_ALIAS VARCHAR(200), USER_GROUP VARCHAR(200),PRIMARY KEY(ID));
CREATE MEMORY TABLE PUBLIC.APPLICATION_PROPS(KEY VARCHAR(200),KEY_VALUE VARCHAR(200));


--MASTER DATA
INSERT INTO PUBLIC.APPLICATION_USERS VALUES (1,'1','ALKA','MR R. K .SINGH','ADMIN');
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('FILENAME', 'backup.script');
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('EMAIL', 'ramkrsingh5@gmail.com');
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('USERNAME', 'ramkrsingh5');  
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('PASSWORD', '*'); 
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('EMAIL_ATTACHMENT_PATH', '**'); 
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('FILE_LOCATION', 'C:\\Users\\Alka\\Desktop\\');
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('EMAIL_SUBJECT', 'BACK UP'); 
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('EMAIL_BODY', 'BACK UP FILE FOR PMS');  
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('2ND_FACTOR_AUTH', '1'); 
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('GST', '18'); 
INSERT INTO PUBLIC.APPLICATION_PROPS VALUES ('NCF', '130'); 


--INDEX ON USER TABLE
CREATE UNIQUE INDEX PUBLIC.ID_USER ON PUBLIC.USER (ID);
CREATE INDEX PUBLIC.CUSTOMERNAME_USER ON PUBLIC.USER (CUSTOMERNAME);
CREATE INDEX PUBLIC.STREET_USER ON PUBLIC.USER (STREET);
CREATE INDEX PUBLIC.SECTOR_USER ON PUBLIC.USER (SECTOR);
CREATE INDEX PUBLIC.MOBNUMBER_USER ON PUBLIC.USER (MOBNUMBER);
CREATE INDEX PUBLIC.SETTOPBOX_USER ON PUBLIC.USER (SETTOPBOX);
CREATE INDEX PUBLIC.CASNUMBER_USER ON PUBLIC.USER (CASNUMBER);
CREATE INDEX PUBLIC.QRNO_USER ON PUBLIC.USER (QRNO);

--INDEX ON DISCO_RECO_DETAILS TABLE
CREATE UNIQUE INDEX PUBLIC.ID_DISCO_RECO_DETAILS ON PUBLIC.DISCO_RECO_DETAILS (ID);
CREATE INDEX PUBLIC.USERID_DISCO_RECO_DETAILS ON PUBLIC.DISCO_RECO_DETAILS (USERID);

--INDEX ON ALL_FEES_DETAILS TABLE
CREATE INDEX PUBLIC.ID_ALL_FEES_DETAILS ON PUBLIC.ALL_FEES_DETAILS (ID);

--INDEX ON FEES_HISTORY TABLE
CREATE INDEX PUBLIC.ID_FEES_HISTORY ON PUBLIC.FEES_HISTORY (ID);

--INDEX ON APPLICATION_PROPS TABLE
CREATE INDEX PUBLIC.KEY_APPLICATION_PROPS ON PUBLIC.APPLICATION_PROPS (KEY);
CREATE INDEX PUBLIC.KEY_VALUE_APPLICATION_PROPS ON PUBLIC.APPLICATION_PROPS (KEY_VALUE);







