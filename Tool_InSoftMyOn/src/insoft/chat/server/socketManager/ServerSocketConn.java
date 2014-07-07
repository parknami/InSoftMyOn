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
	private RspQueue rspQueue = RspQueue.getInstance();
	int sessionId = 0 ;
	
	public ServerSocketConn(SocketChannel ch) throws Exception {
		channel = ch;
		System.out.println(channel);
		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_READ );
	}
	
	public SocketChannel getChannel() {
		return channel;
	}
	
	public void run (){
		while (true){
			try{
				selector.select();

				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

				while(iter.hasNext()){
					SelectionKey key = iter.next();
					SocketChannel ch = (SocketChannel)key.channel();

					if(key.isValid() && key.isReadable())
					{
						try {
							ClientPacket clientPacket = new ClientPacket();
							Message readMsg = clientPacket.readClientPacket(channel);
							System.out.println("message:"+clientPacket.getMessage());
							
							TaskQueueManager taskQueue = TaskQueueManager.getInstance();
							
						} catch(Exception e) {
							
						}
					}
					iter.remove();
				}
			}
			catch (Exception e){
				
			}
		}
	}
	
	public void setAddSessionId(int sessionId){
		this.sessionId = sessionId ;
	}
	
	public int getSessionId (){
		return sessionId ;
	}
}
