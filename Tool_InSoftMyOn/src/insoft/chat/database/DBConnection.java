package insoft.chat.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	public DBConnection() {
		initilaize();
	}
	
	private void initilaize() {
		try {
			conn = DriverManager.getConnection(DBConfig.URL, DBConfig.ID, DBConfig.PW);
			pstmt = conn.prepareStatement(DBConfig.ECHO_QUERY);
			pstmt.setQueryTimeout(DBConfig.TIMEOUT);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {}
	}
	
	public void echo() {
		
		try {
			pstmt.executeQuery();
		} catch(Exception e) {
			e.printStackTrace();
			close();
			initilaize();
		}
		
	}
	
	public void close(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {}
		
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {}
	}
}
