package insoft.chat.database;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class DBConfig {

	public static String HOME = System.getProperty("HOME", "..");
	public static String PROPERTIES = "db.properties";
	public static String DRIVER = "";
	public static String URL = "";
	public static String ID = "";
	public static String PW = "";
	public static int TIMEOUT = 10 * 1000;
	public static int CONN_CNT = 10;
	public static String ECHO_QUERY = "";
	
	static {
		
		Properties dbProp = new Properties();
		
		try {
			dbProp.load(new FileInputStream(HOME + File.separator + "config" + File.separator + PROPERTIES));
			
			DRIVER = dbProp.getProperty("DRIVER");
			URL = dbProp.getProperty("URL");
			ID = dbProp.getProperty("ID");
			PW = dbProp.getProperty("PW");
			ECHO_QUERY = dbProp.getProperty("ECHO_QUERY");
			TIMEOUT = Integer.parseInt(dbProp.getProperty("TIMEOUT", "10000"));
			CONN_CNT = Integer.parseInt(dbProp.getProperty("CONN_CNT", "10"));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			dbProp.clear();
		}
		
	}
	
}
