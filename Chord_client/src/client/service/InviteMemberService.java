package client.service;

import client.model.LoginAccount;
import client.network.ConnectionInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InviteMemberService {

    private static InviteMemberService instance = null;

    public static InviteMemberService getInstance() {
        if (instance == null)
            instance = new InviteMemberService();
        return instance;
    }

    public boolean inviteMember(int roomId, ArrayList<String> memberUserIdList) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[memberUserIdList.size() + 3];
        requestObject[0] = "inviteMemberRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();
        requestObject[2] = String.valueOf(roomId);
        for (int i = 0; i < memberUserIdList.size(); i++) {
            requestObject[i + 3] = memberUserIdList.get(i);
        }
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            return responseObject[0].equals("inviteMemberResponse");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
