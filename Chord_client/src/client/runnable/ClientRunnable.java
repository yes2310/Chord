package client.runnable;

import client.data.DataProvider;
import client.frame.ChatRoomListView;
import client.frame.ChatRoomView;
import client.frame.ContactView;
import client.model.OpenedViewList;

import java.util.ArrayList;
import java.util.Set;

public class ClientRunnable implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                ChatRoomListView chatRoomListView = OpenedViewList.getInstance().getChatRoomListView();
                if (chatRoomListView != null && chatRoomListView.isVisible()) {
                    synchronized (ThreadLock.lock) {
                        DataProvider.getInstance().loadChatRoomData();
                    }
                    chatRoomListView.repaint();
                    chatRoomListView.revalidate();
                }
                Set<Integer> chatRoomViewKeySet = OpenedViewList.getInstance().getOpenedChatRoomView().keySet();
                ArrayList<Integer> openedChatRoomViewList = new ArrayList<>(chatRoomViewKeySet);
                for (int i = 0; i < openedChatRoomViewList.size(); i++) {
                    ChatRoomView chatRoomView = OpenedViewList.getInstance().getOpenedChatRoomView().get(openedChatRoomViewList.get(i));
                    synchronized (ThreadLock.lock) {
                        DataProvider.getInstance().loadMessageData(openedChatRoomViewList.get(i));
                    }
                    chatRoomView.repaint();
                    chatRoomView.revalidate();
                }
                Set<Integer> contactViewKeySet = OpenedViewList.getInstance().getOpenedContactView().keySet();
                ArrayList<Integer> openedContactViewList = new ArrayList<>(contactViewKeySet);
                for(int i=0; i<openedContactViewList.size(); i++) {
                    ContactView contactView = OpenedViewList.getInstance().getOpenedContactView().get(openedContactViewList.get(i));
                    synchronized (ThreadLock.lock) {
                        DataProvider.getInstance().loadChatRoomMemberData(openedContactViewList.get(i));
                    }
                    contactView.repaint();
                    contactView.revalidate();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
