package client.model;

import java.util.Date;

public class ChatRoom implements Comparable<ChatRoom> {

    private int roomId;
    private String roomType;
    private String name;
    private int headcount;
    private String lastMessage;
    private Date lastTime;
    private int unreadMessageCount;

    public ChatRoom(int roomId, String roomType, String name, int headcount, String lastMessage, Date lastTime, int unreadMessageCount) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.name = name;
        this.headcount = headcount;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
        this.unreadMessageCount = unreadMessageCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    @Override
    public int compareTo(ChatRoom chatRoom) {
        if (this.getLastTime().before(chatRoom.getLastTime()))
            return 1;
        else if (this.getLastTime().after(chatRoom.getLastTime()))
            return -1;
        else
            return 0;
    }
}
