package com.bit2015.network.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatClientGUI {

	private static final String SERVER_ADDRESS = "192.168.1.95";
	private static final int SERVER_PORT = 30000;
	private Socket socket = null;
	BufferedReader buffer = null;
	PrintWriter pw = null;
	JFrame frame = new JFrame("Chating Programe");
	JTextArea chatviwe;
	JTextField message;
	JTextField nickname;
	
	public static void main(String[] args) {
		ChatClientGUI chat = new ChatClientGUI();
		chat.go();
		
		
		
	}
	
	
	public void go(){
		
		JPanel panel = new JPanel();
		nickname = new JTextField(20);
		JButton join = new JButton("join");
		join.addActionListener(new JoinButtonListener());
		panel.add(nickname);
		panel.add(join);
		
		
		try {
			//scan = new Scanner(System.in);
			socket = new Socket();
			//System.out.println("[클라이언트] 연결요청");
			socket.connect(new InetSocketAddress(SERVER_ADDRESS,SERVER_PORT));
			//System.out.println("[클라이언트] 연결종료");
			
			buffer = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());
			
			
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			//log("에러 : " + e);
			ex.printStackTrace();
		}
		
		
		
		frame.getContentPane().add(panel);
		frame.setSize(300,100);
		frame.setVisible(true);
		
	}
	
	public void stop(){
		
		try {
			buffer.close();
			pw.close();
			if(socket.isClosed() == false){
				
				socket.close();
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	public class JoinButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			JPanel panel = new JPanel();
			chatviwe = new JTextArea(15,50);
			chatviwe.setLineWrap(true);
			chatviwe.setWrapStyleWord(true);
			chatviwe.setEditable(false);
			JScrollPane scroller = new JScrollPane(chatviwe);
			scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			message = new JTextField(20);
			JButton send = new JButton("send");
			send.addActionListener(new SendButtonListener());
			panel.add(scroller);
			panel.add(message);
			panel.add(send);
			
			pw.println("join:" + nickname.getText());
			pw.flush();
			//System.out.print(nickname.getText());
			
			Thread thread = new Thread(new ChatClientGUIThread());
			thread.start();
			
			
			
			//frame.getContentPane().repaint();
			frame.getContentPane().add(panel);
			//frame.getContentPane().repaint(0, 0, 500, 200);
			frame.getContentPane().remove(0);
			frame.setSize(600,350);
			//frame.remove(0);
			frame.setVisible(true);
			
			
				
				
				
				
			
		}
	}
	
	public class SendButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//String input = frame.getContentPane().getComponent(0).getName();
			//System.out.print(input);
			//while(true){
				
				
				//System.out.print(">>");
				String input = message.getText();
				if("quit".equals(input) == true){
					//pw.println(input);
					//pw.flush();
					frame.dispose();
					stop();
					
				}else{
					String data = "message:" + input;
					pw.println(data);
					pw.flush();
				}
			
				
				message.setText("");
				message.requestFocus();
			//}
			
			
			
		}
		
	}
	
	public class ChatClientGUIThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				while(true){
					String request = buffer.readLine();
					if(request == null){
						break;
					}
					
					System.out.println(request);
					chatviwe.append(request + "\n");
					
					
					
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
	
	
	

}
