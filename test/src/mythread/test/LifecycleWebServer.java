package mythread.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {
	private  ExecutorService exec = null;
	
	public void start() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while(! exec.isShutdown()) {			
			try {
				final Socket conn = socket.accept();
				exec.execute(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						handleRequest(conn);
					}					
				});
			} catch (RejectedExecutionException e) {
				// TODO Auto-generated catch block
				if(!exec.isShutdown())
					System.out.println("task submission rejected");
			}			
		}
	}
	
	public void stop() { exec.shutdown();}
	
	void handleRequest(Socket connection) {
		//Request req = readRequest(connection);
		
	}
}
