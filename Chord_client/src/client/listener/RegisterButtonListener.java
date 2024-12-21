package client.listener;

import client.frame.LoginView;
import client.service.RegisterService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButtonListener implements ActionListener {

    private final JTextField userIdField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JTextField nameField;

    public RegisterButtonListener(JTextField userIdField, JPasswordField passwordField, JPasswordField confirmPasswordField, JTextField nameField) {
        this.userIdField = userIdField;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
        this.nameField = nameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton registerButton = (JButton) e.getSource();

        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String name = nameField.getText();

        boolean result = RegisterService.getInstance().register(registerButton, userId, password, confirmPassword, name);
        if (!result)
            return;
        registerButton.getTopLevelAncestor().setVisible(false);
        new LoginView(registerButton.getTopLevelAncestor().getLocation());
    }
}
