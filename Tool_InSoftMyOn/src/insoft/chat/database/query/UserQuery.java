package insoft.chat.database.query;

import insoft.chat.database.DBConfig;
import insoft.chat.database.DBConnection;
import insoft.chat.database.DBPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserQuery {

	private static DBPoolManager dbPoolManager = DBPoolManager.getInstance();
	
	public static void getUserList() {
		
		DBConnection dbConn = dbPoolManager.getDBConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			String query = "";
			Connection conn = dbConn.getConnection();
			
			pstmt = conn.prepareStatement(query);
			pstmt.setQueryTimeout(DBConfig.TIMEOUT);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.close(pstmt, rs);
			dbPoolManager.release(dbConn);
		}
		
	}
}
