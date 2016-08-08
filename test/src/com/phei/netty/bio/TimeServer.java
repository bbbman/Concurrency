package com.phei.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	
	public static void main(String[] args) throws IOException{
		int port = 8080;
		if(args !=null && args.length>0){
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//default value
			}
		}
		ServerSocket server = null;
		try{
			server = new ServerSocket(port);
			System.out.println("端口是："+port);
			Socket socket = null;
			while(true){
				socket = server.accept();
				new Thread(new TimeServerHander(socket)).start();
			}
		}finally{
			if(server !=null){
				System.out.println("服务器关闭");
				server.close();
				server = null;
			}
		}
	}
}
