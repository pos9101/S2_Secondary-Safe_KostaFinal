package test.com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FinalServer {
	
	public static ArrayList<PrintWriter> m_outputList ;
	
	public static void start() {
		System.out.println("server start");
		m_outputList = new ArrayList<PrintWriter>();
		try {
			ServerSocket servSocket = new ServerSocket(5000);
			while(true){
				Socket socket = servSocket.accept();
				System.out.println("client in");
				m_outputList.add(new PrintWriter(socket.getOutputStream()));
//				Thread thread = new Thread(new ManagerRunnable(socket));
//				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
