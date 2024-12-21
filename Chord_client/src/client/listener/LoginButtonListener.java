package client.listener;

import client.frame.FriendListView;
import client.service.LoginService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButtonListener implements ActionListener {

    private final JTextField userIdField;
    private final JPasswordField passwordField;

    public LoginButtonListener(JTextField userIdField, JPasswordField passwordField) {
        this.userIdField = userIdField;
        this.passwordField = passwordField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton loginButton = (JButton) e.getSource();

        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());

        boolean result = LoginService.getInstance().login(loginButton, userId, password);
        if (!result)
            return;
        loginButton.getTopLevelAncestor().setVisible(false);
        new FriendListView(loginButton.getTopLevelAncestor().getLocation());
    }
}
