package client.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionInfo {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private static ConnectionInfo instance = null;

    public static ConnectionInfo getInstance() {
        if (instance == null)
            instance = new ConnectionInfo();
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }
}
