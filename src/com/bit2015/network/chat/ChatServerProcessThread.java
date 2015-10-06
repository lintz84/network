package com.bit2015.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class ChatServerProcessThread extends Thread {
	
	private static final String PROTOCOL_DIVIDER = ":";
	private String nickname;
	private Socket socket;
	private List<PrintWriter> listpws;
	
	public ChatServerProcessThread(Socket socket, List<PrintWriter> listpws){
		this.socket = socket;
		this.listpws = listpws;
	}

	@Override
	public void run() {
		BufferedReader buffer = null;
		PrintWriter pw = null;
		try{
			//1. 스트림 얻기
			buffer = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());
			
			//2. 리모트 호스트 정보 얻기
			InetSocketAddress inet = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remotehostaddress = inet.getHostName();
			int remotehostport = inet.getPort();
			ChatServer.log("연결됨 from " + remotehostaddress + ":" + remotehostport);
			
			//3. 요청 처리
			while(true){
				String request = buffer.readLine();
				if(request == null){
					ChatServer.log("클라이언트 연결 끊김");
					doQuit(pw);
					break;
				}
				
				String[] tokens = request.split(PROTOCOL_DIVIDER);
				
				if("join".equals(tokens[0])){
					doJoin(tokens[1], pw);
				}else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				}else if("quit".equals(tokens[0])){
					doQuit(pw);
					break;
				}else{
					ChatServer.log("에러: 알수 없는 요청명령(" + tokens + ")");
				}
			}
			
			// 자원정리
			buffer.close();
			pw.close();
			if(socket.isClosed() == false){
				socket.close();
			}
			
		}catch(IOException e){
			ChatServer.log("error :" + e);
		}
	}
	
	private void doJoin(String nickname, PrintWriter pw){
		//1. 닉네임 저장
		this.nickname = nickname;
		
		//2. 메시지 브로드캐스팅
		String message = nickname + "님이 입장했습니다.";
		broadcast(message);
		//3.
		addPrintWriter(pw);
		
		//4. ack
		pw.println("join:Ok");
		pw.flush();
	}
	
	private void doMessage(String message){
			String data = nickname + ":" + message;
			broadcast(data);
	}
	
	private void doQuit(PrintWriter pw){
		// PrintWriter 제거
		removePrintWriter(pw);
		
		// 퇴장 메세지 브로드캐스팅
		String data = nickname + "님이 퇴장 하였습니다.";
		broadcast(data);
	}
	
	private void removePrintWriter(PrintWriter pw){
		synchronized (listpws) {
			listpws.remove(pw);
		}
	}
	
	private void addPrintWriter(PrintWriter pw){
		synchronized(listpws){
			listpws.add(pw);
		}
	}
	
	private void broadcast(String data){
		synchronized (listpws) {
			Iterator<PrintWriter> it = listpws.iterator();
			while(it.hasNext()){
				PrintWriter pw = it.next();
				pw.println(data);
				pw.flush();
			}
		}
	}
}
