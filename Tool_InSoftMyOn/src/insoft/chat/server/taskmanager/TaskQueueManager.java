package insoft.chat.server.taskmanager;

import insoft.openmanager.message.Message;

import java.util.Vector;

public class TaskQueueManager {
	private static TaskQueueManager manager = new TaskQueueManager();
	//private ResponseManager rspManager = ResponseManager.getInstance();
	private Vector<Message> requestQueue = new Vector<Message>();
	
	public static TaskQueueManager getInstance() {
		return manager;
	}
}
