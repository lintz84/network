package com.bit2015.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

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
			System.out.println("[서버] 연결 기다림");
			Socket socket = serverSocket.accept();
			
			
			
			
			
			//4. 데이터 읽기/쓰기
			InetSocketAddress socketaddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			System.out.println("[서버] 연결됨 from : " + socketaddress.getHostName() + ":" + socketaddress.getPort());
			
			InputStream is = null;
			OutputStream os = null;
			try{
				is = socket.getInputStream();
				os = socket.getOutputStream();
				while (true) {
					byte[] buffer = new byte[128];
					int readBytecount = is.read(buffer);
					if (readBytecount < 0) { // 클라이언트가 정상적으로 종료
						System.out.println("[서버] 접속종료");
						break;
					}
					String data = new String(buffer, 0, readBytecount, "UTF-8");
					System.out.print("[서버] 데이터 수신 : " + data);
					// String data = "hello world";
					os.write(data.getBytes("UTF-8"));
					os.flush();
				}
				// 스트림 닫기
				is.close();
				os.close();
				// 데이터 소켓 닫기
				if(socket.isClosed() == false)
					socket.close();
				
			}catch(IOException e){
				System.out.println("[서버] 에러 : " + e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
