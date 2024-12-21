package client.service;

import client.model.ChatRoom;
import client.model.LoginAccount;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LoadChatRoomService {

    private static LoadChatRoomService instance = null;

    public static LoadChatRoomService getInstance() {
        if (instance == null)
            instance = new LoadChatRoomService();
        return instance;
    }

    public ArrayList<ChatRoom> loadChatRoom() {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        HashMap<String, HashMap<Integer, String[]>> responseObject;
        ArrayList<ChatRoom> chatRoomData = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String[] requestObject = new String[2];
        requestObject[0] = "loadChatRoomRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();

        try {
            out.writeObject(requestObject);
            out.flush();

            responseObject = (HashMap<String, HashMap<Integer, String[]>>) in.readObject();
            if (responseObject.containsKey("loadChatRoomResponse")) {
                HashMap<Integer, String[]> responseData = responseObject.get("loadChatRoomResponse");
                for (Integer roomId : responseData.keySet()) {
                    ChatRoom chatRoom = new ChatRoom(
                            Integer.parseInt(responseData.get(roomId)[0]),
                            responseData.get(roomId)[1],
                            responseData.get(roomId)[2],
                            Integer.parseInt(responseData.get(roomId)[3]),
                            responseData.get(roomId)[4],
                            format.parse(responseData.get(roomId)[5]),
                            Integer.parseInt(responseData.get(roomId)[6])
                    );
                    chatRoomData.add(chatRoom);
                }
                Collections.sort(chatRoomData);
                return chatRoomData;
            }
            return null;
        } catch (IOException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
