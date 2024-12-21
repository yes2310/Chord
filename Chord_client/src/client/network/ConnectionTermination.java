package client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTermination {

    private static ConnectionTermination instance = null;

    public static ConnectionTermination getInstance() {
        if (instance == null)
            instance = new ConnectionTermination();
        return instance;
    }

    public void disconnect() {
        Socket socket = ConnectionInfo.getInstance().getSocket();
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] requestObject = new String[1];
        requestObject[0] = "disconnectRequest";
        try {
            out.writeObject(requestObject);
            out.flush();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
