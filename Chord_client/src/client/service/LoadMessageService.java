package client.service;

import client.model.Message;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LoadMessageService {

    private static LoadMessageService instance = null;

    public static LoadMessageService getInstance() {
        if(instance == null)
            instance = new LoadMessageService();
        return instance;
    }

    public ArrayList<Message> loadMessage(int roomId) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        HashMap<String, HashMap<Integer, String[]>> responseObject;
        ArrayList<Message> messageData = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String[] requestObject = new String[2];
        requestObject[0] = "loadMessageRequest";
        requestObject[1] = Integer.toString(roomId);
        try {
            out.writeObject(requestObject);
            out.flush();

            responseObject = (HashMap<String, HashMap<Integer, String[]>>) in.readObject();
            if (responseObject.containsKey("loadMessageResponse")) {
                HashMap<Integer, String[]> responseData = responseObject.get("loadMessageResponse");
                for (Integer messageId : responseData.keySet()) {
                    Message message = new Message(
                            Integer.parseInt(responseData.get(messageId)[0]),
                            responseData.get(messageId)[1],
                            responseData.get(messageId)[2],
                            responseData.get(messageId)[3],
                            responseData.get(messageId)[4],
                            format.parse(responseData.get(messageId)[5])
                    );
                    messageData.add(message);
                }
                Collections.sort(messageData);
                return messageData;
            }
            return null;
        } catch (IOException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
