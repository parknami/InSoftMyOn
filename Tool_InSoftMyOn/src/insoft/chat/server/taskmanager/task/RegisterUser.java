package insoft.chat.server.taskmanager.task;

import insoft.chat.database.query.UserQuery;
import insoft.chat.server.socketManager.ServerSessionManager;
import insoft.chat.server.taskmanager.ServerTask;
import insoft.openmanager.message.Message;

public class RegisterUser extends ServerTask{
	ServerSessionManager sessionManager = ServerSessionManager.getInstance() ;
	UserQuery userQur = null ;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "REG_USER";
	}

	@Override
	public ServerTask newInstance() {
		// TODO Auto-generated method stub
		return new RegisterUser();
	}

	@Override
	public Message execute() {
		Message retrurnMsg = userQur.exportUserList(requestInfo.reqMessage);

		Message writeMsg = requestInfo.reqMessage.cloneMessage(requestInfo.reqMessage.getMessageName());
		writeMsg.setInteger("session_id",requestInfo.socketSessionld);
		writeMsg.setString("retrun_msg",retrurnMsg.getString("return_msg"));
		writeMsg.setInteger("return_code", retrurnMsg.getInteger("return_code"));

		return  writeMsg ;
	}
}
