package com.pms.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pms.util.ApplicationConstants;

public class Migration implements ApplicationConstants {

	private static Connection connPMS = null;
	private static Connection connPMSBASTI = null;

	public static void main(String args[]) {

		/** getConnection for PMS DB */
		Connection connectionPMS = getConnectionPMS();
		/** getConnection for PMS DB BASTI */
		Connection connectionPMSBASTI = getConnectionPMSBASTI();

		List<Integer> idList = migrateUsers(connectionPMS, connectionPMSBASTI);
		migrateFeesHistory(idList, connectionPMS, connectionPMSBASTI);
		migrateDiscoRecoDetails(idList, connectionPMS, connectionPMSBASTI);
		migrateAllFeesDetails(idList, connectionPMS, connectionPMSBASTI);

		deleteUsers(idList, connectionPMS);

	}

	public static List<Integer> migrateUsers(Connection connectionPMS, Connection connectionPMSBASTI) {
		System.out.println("migrateUsers ENTRY");
		List<Integer> idList = new ArrayList<Integer>();
		/** QUERY USER TABLE */
		String readSql = "SELECT * FROM USER where street in ('12','13','14','15','16','40') ";
		try (Statement statement = connectionPMS.createStatement(); ResultSet rs = statement.executeQuery(readSql)) {
			while (rs.next()) {
				String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println("SQL EXECUTED " + sql);
				/** INSERTING INTO USER TABLE */
				try (PreparedStatement preparedStatement = connectionPMSBASTI.prepareStatement(sql)) {
					idList.add(rs.getInt("ID"));
					preparedStatement.setInt(1, rs.getInt("ID"));
					preparedStatement.setString(2, rs.getString("CUSTOMERNAME"));
					preparedStatement.setString(3, rs.getString("STREET"));
					preparedStatement.setString(4, rs.getString("SECTOR"));
					preparedStatement.setDate(5, rs.getDate("DOC"));
					preparedStatement.setString(6, rs.getString("MOBNUMBER"));
					preparedStatement.setString(7, rs.getString("SETTOPBOX"));
					preparedStatement.setString(8, rs.getString("ACTIVE"));
					preparedStatement.setInt(9, rs.getInt("CONNECTIONCHARGE"));
					preparedStatement.setString(10, rs.getString("CASNUMBER"));
					preparedStatement.setInt(11, rs.getInt("QRNO"));
					preparedStatement.setInt(12, rs.getInt("BACK_DUES"));
					preparedStatement.executeUpdate();
					System.out.println("SUCCESSFULLY INSERTED USER DATA INTO TABLE");
				} catch (SQLException e) {
					System.out.println("Db problem  WHILE inserting into FEES_HISTORY");
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			System.out.println("Db problem  WHILE fetching from user");
			e.printStackTrace();
		}
		System.out.println("migrateUsers EXIT");
		return idList;

	}

	private static void migrateAllFeesDetails(List<Integer> idList, Connection connectionPMS, Connection connectionPMSBASTI) {
		System.out.println("migrateAllFeesDetails ENTRY");
		for (Integer id : idList) {
			/** QUERY ALL_FEES_DETAILS TABLE */
			String readSql = "SELECT * FROM ALL_FEES_DETAILS where id ="+ id;
			try (Statement statement = connectionPMS.createStatement(); ResultSet rs = statement.executeQuery(readSql)) {
				while (rs.next()) {
					String sql = "INSERT INTO ALL_FEES_DETAILS VALUES(?,?,?,?,?,?,?)";
					System.out.println("SQL EXECUTED " + sql);
					/** INSERTING INTO ALL_FEES_DETAILS TABLE */
					try (PreparedStatement preparedStatement = connectionPMSBASTI.prepareStatement(sql)) {
						preparedStatement.setInt(1, rs.getInt("ID"));
						preparedStatement.setInt(2, rs.getInt("YEAR"));
						preparedStatement.setString(3, rs.getString("MONTH"));
						preparedStatement.setDate(4, rs.getDate("FEES_INSERTION_DATE"));
						preparedStatement.setInt(5, rs.getInt("FEES_PAID"));
						preparedStatement.setInt(6, rs.getInt("DISCOUNT"));
						preparedStatement.setInt(7, rs.getInt("MONTH_SEQUENCE"));
						preparedStatement.executeUpdate();
						System.out.println("SUCCESSFULLY INSERTED ALL_FEES_DETAILS DATA INTO TABLE");
					} catch (SQLException e) {
						System.out.println("Db problem  WHILE inserting into FEES_HISTORY");
						e.printStackTrace();
					}
				}

			} catch (SQLException e) {
				System.out.println("Db problem  WHILE fetching from ALL_FEES_DETAILS");
				e.printStackTrace();
			}

		}
		System.out.println("migrateAllFeesDetails EXIT");
	}

	private static void migrateDiscoRecoDetails(List<Integer> idList, Connection connectionPMS, Connection connectionPMSBASTI) {
		System.out.println("migrateDiscoRecoDetails ENTRY");
		for (Integer id : idList) {
			/** QUERY DISCO_RECO_DETAILS TABLE */
			String readSql = "SELECT * FROM DISCO_RECO_DETAILS where userid ="+id;
			try (Statement statement = connectionPMS.createStatement(); ResultSet rs = statement.executeQuery(readSql)) {
				while (rs.next()) {
					String sql = "INSERT INTO DISCO_RECO_DETAILS VALUES(?,?,?,?)";
					System.out.println("SQL EXECUTED " + sql);
					/** INSERTING INTO DISCO_RECO_DETAILS TABLE */
					try (PreparedStatement preparedStatement = connectionPMSBASTI.prepareStatement(sql)) {
						preparedStatement.setString(1, rs.getString("ID"));
						preparedStatement.setInt(2, rs.getInt("USERID"));
						preparedStatement.setDate(3, rs.getDate("DODC"));
						preparedStatement.setDate(4, rs.getDate("DORC"));
						preparedStatement.executeUpdate();
						System.out.println("SUCCESSFULLY INSERTED DISCO_RECO_DETAILS DATA INTO TABLE");
					} catch (SQLException e) {
						System.out.println("Db problem  WHILE inserting into DISCO_RECO_DETAILS");
						e.printStackTrace();
					}
				}

			} catch (SQLException e) {
				System.out.println("Db problem  WHILE fetching from DISCO_RECO_DETAILS");
				e.printStackTrace();
			}

		}
		System.out.println("migrateDiscoRecoDetails EXIT");
	}

	private static void migrateFeesHistory(List<Integer> idList, Connection connectionPMS, Connection connectionPMSBASTI) {
		System.out.println("migrateFeesHistory ENTRY");
		for (Integer id : idList) {
			/** QUERY FEES_HISTORY TABLE */
			String readSql = "SELECT * FROM FEES_HISTORY where id =" + id;
			try (Statement statement = connectionPMS.createStatement(); ResultSet rs = statement.executeQuery(readSql)) {
				while (rs.next()) {
					String sql = "INSERT INTO FEES_HISTORY VALUES(?,?,?,?)";
					System.out.println("SQL EXECUTED " + sql);
					/** INSERTING INTO FEES_HISTORY TABLE */
					try (PreparedStatement preparedStatement = connectionPMSBASTI.prepareStatement(sql)) {
						preparedStatement.setInt(1, rs.getInt("ID"));
						preparedStatement.setDate(2, rs.getDate("FROM_DATE"));
						preparedStatement.setDate(3, rs.getDate("TO_DATE"));
						preparedStatement.setInt(4, rs.getInt("FEES"));
						preparedStatement.executeUpdate();
						System.out.println("SUCCESSFULLY INSERTED FEES_HISTORY DATA INTO TABLE");
					} catch (SQLException e) {
						System.out.println("Db problem  WHILE inserting into FEES_HISTORY");
						e.printStackTrace();
					}
				}

			} catch (SQLException e) {
				System.out.println("Db problem  WHILE fetching from FEES_HISTORY");
				e.printStackTrace();
			}

		}
		System.out.println("migrateFeesHistory EXIT");
	}

	private static void deleteUsers(List<Integer> idList, Connection connectionPMS) {
		System.out.println("deleteUsers ENTRY");
		for (Integer id : idList) {
			String sql = "DELETE FROM USER WHERE ID =?";
			try (PreparedStatement preparedStatement = connectionPMS.prepareStatement(sql);) {
				preparedStatement.setInt(1, id);
				deleteDiscoRecoDetails(id,connectionPMS);
				deleteAllFeesDetails(id,connectionPMS);
				deleteFeesHistory(id,connectionPMS);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("problem cleaning up the Details in all tables" + e);

			}
			System.out.println("deleteUser EXIT");

		}
	}
	
	public static Integer deleteDiscoRecoDetails(Integer id, Connection connectionPMS) {
		System.out.println("deleteDiscoRecoDetails ENTRY");
		Integer returnValue = 0;
		String sql = "DELETE FROM DISCO_RECO_DETAILS WHERE USERID =?";
		System.out.println("delete sql :" + sql);
		try (PreparedStatement pStmnt = connectionPMS.prepareStatement(sql);) {
			pStmnt.setInt(1, id);
			returnValue = pStmnt.executeUpdate();
			System.out.println("SUCCESSFULLY DELETED DISCO_RECO_DETAILS ");
		} catch (Exception e) {
			System.out.println("DB Problem in delete"+ e);
		}
		System.out.println("deleteDiscoRecoDetails EXIT");
		return returnValue;
	}
	
	public static Integer deleteAllFeesDetails(Integer id, Connection connectionPMS) throws SQLException {
		System.out.println("deleteAllFeesDetails ENTRY");
		Integer returnValue = 0;
		String sql = "DELETE FROM ALL_FEES_DETAILS WHERE ID =?";
		System.out.println("delete sql :" + sql);
		try (PreparedStatement pStmnt = connectionPMS.prepareStatement(sql);) {
			pStmnt.setInt(1, id);
			returnValue = pStmnt.executeUpdate();
			System.out.println("SUCCESSFULLY DELETED DISCO_RECO_DETAILS ");
		} catch (Exception e) {
			System.out.println("DB Problem in delete"+ e);
		}
		System.out.println("deleteAllFeesDetails EXIT");
		return returnValue;
	}
	
	
	public static Integer deleteFeesHistory(Integer id, Connection connectionPMS) throws SQLException {
		System.out.println("deleteFeesHistory ENTRY");
		Integer returnValue = 0;
		String sql = "DELETE FROM FEES_HISTORY WHERE ID =?";
		System.out.println("delete sql :" + sql);
		try (PreparedStatement pStmnt = connectionPMS.prepareStatement(sql);) {
			pStmnt.setInt(1, id);
			returnValue = pStmnt.executeUpdate();
			System.out.println("SUCCESSFULLY DELETED FEES_HISTORY ");
		} catch (Exception e) {
			System.out.println("DB Problem in delete"+ e);
		}
		System.out.println("deleteFeesHistory EXIT");
		return returnValue;
	}

	public static Connection getConnectionPMS() {

		System.out.println("getConnectionPMS ENTRY");
		try {
			if (connPMS != null) {
				return connPMS;
			}
			Class.forName("org.hsqldb.jdbcDriver");
			System.out.println("Connecting to database..." + DB_URL);
			connPMS = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (Exception e) {
			System.out.println("DB PROBLEM in getConnectionPMS" + e);
		}
		System.out.println("getConnectionPMS EXIT");
		return connPMS;

	}

	public static Connection getConnectionPMSBASTI() {
		System.out.println("getConnectionPMSBASTI ENTRY");
		try {
			if (connPMSBASTI != null) {
				return connPMSBASTI;
			}
			Class.forName("org.hsqldb.jdbcDriver");
			//System.out.println("Connecting to database..." + DB_URL_BASTI);
			//connPMSBASTI = DriverManager.getConnection(DB_URL_BASTI, USER, PASS);

		} catch (Exception e) {
			System.out.println("DB PROBLEM in getConnectionPMSBASTI" + e);
		}
		System.out.println("getConnectionPMSBASTI EXIT");
		return connPMSBASTI;

	}

}
