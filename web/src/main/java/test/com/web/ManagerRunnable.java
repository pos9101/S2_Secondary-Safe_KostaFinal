package test.com.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManagerRunnable implements Runnable {

	Socket m_socket;
	public ManagerRunnable(Socket socket) {
		m_socket = socket;
	}
	@Override
	public void run() {
		try {
			
			BufferedReader inputSystem = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			PrintWriter prWriter = new PrintWriter(m_socket.getOutputStream());
			String text;
			
			//????
			while(true){
				if((text=inputSystem.readLine())!=null){
					if(text == "exit"){
						for(int i=0 ; i<FinalServer.m_outputList.size() ; i++ ){
							FinalServer.m_outputList.get(i).println("server closed");
							FinalServer.m_outputList.get(i).flush();
							inputSystem.close();
						}
						break;
					}
						
					for(int i=0 ; i<FinalServer.m_outputList.size() ; i++ ){
						FinalServer.m_outputList.get(i).println(text);
						FinalServer.m_outputList.get(i).flush();
						System.out.println("send");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			for(int i=0 ; i<FinalServer.m_outputList.size() ; i++ ){
				FinalServer.m_outputList.get(i).close();
			}
			try {
				m_socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
