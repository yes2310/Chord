package client.service;

import client.data.DataProvider;
import client.model.LoginAccount;
import client.model.Member;
import client.network.ConnectionInfo;
import client.runnable.ClientRunnable;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginService {

    private static final int LOGIN_SUCCESS = 0;

    private static final int NOT_EXIST_ID = 1;

    private static final int NOT_MATCH_PASSWORD = 2;

    private static LoginService instance = null;

    public static LoginService getInstance() {
        if (instance == null)
            instance = new LoginService();
        return instance;
    }

    public boolean login(JButton loginButton, String userId, String password) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[3];
        requestObject[0] = "loginRequest";
        requestObject[1] = userId;
        requestObject[2] = password;

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginButton, "항목을 모두 적으셔야 합니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if(userId.length() > 15) {
            JOptionPane.showMessageDialog(loginButton, "아이디는 15자 이하로 적으셔야 합니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            out.writeObject(requestObject);
            out.flush();

            responseObject = (String[]) in.readObject();
            if (responseObject[0].equals("loginResponse")) {
                int responseCode = Integer.parseInt(responseObject[1]);
                if (responseCode == LOGIN_SUCCESS) {
                    LoginAccount.getInstance().setMyInfo(new Member(
                            userId,
                            responseObject[2],
                            responseObject[3]
                    ));
                    DataProvider.getInstance().loadMemberData();
                    DataProvider.getInstance().loadChatRoomData();
                    Thread thread = new Thread(new ClientRunnable());
                    thread.start();

                    return true;
                } else if (responseCode == NOT_EXIST_ID) {
                    JOptionPane.showMessageDialog(loginButton, "아이디가 존재하지 않습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
                    return false;
                } else if (responseCode == NOT_MATCH_PASSWORD) {
                    JOptionPane.showMessageDialog(loginButton, "비밀번호가 일치하지 않습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
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
