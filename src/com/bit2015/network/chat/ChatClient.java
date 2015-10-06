package com.bit2015.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_ADDRESS = "192.168.1.95";
	private static final int SERVER_PORT = 30000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner scan = null;
		
		try {
			scan = new Scanner(System.in);
			socket = new Socket();
			//System.out.println("[클라이언트] 연결요청");
			socket.connect(new InetSocketAddress(SERVER_ADDRESS,SERVER_PORT));
			//System.out.println("[클라이언트] 연결종료");
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			
			System.out.print("닉네임>>");
			String nickname = scan.nextLine();
			pw.println("join:" + nickname);
			pw.flush();
			
			//String join = buffer.readLine();
			//System.out.println(join);
			
			//buffer.readLine();
			
			Thread thread = new ChatClientRecevieThread(buffer);
			thread.start();
			
			
			while(true){
				
				
				//System.out.print(">>");
				String input = scan.nextLine();
				if("quit".equals(input) == true){
					pw.println(input);
					pw.flush();
					break;
				}else{
					String data = "message:" + input;
					pw.println(data);
					pw.flush();
				}
				
			}
			
			scan.close();
			buffer.close();
			pw.close();
			if(socket.isClosed() == false){
				
				socket.close();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log("에러 : " + e);
		}
	}
	
	public static void log(String log){
		System.out.println("[chat-client]" + log);
	}
}
