package client.service;

import client.model.LoginAccount;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LeaveChatRoomService {

    private static LeaveChatRoomService instance = null;

    public static LeaveChatRoomService getInstance() {
        if (instance == null)
            instance = new LeaveChatRoomService();
        return instance;
    }

    public boolean leaveChatRoom(int roomId) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[3];
        requestObject[0] = "leaveChatRoomRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();
        requestObject[2] = Integer.toString(roomId);
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            return responseObject[0].equals("leaveChatRoomResponse");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
