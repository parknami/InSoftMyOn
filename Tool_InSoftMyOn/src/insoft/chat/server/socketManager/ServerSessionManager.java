package insoft.chat.server.socketManager;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class ServerSessionManager {
	private int session = 0;
	private static ServerSessionManager sessionManager = new ServerSessionManager();
	private static HashMap <Integer , ServerSocketConn> socketConn  = new HashMap<Integer , ServerSocketConn>();
	private static HashMap <Integer , SocketChannel> scChannel  = new HashMap<Integer , SocketChannel>();
	private ServerSessionManager() {}
	
	public static ServerSessionManager getInstance() {
		return sessionManager;
	}
	
	public synchronized int sessionId(){
		if (session > Integer.MAX_VALUE)
			session = 0 ;
		
		return session ++ ;
	}
	
	public void setAddSessionId(ServerSocketConn conn){
		if (conn != null){
			socketConn.put(sessionId(), conn);
		}
	}

	public void setAddSessionId(SocketChannel ch) {
		if (ch != null){
			scChannel.put(sessionId(), ch);
		}
		System.out.println("session size :"+ scChannel.size());
	}
	
	
}
