package client.service;

import client.model.LoginAccount;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SendMessageService {

    private static SendMessageService instance = null;

    public static SendMessageService getInstance() {
        if(instance == null)
            instance = new SendMessageService();
        return instance;
    }

    public boolean sendMessage(int roomId, String message) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[4];
        requestObject[0] = "sendMessageRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();
        requestObject[2] = String.valueOf(roomId);
        requestObject[3] = message;
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            return responseObject[0].equals("sendMessageResponse");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
