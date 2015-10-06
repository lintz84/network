package com.bit2015.network.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;



public class UDPEchoClient {
	
	private static final String SERVER_IP = "192.168.1.95";
	private static final int SERVER_PORT = 60000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatagramSocket datasocket = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			//1. UDP 클라이언트 소켓 생성
			datasocket = new DatagramSocket();
			while(true){
				// 2. packet 보내기
				String message = scan.nextLine();
				byte[] data = message.getBytes();

				DatagramPacket sendpacket = new DatagramPacket(data,
						data.length, new InetSocketAddress(SERVER_IP,
								SERVER_PORT));
				datasocket.send(sendpacket);
				
				DatagramPacket receivepacket = new DatagramPacket(
						new byte[BUFFER_SIZE], BUFFER_SIZE);
				datasocket.receive(receivepacket);

				String data1 = new String(receivepacket.getData(), 0,
						receivepacket.getLength());
				if("end".equals(message)){
					break;
				}else
					System.out.println("<<" + data1);
			}
			scan.close();
			datasocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log("error : " + e);
		}
	}

	public static void log(String log){
		System.out.println("[UDP-echo-Client]" + log);
	}
}
