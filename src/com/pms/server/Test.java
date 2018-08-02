package com.pms.server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
//import java.util.Date;
import java.util.Random;

import com.pms.dao.JDBCConnection;
import com.pms.enums.util.ActiveStatus;
//import com.pms.enums.util.ActiveStatus;
//import com.pms.util.PMSUtility;
import com.pms.util.PMSUtility;

public class Test {
	
	public static void main(String args[]){
		System.setProperty("ashutosh", "kumar");
		//insertIntoFeesHistoryTable();
		System.out.println(System.getProperty("ashutosh"));
		
		Connection connection = JDBCConnection.getConnection();
		for(int i =2;i<1000;i++){
			
			String sql = "insert into USER values(?,?,?,?,?,?,?,?,?,?,?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
				preparedStatement.setInt(1, i);
				preparedStatement.setString(2, new BigInteger(130, new SecureRandom()).toString(32));
				preparedStatement.setString(3, "5");
				preparedStatement.setString(4, "5-C");
				preparedStatement.setDate(5, PMSUtility.convertToSqlDate(new Date()));
				preparedStatement.setString(6, String.valueOf(generateRandom(10)));
				preparedStatement.setString(7, String.valueOf(generateRandom(12)));
				preparedStatement.setString(8, ActiveStatus.Y.name());
				preparedStatement.setInt(9, Integer.valueOf(200));
				preparedStatement.setString(10, String.valueOf(generateRandom(9)));
				preparedStatement.setInt(11, i);
				preparedStatement.setInt(12, 0);
				preparedStatement.executeUpdate();
				System.out.println("SUCCESSFULLY INSERTED USER DATA INTO TABLE");
			} catch (SQLException e) {
				System.out.println("Db problem");
				e.printStackTrace();
			}
		}
		
		
/*		INSERT INTO USER
		( "ID", "CUSTOMERNAME", "STREET", "SECTOR", "DOC", "FEECODE", "MOBNUMBER", "SETTOPBOX", "ACTIVE", "FEE", "CONNECTIONCHARGE", "CASNUMBER", "QRNO", "BACK_DUES" )
		VALUES (4 , 'A', '6', '6',TO_DATE('13-04-2016','DD-MM-YYYY') , 'F', '666666666', '6', 'Y',6 , 6, '7',5 , 5)*/
	}
	
	public static long generateRandom(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}
	
	

	
	public static void insertIntoFeesHistoryTable() {
		Connection connection = JDBCConnection.getConnection();

		/** QUERY USER TABLE */
		String readSql = "SELECT ID, DOC,FEE FROM USER";
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(readSql)) {
			while (rs.next()) {
				String sql = "INSERT INTO FEES_HISTORY (ID,FROM_DATE,FEES) VALUES(?,?,?)";
				System.out.println("SQL EXECUTED "+sql);
				/** INSERTING INTO FEES_HISTORY TABLE */
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
					preparedStatement.setInt(1, rs.getInt("id"));
					preparedStatement.setDate(2, rs.getDate("doc"));
					preparedStatement.setInt(3, rs.getInt("fee"));
					preparedStatement.executeUpdate();
					System.out.println("SUCCESSFULLY INSERTED FEES_HISTORY DATA INTO TABLE");
				} catch (SQLException e) {
					System.out.println("Db problem  WHILE inserting into FEES_HISTORY");
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			System.out.println("Db problem  WHILE fetching from user");
			e.printStackTrace();
		}

	}

}
