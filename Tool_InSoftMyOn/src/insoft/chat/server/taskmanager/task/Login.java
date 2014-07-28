package insoft.chat.server.taskmanager.task;

import insoft.chat.server.socketManager.ServerSessionManager;
import insoft.chat.server.taskmanager.ServerTask;
import insoft.openmanager.message.Message;

public class Login extends ServerTask{
	ServerSessionManager sessionManager = ServerSessionManager.getInstance() ;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LOGIN";
	}

	@Override
	public ServerTask newInstance() {
		// TODO Auto-generated method stub
		return new Login();
	}

	@Override
	public Message execute() {
		//성공이 되면 실행
		int socketSessionId = requestInfo.socketSessionld ;
		String userId =  requestInfo.reqMessage.getString("user_id");
		sessionManager.setUserId(userId,socketSessionId);
		
		Message writeMsg = requestInfo.reqMessage.cloneMessage(requestInfo.reqMessage.getMessageName());
		writeMsg.setInteger("session_id", socketSessionId);
		writeMsg.setInteger("return_code", 1);
		writeMsg.setString("return_msg", "");

		return  writeMsg ;
	}
}
