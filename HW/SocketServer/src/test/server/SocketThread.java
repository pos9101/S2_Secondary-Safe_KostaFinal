package test.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketThread extends Thread{
	private Socket m_socket;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		
		try {
			BufferedReader tmpBuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			while( true ){
			String text = tmpBuffer.readLine();
			System.out.println(text);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void setSocket(Socket socket){
		m_socket = socket;
	}

}
