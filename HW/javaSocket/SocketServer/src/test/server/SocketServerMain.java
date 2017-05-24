package test.server;

import java.io.IOException; 
import java.net.ServerSocket;
import java.net.Socket;



public class SocketServerMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		try {
			
			ServerSocket s_socket = new ServerSocket(5000);
			while(true){
				Socket socket = s_socket.accept();
				SocketThread c_thread = new SocketThread();
				c_thread.setSocket(socket);
				c_thread.start();
				Thread.sleep(1000);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

}
}
