package insoft.chat.server.taskmanager.task;

import java.util.Vector;

import insoft.chat.server.chatManager.ChatRoomManager;
import insoft.chat.server.taskmanager.ServerTask;
import insoft.openmanager.message.Message;

public class ChatExit extends ServerTask{
	ChatRoomManager chatManager = ChatRoomManager.getInstance();
	@Override
	public String getName() {
		
		return "EXIT";
	}

	@Override
	public ServerTask newInstance() {
		
		return new ChatExit();
	}

	@Override
	public Message execute() {
		String userID =  requestInfo.reqMessage.getString("user_id");
		int chatRoomID = requestInfo.reqMessage.getInteger("chat_room_id");
		
		Vector<Message> chatGuestList= chatManager.delRoomGuest(userID, chatRoomID);
		
		Message msg = new Message(getName());
		msg.setInteger("retrun_code", 1);
		msg.setString("return_msg","Ã¤ÆÃ¹æ out");
		msg.setVector("users_list", chatGuestList);
		
		return msg;
	}
}
