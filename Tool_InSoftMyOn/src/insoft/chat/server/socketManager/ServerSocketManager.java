package insoft.chat.server.socketManager;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class ServerSocketManager {

	public ServerSocketManager(){

	}

	public void startSocket() {
		Selector selector = null ;
		ServerSocketChannel channel = null ;
		ServerSocket socket ;

		try {
			selector = Selector.open();
			channel = ServerSocketChannel.open();
			socket = channel.socket();

			SocketAddress addr = new InetSocketAddress(7777);
			socket.bind(addr);

			channel.configureBlocking(false);

			channel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("---- Client의 접속을 기다립니다... ----");
		}
		catch(BindException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
			return;
		}

		try {
			while (selector.select() > 0 ){
				Set keys = selector.selectedKeys();
				Iterator<SelectionKey> iter = keys.iterator();
				while (iter.hasNext()){
					try{
						SelectionKey selected = iter.next();
						if (selected.isValid() && selected.isAcceptable()){
							System.out.println("accept");
							SocketChannel ch = ((ServerSocketChannel) selected.channel()).accept();

							ch.finishConnect();
							ch.configureBlocking(false);

							ServerSocketConn socketConn = new ServerSocketConn(ch);
							socketConn.start();
							
							ServerSessionManager sessionMg = ServerSessionManager.getInstance();
							//sessionMg.setAddSessionId(socketConn);
							
							sessionMg.setAddSessionId(ch);
						}
					}
					catch(Exception e){
						System.out.println("로그아웃");
					}
					iter.remove();
				}
			}
		} catch (IOException e) {
			System.out.println("마지막 로그아웃");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerSocketManager server = new ServerSocketManager();
		server.startSocket();
	}
}
