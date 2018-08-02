package com.pms.server;

import org.apache.log4j.Logger;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;

import com.pms.util.ApplicationConstants;

public class DBServerStartup implements Runnable,ApplicationConstants {

	private Thread thread = new Thread(this,"DBServerStartup");

	private Server dbServer = null;

	private Logger LOG = Logger.getLogger(DBServerStartup.class);

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	@Override
	public void run() {
		try {
			LOG.info("*********************************PMS START************************************************");
			HsqlProperties hsqlProperties = new HsqlProperties();
			hsqlProperties.setProperty("server.database.0", System.getProperty(DB_LOC));
			hsqlProperties.setProperty("server.dbname.0", System.getProperty(DB_NAME));
			LOG.info("properties set");

			dbServer = new Server();
			dbServer.setPort(Integer.valueOf(System.getProperty(PORT)));
			dbServer.setProperties(hsqlProperties);
			
			dbServer.start();
			LOG.info("ServerStarted" + dbServer.getState());
		} catch (Exception e) {
			LOG.error("Server startup Failed" + dbServer.getState(), e);
		}

	}
	
	public  void dispose() {
		dbServer.signalCloseAllServerConnections();
		dbServer.shutdown();
		LOG.info("ServerStoped" + dbServer.getState());
		dbServer = null;
	}

	

}
