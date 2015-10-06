package com.bit2015.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			InetAddress inetaddress = InetAddress.getLocalHost();
			System.out.println(inetaddress.getHostName());
			System.out.println(inetaddress.getHostAddress());
			byte[] address = inetaddress.getAddress();
			for (byte b : address) {
				int addr = b & 0xff;
				System.out.println(b);
				System.out.println(addr);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
