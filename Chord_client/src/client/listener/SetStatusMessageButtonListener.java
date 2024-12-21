package client.listener;

import client.data.DataProvider;
import client.frame.FriendListView;
import client.model.LoginAccount;
import client.runnable.ThreadLock;
import client.service.SetStatusMessageService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetStatusMessageButtonListener implements ActionListener {
    private final FriendListView view;
    private final JTextField statusMessageField;

    public SetStatusMessageButtonListener(FriendListView view, JTextField statusMessageField) {
        this.statusMessageField = statusMessageField;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton setStatusMessageButton = (JButton) e.getSource();
        String statusMessage = statusMessageField.getText();
        boolean result;
        synchronized (ThreadLock.lock) {
            result = SetStatusMessageService.getInstance().SetStatusMessage(setStatusMessageButton, statusMessage);
            DataProvider.getInstance().loadMemberData();
        }
        if (!result) {
            return;
        }
        LoginAccount.getInstance().getMyInfo().setStatusMessage(statusMessage);
        setStatusMessageButton.getTopLevelAncestor().setVisible(false);
        view.setEnabled(true);
        view.repaint();
        view.revalidate();
        view.requestFocus();
    }
}
