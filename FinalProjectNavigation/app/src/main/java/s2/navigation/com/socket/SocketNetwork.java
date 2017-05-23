package s2.navigation.com.socket;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Administrator on 2017-05-23.
 */

public class SocketNetwork {
    static Socket mSocket;


    public SocketNetwork() {
        try {
            mSocket = IO.socket("https://nodejs-jeongjiho.c9users.io:8080/");
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("smart", system2);
            mSocket.connect();

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //값보낼때 사용
    static Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("ddd");
            // TODO Auto-generated method stub
            String sendM="kyo1";
            mSocket.emit("rint",sendM);
            System.out.println("보낸값:"+sendM);
        }
    };//end emitter


    //값받을 때 사용
    static Emitter.Listener system2 = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println(args[0]);
        }//end call
    };//end Listener


}
