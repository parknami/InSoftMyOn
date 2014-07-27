package insoft.chat.server.taskmanager.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import insoft.chat.server.socketManager.ServerSessionManager;
import insoft.chat.server.taskmanager.ServerTask;
import insoft.openmanager.message.Message;

public class GetUser extends ServerTask{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_USERS";
	}

	@Override
	public ServerTask newInstance() {
		// TODO Auto-generated method stub
		return new GetUser();
	}

	@Override
	public Message execute() {
		String RequestorId = requestInfo.reqMessage.getString("user_id");
		Vector <String> vUserList = new Vector<String>();
		try{
			HashMap<String, Integer> hUserId = ServerSessionManager.getInstance().getUserId();
			Iterator<String> iterator = hUserId.keySet().iterator();

			while (iterator.hasNext()){
				String userId  = iterator.next();
				if (!userId.equals(RequestorId))
					vUserList.add(userId);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Message writeMsg = new Message("GET_USERS");
		writeMsg.setVector("login_user_list", vUserList);
		writeMsg.setInteger("session_id",requestInfo.socketSessionld);
		writeMsg.setInteger("return_code",1);
		writeMsg.setString("retrun_msg","");

		return writeMsg ;
	}
}
