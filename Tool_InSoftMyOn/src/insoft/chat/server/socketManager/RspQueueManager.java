package insoft.chat.server.socketManager;

import java.nio.channels.SocketChannel;
import java.util.Vector;

import insoft.chat.server.chatManager.ChatRoomManager;
import insoft.openmanager.message.ClientPacket;
import insoft.openmanager.message.Message;

public class RspQueueManager extends Thread{
	public static RspQueueManager rspQueueManager = new RspQueueManager();
	private Vector<ResponseInfo> responseQueue = new Vector<ResponseInfo>();
	public ServerSessionManager sessionMsg = ServerSessionManager.getInstance();

	private RspQueueManager(){
		start();
	}

	public static RspQueueManager getInstance() {
		return rspQueueManager;
	}

	public synchronized void addResponseMessage(ServerSocketConn socketConn, Message rspMsg) {
		ResponseInfo info = new ResponseInfo();
		info.conn = socketConn;
		info.rspMessage = rspMsg;

		responseQueue.add(info);
		this.notifyAll();
	}

	public void run(){
		Vector<ResponseInfo> rspQueue = new Vector<ResponseInfo>();
		ClientPacket clientPacket = new ClientPacket();
		while(true)
		{
			rspQueue.clear();

			if (responseQueue.size() > 0) {
				rspQueue.addAll(responseQueue);
				responseQueue.clear();
			}
			for (ResponseInfo rspInfo : rspQueue) {
				String messageName = rspInfo.rspMessage.getMessageName();
				if (messageName.equals("LOGIN") ||messageName.equals("GET_USERS") || messageName.equals("REG_USER")){
					
					SocketChannel channel = rspInfo.conn.getChannel();
					Message rspMsg = rspInfo.rspMessage;

					try {
						clientPacket.writeClientPacket(channel, rspMsg);
					} 
					catch (Exception e) {
						e.printStackTrace();
					}	
				}
				else{
					
					int chatRoomID = rspInfo.rspMessage.getInteger("chat_room_id");
					
					try
					{
						if (ChatRoomManager.hChatRoom.get(chatRoomID) != null){

							Vector<Message> vChat = ChatRoomManager.hChatRoom.get(chatRoomID) ;
							System.out.println(vChat.size());
							for (Message msg : vChat){

								String userId = msg.getString("user_id");

								Integer userSessionID = sessionMsg.userSessionID.get(userId);
								System.out.println("userSessionID:"+userSessionID);
								ServerSocketConn socketConn= sessionMsg.socketConn.get(userSessionID);

								try {
									clientPacket.writeClientPacket(socketConn.getChannel(),  rspInfo.rspMessage);
								} 
								catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					catch(Exception e){	
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(100);
				}
				catch(Exception e) {}
			}
		}
	}
}
