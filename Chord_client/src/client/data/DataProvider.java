package client.data;

import client.model.ChatRoom;
import client.model.Member;
import client.model.Message;
import client.service.LoadChatRoomMemberService;
import client.service.LoadChatRoomService;
import client.service.LoadFriendService;
import client.service.LoadMessageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataProvider {

    private static DataProvider instance = null;

    private ArrayList<Member> memberData = new ArrayList<>();

    private ArrayList<ChatRoom> chatRoomData = new ArrayList<>();

    private final HashMap<Integer, ArrayList<Message>> messageData = new HashMap<>();

    private final HashMap<Integer, ArrayList<String[]>> chatRoomMemberData = new HashMap<>();

    public static DataProvider getInstance() {
        if (instance == null)
            instance = new DataProvider();
        return instance;
    }

    public ArrayList<Member> getMemberData() {
        return memberData;
    }

    public void loadMemberData() {
        memberData = LoadFriendService.getInstance().loadFriend();
    }

    public void addMemberData(Member member) {
        memberData.add(member);
        Collections.sort(memberData);
    }

    public Member getMember(String userId) {
        for (int i = 0; i < memberData.size(); i++) {
            if (userId.equals(memberData.get(i).getUserId()))
                return memberData.get(i);
        }
        return null;
    }

    public Boolean containsMemberFromFriend(String userId) {
        for (int i = 0; i < memberData.size(); i++) {
            if (userId.equals(memberData.get(i).getUserId()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<ChatRoom> getChatRoomData() {
        return chatRoomData;
    }

    public void loadChatRoomData() {
        chatRoomData = LoadChatRoomService.getInstance().loadChatRoom();
    }

    public ChatRoom getChatRoom(int roomId) {
        for (int i = 0; i < chatRoomData.size(); i++) {
            if (roomId == chatRoomData.get(i).getRoomId())
                return chatRoomData.get(i);
        }
        return null;
    }

    public Boolean containsMemberFromChatRoom(String userId, int roomId) {
        ArrayList<String[]> chatRoomMemberList = chatRoomMemberData.get(roomId);
        for (int i = 0; i < chatRoomMemberList.size(); i++) {
            if (userId.equals(chatRoomMemberList.get(i)[0]))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public int getChatRoomId(String name) {
        for(int i=0; i< chatRoomData.size(); i++) {
            if(name.equals(chatRoomData.get(i).getName())) {
                return chatRoomData.get(i).getRoomId();
            }
        }
        return -1;
    }

    public void loadMessageData(int roomId) {
        messageData.put(roomId, LoadMessageService.getInstance().loadMessage(roomId));
    }

    public ArrayList<Message> getMessageData(int roomId) {
        if (messageData.containsKey(roomId))
            return messageData.get(roomId);
        return null;
    }

    public void loadChatRoomMemberData(int roomId) {
        chatRoomMemberData.put(roomId, LoadChatRoomMemberService.getInstance().loadChatRoomMember(roomId));
    }

    public ArrayList<String[]> getChatRoomMemberData(int roomId) {
        if (chatRoomMemberData.containsKey(roomId))
            return chatRoomMemberData.get(roomId);
        return null;
    }
}
