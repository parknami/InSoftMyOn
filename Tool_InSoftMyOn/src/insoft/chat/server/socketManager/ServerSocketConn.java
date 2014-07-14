package insoft.chat.server.socketManager;

import insoft.chat.server.taskmanager.TaskQueueManager;
import insoft.openmanager.message.ClientPacket;
import insoft.openmanager.message.Message;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerSocketConn extends Thread{
	private SocketChannel channel = null;
	private Selector selector = null;
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
	
	public void write(Message writeMsg) throws Exception  {

		ClientPacket clientPacket = new ClientPacket();
		clientPacket.writeClientPacket(channel, writeMsg);
	}

	public void run (){
		TaskQueueManager queueManager = TaskQueueManager.getInstance();
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
							System.out.println("Read Client message:"+readMsg);
							queueManager.addReqQueue(readMsg , sessionID);
							

				/*			Message writeMsg = readMsg.cloneMessage(readMsg.getMessageName());
							writeMsg.setInteger("session_id", sessionID);
							writeMsg.setString("return_msg","OK");
							writeMsg.setInteger("return_code", 1);

							System.out.println("writeMsg:"+writeMsg);
							clientPacket.write(channel, writeMsg);
*/
							//queueManager.addReqQueue(readMsg);
						} catch(Exception e) {
							System.out.println("conn ·Î±×¾Æ¿ô");
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
