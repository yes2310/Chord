package client.listener;

import client.data.DataProvider;
import client.frame.ChatRoomView;
import client.model.LoginAccount;
import client.model.OpenedViewList;
import client.runnable.ThreadLock;
import client.service.CreateChatRoomService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalChatButtonListener implements ActionListener {

    private final String friendId;

    public PersonalChatButtonListener(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // 아이디 친구아이디
        JButton personalChatButton = (JButton) e.getSource();
        String myName = LoginAccount.getInstance().getMyInfo().getName();
        String friendName = DataProvider.getInstance().getMember(friendId).getName();
        int roomId;
        if((roomId = DataProvider.getInstance().getChatRoomId(myName + "," + friendName)) == -1) {
            synchronized (ThreadLock.lock) {
                roomId = CreateChatRoomService.getInstance().createChatRoom("private", friendId);
                DataProvider.getInstance().loadChatRoomData();
            }
            if (roomId == -1)
                return;
        }
        synchronized (ThreadLock.lock) {
            DataProvider.getInstance().loadChatRoomData();
            DataProvider.getInstance().loadMessageData(roomId);
        }
        if (OpenedViewList.getInstance().getOpenedChatRoomView().containsKey(roomId)) {
            OpenedViewList.getInstance().getOpenedChatRoomView().get(roomId).setState(JFrame.NORMAL);
            OpenedViewList.getInstance().getOpenedChatRoomView().get(roomId).requestFocus();
        } else {
            OpenedViewList.getInstance().getOpenedChatRoomView().put(roomId, new ChatRoomView(roomId));
        }
    }
}
