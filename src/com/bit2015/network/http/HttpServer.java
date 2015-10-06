package com.bit2015.network.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private static final int PORT = 8088;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serversocket = null;
	
		try{
			//1. 서버소켓 생성
			serversocket = new ServerSocket();
		
			//2. 바인딩
			InetAddress inetadd = InetAddress.getLocalHost();
			String hostaddress = inetadd.getHostAddress(); // 웹서버 주소값 반환
			serversocket.bind(new InetSocketAddress(hostaddress,PORT));
			log("연결 기다림" + hostaddress + ":" + PORT);
		
			//3. 연결요청 기다림
			while(true){
				Socket socket = serversocket.accept();
			
				Thread thread = new HttpThread(socket);
				thread.start();
			}
		
		}catch(IOException e){
			log("error :" + e);
		}finally{
			if(serversocket != null && serversocket.isClosed() == false){
				try{
					serversocket.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	public static void log(String log){
		System.out.println("[Http-server]" + log);
	}
	
	

}
