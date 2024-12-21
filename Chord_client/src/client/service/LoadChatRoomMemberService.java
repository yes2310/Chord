package client.service;

import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class LoadChatRoomMemberService {

    private static LoadChatRoomMemberService instance =null;

    public static LoadChatRoomMemberService getInstance() {
        if(instance == null)
            instance = new LoadChatRoomMemberService();
        return instance;
    }

    public ArrayList<String[]> loadChatRoomMember(int roomId) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        HashMap<String, ArrayList<String[]>> responseObject;
        ArrayList<String[]> memberData;

        String[] requestObject = new String[2];
        requestObject[0] = "loadChatRoomMemberRequest";
        requestObject[1] = Integer.toString(roomId);
        try {
            out.writeObject(requestObject);
            out.flush();

            responseObject = (HashMap<String, ArrayList<String[]>>) in.readObject();
            if (responseObject.containsKey("loadChatRoomMemberResponse")) {
                memberData = responseObject.get("loadChatRoomMemberResponse");
                memberData.sort(new Comparator<String[]>() {
                    @Override
                    public int compare(String[] o1, String[] o2) {
                        return o1[1].compareTo(o2[1]);
                    }
                });
                return memberData;
            }
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
