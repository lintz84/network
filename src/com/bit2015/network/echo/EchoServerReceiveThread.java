package com.bit2015.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {
	private Socket socket = null; 
	
	public EchoServerReceiveThread(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		
		InetSocketAddress socketaddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		System.out.println("[서버] 연결됨 from : " + socketaddress.getHostName() + ":" + socketaddress.getPort());
		
	//	InputStream is = null;
	//	OutputStream os = null;
		
		BufferedReader reader = null;
		PrintWriter pw = null;
		
		
			try {
			//	is = socket.getInputStream();
			//	os = socket.getOutputStream();
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				pw = new PrintWriter(socket.getOutputStream());
				
				while(true){
					/*
					byte[] buffer = new byte[128];
					int readBytecount = is.read(buffer);
					if (readBytecount < 0) { // 클라이언트가 정상적으로 종료
						System.out.println("[클라이언트] : " + socketaddress.getHostName() +" 접속종료");
						break;
					}
					String data = new String(buffer, 0, readBytecount, "UTF-8");
					*/
					String data = reader.readLine();
					if(data == null){
						break;
					}
					System.out.println("[클라이언트] : " + socketaddress.getHostName() +  " 데이터 수신 : " + data);
					
					// String data = "hello world";
					//os.write(data.getBytes("UTF-8"));
					//os.flush();
					
					pw.println(data);
					pw.flush();
					
					System.out.println("[클라이언트] : " + socketaddress.getHostName() +  " 데이터 송신 ");
				}
				
				
				// 스트림 닫기
				//is.close();
				//os.close();
				
				
				reader.close();
				pw.close();
				// 데이터 소켓 닫기
				if(socket.isClosed() == false)
					socket.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("[서버] 에러 : " + e);
			}
			
			
	}
}
