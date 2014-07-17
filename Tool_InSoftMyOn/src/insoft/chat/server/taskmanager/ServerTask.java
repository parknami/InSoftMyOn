package insoft.chat.server.taskmanager;

import insoft.chat.server.socketManager.RequestInfo;
import insoft.chat.server.socketManager.RspQueueManager;
import insoft.chat.server.socketManager.ServerSessionManager;
import insoft.openmanager.message.Message;

public abstract class ServerTask  implements Runnable {
	protected RequestInfo requestInfo = null;
	private RspQueueManager rspManager = RspQueueManager.getInstance();
	private ServerSessionManager sessioManager = ServerSessionManager.getInstance();
	
	public void setRequestInfo(RequestInfo info) {
		this.requestInfo = info;
	}
	
	public RequestInfo getRequestInfo() {
		 return this.requestInfo;
	}

	protected Message msgTask;

	public void setTask(Message msgTask) {
		this.msgTask = msgTask;
	}

	public void run() {
		Message rspMsg = null;
		try{
			rspMsg  = execute();
		}
		catch(Exception e){
			e.printStackTrace();
			rspMsg = new Message(requestInfo.reqMessage.getMessageName());
			rspMsg.setString("return_msg", e.toString());
			rspMsg.setInteger("return_code", 0);
		}

		rspManager.addResponseMessage(sessioManager.getSocketConn(rspMsg.getInteger("session_id")), rspMsg);
	}
	
	abstract public String getName();
	abstract public ServerTask newInstance();
	abstract public Message execute();
}
