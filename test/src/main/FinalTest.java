package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class Test1{
	public Test1 get(){
		return new Test1();
	}
}

public class FinalTest {

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		final Socket connection = socket.accept();
	}
}
