package client.service;

import client.model.LoginAccount;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CreateChatRoomService {

    private static CreateChatRoomService instance = null;

    public static CreateChatRoomService getInstance() {
        if(instance == null)
            instance = new CreateChatRoomService();
        return instance;
    }

    public int createChatRoom(String roomType, String friendId) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;
        int roomId;

        String[] requestObject = new String[4];
        requestObject[0] = "createChatRoomRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();
        requestObject[2] = roomType;
        requestObject[3] = friendId;
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            if (responseObject[0].equals("createChatRoomResponse")) {
                roomId = Integer.parseInt(responseObject[1]);
                return roomId;
            }
            return -1;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
