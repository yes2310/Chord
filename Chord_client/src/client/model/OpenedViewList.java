package client.model;

import client.frame.ChatRoomListView;
import client.frame.ChatRoomView;
import client.frame.ContactView;

import java.util.HashMap;

public class OpenedViewList {

    private static OpenedViewList instance = null;

    private HashMap<Integer, ChatRoomView> openedChatRoomView = new HashMap<>();

    private HashMap<Integer, ContactView> openedContactView = new HashMap<>();

    private ChatRoomListView chatRoomListView = null;

    public static OpenedViewList getInstance() {
        if(instance == null)
            instance = new OpenedViewList();
        return instance;
    }

    public HashMap<Integer, ChatRoomView> getOpenedChatRoomView() {
        return openedChatRoomView;
    }

    public void setOpenedChatRoomView(HashMap<Integer, ChatRoomView> openedChatRoomView) {
        this.openedChatRoomView = openedChatRoomView;
    }

    public HashMap<Integer, ContactView> getOpenedContactView() {
        return openedContactView;
    }

    public void setOpenedContactView(HashMap<Integer, ContactView> openedContactView) {
        this.openedContactView = openedContactView;
    }

    public ChatRoomListView getChatRoomListView() {
        return chatRoomListView;
    }

    public void setChatRoomListView(ChatRoomListView chatRoomListView) {
        this.chatRoomListView = chatRoomListView;
    }
}
