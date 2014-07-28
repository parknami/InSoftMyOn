package insoft.chat.server.chatManager;

import insoft.openmanager.message.Message;

import java.util.HashMap;
import java.util.Vector;

public class ChatRoomManager {
	public  static ChatRoomManager chatManager = new ChatRoomManager();
	public static HashMap <Integer , Vector> hChatRoom = new HashMap<Integer,Vector>();
	
	Vector <String> vGuestList =null;
	int chatRoomID = 0 ;
	
	private ChatRoomManager(){
		
	}
	
	public static ChatRoomManager getInstance(){
		return chatManager ;
	}
	
	public synchronized int addChatRoomId (Vector<Message> userList){ // 채팅방 만들기
		if (chatRoomID > Integer.MAX_VALUE)
			chatRoomID = 0 ;
		
		chatRoomID ++ ;
		
		hChatRoom.put(chatRoomID, userList);
		
		return chatRoomID ;
	}
	
	public void addGuest(Vector<Message> userList, int roomID) { // 채팅방에 친구 추가
		
		if (hChatRoom.get(roomID) != null){
			Vector<Message> vGuestList = hChatRoom.get(roomID);
			
			for (Message guestMsg : userList){
				vGuestList.add(guestMsg);
			}
			hChatRoom.put(roomID, vGuestList);
		}
		
		else {
			System.out.println("채팅방 아이디가 맞는게 없습니다.");
		}
	}

	public void delChatRoomId (int chatRoomId){
		hChatRoom.remove(chatRoomId);
	}
	
	public Vector<Message> delRoomGuest(String userID , int roomID){
		
		Vector<Message> vGuestList = hChatRoom.get(roomID);
		
		for (int i = 0 , size = vGuestList.size() ; i < size  ; i++ ){
			if (vGuestList.get(i).getString("user_id").equals(userID)){
				vGuestList.remove(i);
			}
		}
		
		if (vGuestList.size() == 0){
			delChatRoomId(roomID);
		}
		return vGuestList ;
	}
}
