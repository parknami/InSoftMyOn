package insoft.chat.server.socketManager;

import insoft.chat.server.taskmanager.TaskQueueManager;
import insoft.openmanager.message.ClientPacket;
import insoft.openmanager.message.Message;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class ServerSocketConn extends Thread{
	private SocketChannel channel = null;
	private Selector selector = null;
	private RspQueueManager rspQueue = RspQueueManager.getInstance();
	public ServerSessionManager sessionManager ;
	int sessionID = 0 ; 
	
	public ServerSocketConn(SocketChannel ch , int  sessionID) throws Exception {
		channel = ch;
		this.sessionID = sessionID ;
		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_READ);
	}

	public SocketChannel getChannel() {
		return channel;
	}

	public void run (){
		//TaskQueueManager queueManager = TaskQueueManager.getInstance();


		try{
			while (true){
				selector.select();

				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

				while(iter.hasNext()){
					SelectionKey key = iter.next();

					if(key.isValid() && key.isReadable())
					{

						try {
							ClientPacket clientPacket = new ClientPacket();
							Message readMsg = clientPacket.readClientPacket(channel);
							System.out.println("message:"+readMsg);

							Message writeMsg = readMsg.cloneMessage(readMsg.getMessageName());
							writeMsg.setInteger("session_id", sessionID);
							writeMsg.setString("return_msg","OK");
							writeMsg.setInteger("return_code", 1);

							System.out.println("writeMsg:"+writeMsg);
							clientPacket.write(channel, writeMsg);

							//queueManager.addReqQueue(readMsg);
						} catch(Exception e) {
							System.out.println("conn 로그아웃");
							System.out.println("session_id:"+sessionID);
							ServerSessionManager.getInstance().sessionDelete(sessionID);
							close();
							e.printStackTrace();
						}
					}
					iter.remove();
				}
			}
		}
		catch (Exception e){
			System.out.println("conn 마지막 로그아웃");
			close();
			e.printStackTrace();
		}

	}

	public void close() {
		try{
			channel.close();
			selector.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
