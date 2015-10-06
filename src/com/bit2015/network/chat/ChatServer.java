package com.bit2015.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private static final int PORT = 30000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serversocket = null;
		List<PrintWriter> listpws = new ArrayList<PrintWriter>();
		
		try{
			//1. 서버소켓 생성
			serversocket = new ServerSocket();
			
			//2. 바인딩
			InetAddress inetadd = InetAddress.getLocalHost();
			String hostaddress = inetadd.getHostAddress();
			serversocket.bind(new InetSocketAddress(hostaddress,PORT));
			log("연결 기다림" + hostaddress + ":" + PORT);
			
			//3. 연결요청 기다림
			while(true){
				Socket socket = serversocket.accept();
				
				Thread thread = new ChatServerProcessThread(socket, listpws);
				thread.start();
			}
			
		}catch(IOException e){
			log("error :" + e);
		}finally{
			if(serversocket != null && serversocket.isClosed() == false){
				try{
					serversocket.close();
				}catch(IOException e){
				
				}
			}
		}
	}

	public static void log(String log){
		System.out.println("[chat-server]" + log);
	}
}
