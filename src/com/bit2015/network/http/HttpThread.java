package com.bit2015.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpThread extends Thread {

	private Socket socket;
	private static final String WEB_ROOT = "C:\\bit2015\\workspace\\network\\web-root";

	public HttpThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;

		try {
			// 1. 스트림 얻기
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = socket.getOutputStream();

			// 2. Remote Host Information
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			HttpServer.log("연결됨 from " + inetSocketAddress.getHostName() + ":"
					+ inetSocketAddress.getPort());

			// 3. 요청처리
			// 웹서버는 while로 대기하는게 아니라 한번 받고 연결끝는게 프로토콜
			String request = bufferedReader.readLine();
			if (request != null) {
				HttpServer.log("request:" + request);
				
				String[] tokens = request.split(" ");
				if ("GET".equals(tokens[0]) == true) {
					// 브라우저에서 요청하는 자원을 outputStream으로 보내기
					// tokens[1]은 path가 들어있음
					//System.out.print(tokens[1] +":"+ tokens[2]);
					sendStaticResource(outputStream, tokens[1], tokens[2]);
				} else {
					HttpServer.log("error: 지원하지 않는 요청 명령(" + tokens[0] + ")");
				}
				
				
				/*// header
				outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
				outputStream.write("Content-Type:text/html; charset=UTF-8\r\n".getBytes());
				outputStream.write("\r\n".getBytes());
				// body
				outputStream.write("<h1>Hello World</h1>".getBytes());
				// flush
				outputStream.flush();*/
			}
			
			// 4. 연결 끊기
			bufferedReader.close();
			outputStream.close();
			socket.close();
			
		} catch (IOException ex) {
			HttpServer.log("error:" + ex);
		}
	}

	private void sendStaticResource( OutputStream outputStream, String path, String protocol ) throws IOException {

	    if( "/".equals( path ) ) {
	        path = "/index.html"; 
	    }
	    
	    String extension = path.substring( path.lastIndexOf( "." ) );
	    if( ".html".equals( extension ) == false && ".htm".equals( extension ) == false ) {
	       sendError404( outputStream, protocol );
	       return;
	    }
	    
	    File file = new File( WEB_ROOT, path );
	    if( file.exists() == false ) {
	      sendError404( outputStream, protocol );
	      return;
	    }
	    
	    // 요청한 파일을 읽어서 브라우저에게 전송한다
	    
	    //header
	    outputStream.write( ( protocol + " 200 OK\r\n" ).getBytes() );
	    outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );
	    outputStream.write( "\r\n".getBytes() );

	    // body
	    FileInputStream fis = new FileInputStream( file );
	    byte[] buffer = new byte[ 1024 ];

	    int countReadByte = fis.read( buffer, 0, 1024 );
	    while( countReadByte >= 0 ) {
	        outputStream.write( buffer, 0, countReadByte );
	        countReadByte = fis.read( buffer, 0, 1024 );
	    }

	    outputStream.flush();
	    fis.close();
	}
	
	private void sendError404( OutputStream outputStream, String protocol ) throws IOException {

	    // header
	    outputStream.write( ( protocol + " 404 File Not Found\r\n" ).getBytes() );
	    outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );
	    outputStream.write( "\r\n".getBytes() );

	    //body
	    outputStream.write( "<h1>File Not Found</h1>".getBytes() );
	    outputStream.flush();
	}
}
