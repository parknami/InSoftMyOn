package insoft.chat.server.taskmanager.task;

import java.util.Vector;

import insoft.chat.server.taskmanager.ServerTask;
import insoft.chat.server.chatManager.*;
import insoft.openmanager.message.Message;

public class AddRoomGuest extends ServerTask {
	ChatRoomManager chatManager = ChatRoomManager.getInstance();
	@Override
	public String getName() {

		return "ADD_ROOM_GUEST ";
	}

	@Override
	public ServerTask newInstance() {

		return new AddRoomGuest();
	}

	@Override
	public Message execute() {
		
		Vector<Message> userList = requestInfo.reqMessage.getVector("users_list");	
		int chatRoomID = requestInfo.reqMessage.getInteger("chat_room_id");
		
		chatManager.addGuest(userList, chatRoomID);
	 	    
	    Message writeMsg = requestInfo.reqMessage.cloneMessage(requestInfo.reqMessage.getMessageName());
	    writeMsg.setInteger("chat_room_id", chatRoomID);
	    writeMsg.setInteger("return_code",1);
	    writeMsg.setString("return_msg","");

	    return writeMsg;
	}
}
