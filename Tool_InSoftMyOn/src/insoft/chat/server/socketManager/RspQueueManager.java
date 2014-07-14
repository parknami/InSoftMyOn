package insoft.chat.server.socketManager;

import java.nio.channels.SocketChannel;
import java.util.Vector;
import insoft.openmanager.message.ClientPacket;
import insoft.openmanager.message.Message;

public class RspQueueManager extends Thread{
	public static RspQueueManager rspQueueManager = new RspQueueManager();
	private Vector<ResponseInfo> responseQueue = new Vector<ResponseInfo>();
	public ServerSessionManager sessionMsg = null;

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
			//System.out.println("rspQueue size:"+rspQueue.size());
			for (ResponseInfo rspInfo : rspQueue) {
				System.out.println("rspQueue size:"+rspQueue.size());
				try
				{
					SocketChannel channel = rspInfo.conn.getChannel();
					Message rspMsg = rspInfo.rspMessage;
					clientPacket.writeClientPacket(channel, rspMsg);	
				} 
				catch(Exception e){	

				}
			}

			try {
				Thread.sleep(100);
			} catch(Exception e) {}
		}
	}
}
