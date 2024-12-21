package client.service;

import client.model.LoginAccount;
import client.network.ConnectionInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SetStatusMessageService {

    private static SetStatusMessageService instance = null;

    public static SetStatusMessageService getInstance() {
        if(instance == null)
            instance = new SetStatusMessageService();
        return instance;
    }

    public boolean SetStatusMessage(JButton setStatusMessageButton, String statusMessage) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[3];
        requestObject[0] = "setStatusMessageRequest";
        requestObject[1] = LoginAccount.getInstance().getMyInfo().getUserId();
        requestObject[2] = statusMessage;

        if (statusMessage.isEmpty()) {
            JOptionPane.showMessageDialog(setStatusMessageButton, "항목을 적으셔야 합니다.", "상태 메시지 설정 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if(statusMessage.length() > 15) {
            JOptionPane.showMessageDialog(setStatusMessageButton, "15자 이하로 적으셔야 합니다.", "상태 메시지 설정 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            return responseObject[0].equals("setStatusMessageResponse");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
