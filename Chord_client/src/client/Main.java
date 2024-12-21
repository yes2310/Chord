package client;

import client.frame.LoginView;
import client.network.ConnectionInfo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.PLAIN, 15));
        UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.PLAIN, 15));
        connectServer();
        new LoginView(null);
    }

    public static void connectServer() {
        Socket socket;
        try {
            ConnectionInfo.getInstance().setSocket(socket = new Socket("yes2310.synology.me", 8888));
            ConnectionInfo.getInstance().setIn(new ObjectInputStream(socket.getInputStream()));
            ConnectionInfo.getInstance().setOut(new ObjectOutputStream(socket.getOutputStream()));
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "알 수 없는 호스트입니다.", "연결 실패", JOptionPane.WARNING_MESSAGE);
            System.exit(-1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "서버 연결에 실패하였습니다.", "연결 실패", JOptionPane.WARNING_MESSAGE);
            System.exit(-1);
        }
    }
}
