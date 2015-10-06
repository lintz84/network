package com.bit2015.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner scan = new Scanner(System.in);
			while(true){
				System.out.print(">");
				String host = scan.nextLine();
				if("exit".equals(host))
					break;
				InetAddress[] inetaddress = InetAddress.getAllByName(host);
				for (InetAddress inetAddress2 : inetaddress) {
					System.out.println(inetAddress2.getHostName() + ":" + inetAddress2.getHostAddress());
				}
			}
			
			scan.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("IP를 가져올 수 없습니다.");
			//e.printStackTrace();
		}
	}

}
