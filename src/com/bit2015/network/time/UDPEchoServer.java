package com.bit2015.network.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {

	private static final int PORT = 60000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				String data = new String(reveivepacket.getData(), 0,
						reveivepacket.getLength());
				log("packet 수신: " + data);

				// 4. 데이터 보내기
				DatagramPacket sendpacket = new DatagramPacket(
						reveivepacket.getData(), reveivepacket.getLength(),
						reveivepacket.getAddress(), reveivepacket.getPort());
				datasocket.send(sendpacket);
				if("end".equals(data))
					break;
			}
			
			//5. 자원정리
			datasocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log("error : " + e);
		}
	}

	public static void log(String log){
		System.out.println("[UDP-echo-Server]" + log);
	}
}
