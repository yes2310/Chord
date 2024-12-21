package client.service;

import client.network.ConnectionInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegisterService {

    public static final int REGISTER_SUCCESS = 0;

    public static final int DUPLICATE_ID = 1;

    private static RegisterService instance = null;

    public static RegisterService getInstance() {
        if (instance == null)
            instance = new RegisterService();
        return instance;
    }

    public boolean register(JButton registerButton, String userId, String password, String confirmPassword, String name) {
        ObjectInputStream in = ConnectionInfo.getInstance().getIn();
        ObjectOutputStream out = ConnectionInfo.getInstance().getOut();
        String[] responseObject;

        String[] requestObject = new String[4];
        requestObject[0] = "registerRequest";
        requestObject[1] = userId;
        requestObject[2] = password;
        requestObject[3] = name;

        if (userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(registerButton, "항목을 모두 적으셔야 합니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (userId.length() > 15 || name.length() > 15) {
            JOptionPane.showMessageDialog(registerButton, "아이디, 이름은 15자 이하로 적으셔야 합니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerButton, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            out.writeObject(requestObject);
            out.flush();
            responseObject = (String[]) in.readObject();
            if (responseObject[0].equals("registerResponse")) {
                int responseCode = Integer.parseInt(responseObject[1]);
                if (responseCode == REGISTER_SUCCESS) {
                    JOptionPane.showMessageDialog(registerButton, "회원가입이 완료되었습니다.");
                    return true;
                } else if (responseCode == DUPLICATE_ID) {
                    JOptionPane.showMessageDialog(registerButton, "이미 존재하는 아이디입니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
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
