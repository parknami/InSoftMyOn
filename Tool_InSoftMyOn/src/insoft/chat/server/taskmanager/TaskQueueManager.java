package insoft.chat.server.taskmanager;

public class TaskQueueManager {
	private static TaskQueueManager manager = new TaskQueueManager();
	
	public static TaskQueueManager getInstance() {
		return manager;
	}
}
