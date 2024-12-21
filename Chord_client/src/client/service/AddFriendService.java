package client.service;

import client.data.DataProvider;
import client.model.LoginAccount;
import client.model.Member;
import client.network.ConnectionInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddFriendService {

    private static final int ADD_FRIEND_SUCCESS = 0;

    private static final int NOT_EXIST_ID = 1;

    public static AddFriendService instance = null;

    public static AddFriendService getInstance() {
        if (instance == null)
            instance = new AddFriendService();
        return instance;
    }

    public boolean addFriend(JButton addFriendButton, String friendId) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[3];
        requestObject[0] = "addFriendRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();
        requestObject[2] = friendId;

        if (friendId.isEmpty()) {
            JOptionPane.showMessageDialog(addFriendButton, "항목을 적으셔야 합니다.", "친구 추가 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (DataProvider.getInstance().containsMemberFromFriend(friendId)) {
            JOptionPane.showMessageDialog(addFriendButton, "이미 친구로 등록된 사용자입니다.", "친구 추가 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (friendId.equals(LoginAccount.getInstance().getMyInfo().getUserId())) {
            JOptionPane.showMessageDialog(addFriendButton, "자신을 친구로 등록할 수 없습니다.", "친구 추가 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            if (responseObject[0].equals("addFriendResponse")) {
                int responseCode = Integer.parseInt(responseObject[1]);
                if (responseCode == ADD_FRIEND_SUCCESS) {
                    Member friend = new Member(responseObject[2], responseObject[3], responseObject[4]);
                    DataProvider.getInstance().addMemberData(friend);
                    return true;
                } else if (responseCode == NOT_EXIST_ID) {
                    JOptionPane.showMessageDialog(addFriendButton, "아이디가 존재하지 않습니다.", "친구 추가 실패", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
