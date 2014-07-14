package insoft.chat.server.socketManager;

import java.util.HashMap;

public class ServerSessionManager {
	private int sessionId = 0;
	private static ServerSessionManager sessionManager = new ServerSessionManager();
	HashMap <Integer , ServerSocketConn> socketConn = new HashMap<Integer , ServerSocketConn>();
	HashMap <String , Integer> userSessionID = new HashMap<String , Integer>();

	public ServerSessionManager() {

	}

	public static ServerSessionManager getInstance() {
		return sessionManager;
	}

	public void setSocket(ServerSocketConn conn){
		if (conn != null){
			socketConn.put(sessionId, conn);
		}
	}
	
	public ServerSocketConn getSocketConn(int socketSesseinId){
		//socketConn.get(socketSesseinId);
		return socketConn.get(socketSesseinId) ;
	}

	public int getSessionID(){
		if (sessionId > Integer.MAX_VALUE)
			sessionId = 0 ;

		sessionId ++;

		return sessionId ;
	}

	public void sessionDelete(int sessionId){
		synchronized (socketConn) {
			socketConn.remove(sessionId);
		}
	}
	
	public void setUserId (String userId , int socketSessionId){
		userSessionID.put(userId, socketSessionId);
	}
	
	public HashMap<String , Integer> getUserId(){
		return userSessionID;
	}
}
