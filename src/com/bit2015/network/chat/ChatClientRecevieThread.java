package com.bit2015.network.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientRecevieThread extends Thread {
	private BufferedReader buffer;
	//private static final String PROTOCOL_DIVIDER = ":";
	
	public ChatClientRecevieThread(BufferedReader buffer) {
		this.buffer = buffer;
		
	}

	@Override
	public void run() {
		
		try{
			while(true){
				String request = buffer.readLine();
				if(request == null){
					break;
				}
				
				System.out.println(request);
				
				
				
				/*
				String[] tokens = request.split(PROTOCOL_DIVIDER);
			
				if(tokens[1] != null){
					System.out.println("\n" + tokens[0] + ":" + tokens[1]);
				}else{
					System.out.println("\n" + request);
				}*/
			}
		}catch(IOException e){
			ChatClient.log("에러 : " + e);
		}
		
	}
}
