package client.service;

import client.model.LoginAccount;
import client.model.Member;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LoadFriendService {

    private static LoadFriendService instance = null;

    public static LoadFriendService getInstance() {
        if (instance == null)
            instance = new LoadFriendService();
        return instance;
    }

    public ArrayList<Member> loadFriend() {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        HashMap<String, HashMap<String, String[]>> responseObject;
        ArrayList<Member> friendData = new ArrayList<>();

        String[] requestObject = new String[2];
        requestObject[0] = "loadFriendRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();

        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (HashMap<String, HashMap<String, String[]>>) in.readObject();
            if (responseObject.containsKey("loadFriendResponse")) {
                HashMap<String, String[]> responseData = responseObject.get("loadFriendResponse");
                for (String friendId : responseData.keySet()) {
                    Member friend = new Member(responseData.get(friendId)[0], responseData.get(friendId)[1], responseData.get(friendId)[2]);
                    friendData.add(friend);
                }
                Collections.sort(friendData);
                return friendData;
            }
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
