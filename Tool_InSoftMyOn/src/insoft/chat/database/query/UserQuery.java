package insoft.chat.database.query;

import insoft.chat.database.DBConfig;
import insoft.chat.database.DBConnection;
import insoft.chat.database.DBPoolManager;
import insoft.openmanager.message.Message;

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
	
	public static Message exportUserList(Message memberMsg){
		DBConnection dbConn = dbPoolManager.getDBConnection();

		PreparedStatement pstmt = null;
		Message exportMsg = null ;
		
		try{
			exportMsg = new Message("REG_USER");
			
			String userID = memberMsg.getString("user_id");
			String userPw = memberMsg.getString("user_pw");
			String userName = memberMsg.getString("user_name");
			String email = memberMsg.getString("email");
			
			String query = " INSERT INTO USER (USER_ID, USER_PW, USER_NAME, EMAIL, REG_DATETIME) ";
			query += " VALUES ( ?, ?, ?, ?, now()) ";
			
			Connection conn = dbConn.getConnection();

			pstmt = conn.prepareStatement(query);
			pstmt.setQueryTimeout(DBConfig.TIMEOUT);
			
			pstmt.setString(1, userID);
			pstmt.setString(2, userPw);
			pstmt.setString(3, userName);
			pstmt.setString(4, email);
			
			pstmt.executeUpdate();
			
			exportMsg.setString("return_msg","USER table insert OK");
			exportMsg.setInteger("return_code", 1);
			
		}
		catch(Exception e){
			exportMsg.setString("return_msg","DB export Exception");
			exportMsg.setInteger("return_code", 0);
			e.printStackTrace();
		}
		
		
		return exportMsg ;
	}
}
