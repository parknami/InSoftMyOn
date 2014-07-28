package insoft.chat.server.taskmanager;

import insoft.chat.server.taskmanager.task.*;

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
		ChattingMake chattinMake = new ChattingMake();
		AddRoomGuest addGuest = new AddRoomGuest();
		ChatExit  chatExit = new ChatExit();
		RegisterUser  regUser = new RegisterUser();
		
		mapTask.put(login.getName(), login);
		mapTask.put(getUser.getName(), getUser);
		mapTask.put(sendMsg.getName(), sendMsg);
		mapTask.put(chattinMake.getName(), chattinMake);
		mapTask.put(addGuest.getName(), addGuest);
		mapTask.put(chatExit.getName(), chatExit);
		mapTask.put(regUser.getName(), regUser);
	}
	
	public ServerTask getTask(String msgName) {
		return mapTask.get(msgName).newInstance();
	}
}
