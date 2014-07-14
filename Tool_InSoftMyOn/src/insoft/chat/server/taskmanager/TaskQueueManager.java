package insoft.chat.server.taskmanager;

import insoft.chat.server.socketManager.RequestInfo;
import insoft.openmanager.message.Message;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskQueueManager extends Thread {
	private static TaskQueueManager manager = new TaskQueueManager();
	private Vector<RequestInfo> requestQueue = new Vector<RequestInfo>();
	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(30);
	
	private TaskQueueManager() {
		this.start();
	}
	
	public static TaskQueueManager getInstance() {
		return manager;
	}
	
	public void addReqQueue(Message reqMsg , int socketSessionID){
		RequestInfo info = new RequestInfo();
		info.reqMessage = reqMsg;
		info.socketSessionld = socketSessionID ;

		requestQueue.add(info);
	}
	
	public void run (){
		ArrayList<RequestInfo> buffer = new ArrayList<RequestInfo>();
		ServerTaskManager taskManager = ServerTaskManager.getInstance();
		
		while (true){
			try {
				synchronized(this)
				{
					buffer.addAll(requestQueue);
					requestQueue.removeAllElements();

					for (int i = 0 , size = buffer.size() ; i < size ; i++){
						try{
							RequestInfo info = (RequestInfo)buffer.get(i);
							String reqMsgName = info.reqMessage.getMessageName();
							ServerTask task = taskManager.getTask(reqMsgName);

							task.setRequestInfo(info);
							executor.execute(task);
						}
						catch (Exception e){
							e.printStackTrace();
						}
					}
					buffer.clear();
					Thread.sleep(20);
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
