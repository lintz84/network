package com.bit2015.network.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {

	private static final int PORT = 60000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
		
		//System.out.println(data);
		DatagramSocket datasocket = null;
		
		try {
			//1. UDP 서버 소켓 생성
			datasocket = new DatagramSocket(PORT);
			while(true){
				// 2. 수신 대기
				log("packet 수신대기");
				DatagramPacket reveivepacket = new DatagramPacket(
						new byte[BUFFER_SIZE], BUFFER_SIZE);
				datasocket.receive(reveivepacket);

				// 3. 수신 데이터 출력
				//String signal = new String(reveivepacket.getData(), 0,
				//		reveivepacket.getLength());

				// 4. 데이터 보내기
				String data = format.format(new Date());
				DatagramPacket sendpacket = new DatagramPacket(data.getBytes(),
						data.getBytes().length, reveivepacket.getAddress(),
						reveivepacket.getPort());
				datasocket.send(sendpacket);
				
			}
			
			//5. 자원정리
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log("error : " + e);
		} finally{
			datasocket.close();
		}
	}

	public static void log(String log){
		System.out.println("[UDP-echo-Server]" + log);
	}

}
