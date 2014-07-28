package insoft.chat.server.taskmanager.task;

import java.util.Vector;

import insoft.chat.server.taskmanager.ServerTask;
import insoft.chat.server.chatManager.*;
import insoft.openmanager.message.Message;

public class ChattingMake extends ServerTask {
	ChatRoomManager chatManager = ChatRoomManager.getInstance();
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CHATTING";
	}

	@Override
	public ServerTask newInstance() {

		return new ChattingMake();
	}

	@Override
	public Message execute() {
		Vector<Message> userList = requestInfo.reqMessage.getVector("users_list");
		
		int chatRoomID = chatManager.addChatRoomId(userList);
	 	    
	    Message writeMsg = requestInfo.reqMessage.cloneMessage(requestInfo.reqMessage.getMessageName());
	    writeMsg.setInteger("chat_room_id", chatRoomID);
	    writeMsg.setInteger("session_id",requestInfo.socketSessionld);
	    writeMsg.setInteger("return_code",1);
	    writeMsg.setString("return_msg","");

	    return writeMsg;
	}
}
