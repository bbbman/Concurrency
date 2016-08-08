package threadTest.sample.servertest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//串行执行任务，效率低
public class SingleThreadWebServer {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while(true) {
			Socket connection = socket.accept();
			//处理connection
		}
	}
}
