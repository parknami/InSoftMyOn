package insoft.chat.server.chatManager;

import insoft.openmanager.message.Message;

import java.util.HashMap;
import java.util.Vector;

public class Test {
	public static void main(String[] args) {
		HashMap<Integer , Vector> hmap = new HashMap<Integer , Vector>();

		Vector<Message> vMsg = new Vector<Message>();

		Message msg = new Message("TEST");
		msg.setString("user_id", "NMP");
		vMsg.add(msg);

		Message msg1 = new Message("TEST");
		msg1.setString("user_id", "DMK");
		vMsg.add(msg1);

		Message result = new Message("TEST");
		result.setVector("users_list", vMsg);

		Vector<Message> userList = result.getVector("users_list");
		
		hmap.put(1,userList);
		
		Vector<Message> _vMsg = new Vector<Message>();

		Message _msg = new Message("TEST");
		_msg.setString("user_id", "NMP111");
		_vMsg.add(_msg);

		Message _msg1 = new Message("TEST");
		_msg1.setString("user_id", "DMK111");
		_vMsg.add(_msg1);
		
		Message _result = new Message("TEST");
		_result.setVector("users_list", _vMsg);

		Vector<Message> _userList = _result.getVector("users_list");

		if (hmap.get(1) != null){

			Vector<Message> vGuestList = hmap.get(1);
			
			for (Message guestMsg : _userList){
				vGuestList.add(guestMsg);
			}
			//System.out.println(hmap.put(1, vGuestList));			
		}
		// remove 
		Vector<Message> vGuest = hmap.get(1);
		System.out.println(vGuest);
		for (int i = 0 ; i < vGuest.size() ; i++){
			if(vGuest.get(i).getString("user_id").equals("NMP")){
				vGuest.remove(i);
			}
		}
		System.out.println(vGuest);
	}
}
