package server.runnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunnable implements Runnable {
    @Override
    public void run() {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(8888);
            while (true) {
                socket = serverSocket.accept();

                Thread thread = new Thread(new ClientManager(socket));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("소켓 생성에 실패하였습니다.");
            System.exit(-1);
        }
    }
}
