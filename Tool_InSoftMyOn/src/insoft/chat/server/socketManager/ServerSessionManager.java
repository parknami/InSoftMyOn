package insoft.chat.server.socketManager;

import java.util.HashMap;

public class ServerSessionManager {
	private int sessionId = 0;
	private static ServerSessionManager sessionManager = new ServerSessionManager();
	HashMap <Integer , ServerSocketConn> socketConn = new HashMap<Integer , ServerSocketConn>();
	
	public ServerSessionManager() {

	}
	
	public static ServerSessionManager getInstance() {
		return sessionManager;
	}

/*	public void setAddSessionId(ServerSocketConn conn){
		if (conn != null){
			System.out.println("session manager conn:"+conn.getId());
			socketConn.put(sessionId(), conn);
		}
	}*/
	
	public int getSessionID(){
		if (sessionId > Integer.MAX_VALUE)
			sessionId = 0 ;
		
		return sessionId ++ ;
	}
	
	public void sessionDelete(int sessionId){
		synchronized (socketConn) {
			socketConn.remove(sessionId);
		}
	}
}
