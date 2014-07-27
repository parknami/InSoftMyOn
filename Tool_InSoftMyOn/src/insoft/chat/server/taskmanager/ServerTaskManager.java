package insoft.chat.server.taskmanager;

import insoft.chat.server.taskmanager.task.GetUser;
import insoft.chat.server.taskmanager.task.Login;
import insoft.chat.server.taskmanager.task.SendMessage;

import java.util.HashMap;


public class ServerTaskManager {
	public  static  ServerTaskManager taskManager = new ServerTaskManager();
	
	private HashMap<String, ServerTask> mapTask = new HashMap<String, ServerTask>();
	
	private ServerTaskManager() {
		initalize();
	}
	
	public static ServerTaskManager getInstance() {
		return taskManager;
	}
	
	private void initalize() {
		Login login = new Login();
		GetUser getUser = new GetUser();
		SendMessage sendMsg = new SendMessage();
		
		mapTask.put(login.getName(), login);
		mapTask.put(getUser.getName(), getUser);
		mapTask.put(sendMsg.getName(), sendMsg);
	}
	
	public ServerTask getTask(String msgName) {
		return mapTask.get(msgName).newInstance();
	}
}
