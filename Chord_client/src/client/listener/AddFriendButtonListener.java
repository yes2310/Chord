package client.listener;

import client.data.DataProvider;
import client.frame.FriendListView;
import client.runnable.ThreadLock;
import client.service.AddFriendService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFriendButtonListener implements ActionListener {

    private final FriendListView view;
    private final JTextField friendIdField;

    public AddFriendButtonListener(FriendListView view, JTextField friendIdField) {
        this.friendIdField = friendIdField;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton addFriendButton = (JButton) e.getSource();
        String friendId = friendIdField.getText();
        boolean result;
        synchronized (ThreadLock.lock) {
            result = AddFriendService.getInstance().addFriend(addFriendButton, friendId);
            DataProvider.getInstance().loadMemberData();
        }
        if (!result) {
            return;
        }
        addFriendButton.getTopLevelAncestor().setVisible(false);
        view.setEnabled(true);
        view.repaint();
        view.revalidate();
        view.requestFocus();
    }
}
