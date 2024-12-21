package client.listener;

import client.data.DataProvider;
import client.frame.ChatRoomView;
import client.model.OpenedViewList;
import client.runnable.ThreadLock;
import client.service.CreateChatRoomService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupChatButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int roomId;
        synchronized (ThreadLock.lock) {
            roomId = CreateChatRoomService.getInstance().createChatRoom("public", null);
            DataProvider.getInstance().loadChatRoomData();
        }
        if (roomId == -1)
            return;

        synchronized (ThreadLock.lock) {
            DataProvider.getInstance().loadChatRoomData();
            DataProvider.getInstance().loadMessageData(roomId);
        }
        OpenedViewList.getInstance().getOpenedChatRoomView().put(roomId, new ChatRoomView(roomId));
    }
}
