package insoft.chat.database;

import java.util.ArrayList;

public class DBPoolManager extends Thread {

	private static DBPoolManager poolManager = new DBPoolManager();
	private ArrayList<DBConnection> ltBusy = new ArrayList<DBConnection>();
	private ArrayList<DBConnection> ltIdle = new ArrayList<DBConnection>();
	private Object objLock = new Object();

	private DBPoolManager() {
		initialize();
		start();
	}

	public static DBPoolManager getInstance() {
		return poolManager;
	}

	private void initialize() {

		try {
			Class.forName(DBConfig.DRIVER);

			for (int i=0; i<DBConfig.CONN_CNT; i++) {
				ltIdle.add(new DBConnection());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized DBConnection getDBConnection() {

		DBConnection dbConn = null;

		if (ltIdle.size() > 0) {
			dbConn = ltIdle.remove(0);
			ltBusy.add(dbConn);
			return dbConn;
		}

		synchronized (objLock) {
			try {
				objLock.wait();
			} catch (InterruptedException e) {}
		}

		dbConn = ltIdle.remove(0);
		ltBusy.add(dbConn);
		return dbConn;

	}

	public void release(DBConnection dbConn) {
		ltBusy.remove(dbConn);
		ltIdle.add(dbConn);

		synchronized (objLock) {
			objLock.notifyAll();
		}
	}

	public void close() {
		synchronized (ltBusy) {
			for (DBConnection conn : ltBusy) {
				conn.close();
			}
		}

		synchronized (ltIdle) {
			for (DBConnection conn : ltIdle) {
				conn.close();
			}
		}
	}
	
	public void run() {
		
		while(true) {
			synchronized (ltIdle) {
				for (DBConnection dbConn : ltIdle) {
					dbConn.echo();
				}
			}
			
			try {
				Thread.sleep(DBConfig.TIMEOUT);
			} catch (InterruptedException e) {}
		}
	}
}
