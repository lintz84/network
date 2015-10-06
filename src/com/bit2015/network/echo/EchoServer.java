package com.bit2015.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class EchoServer {

	private static final int PORT = 10001;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		
		try {
			//1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			//2. 바인딩, 포트는 5000이상 아무거나
			InetAddress inetaddress = InetAddress.getLocalHost();
			String hostaddress = inetaddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostaddress,PORT));
			System.out.println("[서버] 바인딩" + hostaddress + ":" + PORT);
			
			
			
			//3. 연결 요청 대기
			while(true){
				System.out.println("[서버] 연결 기다림");
				Socket socket = serverSocket.accept();
				
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
				//System.out.println(thread.activeCount());
			}
		}catch(IOException e){
			System.out.println("[서버] 에러 : " + e);
		}finally{
			if(serverSocket != null && serverSocket.isClosed() == false){
				try{
					serverSocket.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

}
