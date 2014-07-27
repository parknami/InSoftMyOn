package insoft.chat.server.taskmanager.task;

import insoft.chat.server.taskmanager.ServerTask;
import insoft.openmanager.message.Message;

public class SendMessage extends ServerTask {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SEND_MESSAGE";
	}

	@Override
	public ServerTask newInstance() {

		return new SendMessage();
	}

	@Override
	public Message execute() {
	    int socketSessionId = requestInfo.socketSessionld ;
	    
	    Message writeMsg = requestInfo.reqMessage.cloneMessage(requestInfo.reqMessage.getMessageName());
	    writeMsg.setInteger("session_id", socketSessionId);
	    writeMsg.setInteger("return_code",1);
	    writeMsg.setString("return_msg","");
		System.out.println("writeMsg:"+writeMsg);
	    return writeMsg;
	}
}
