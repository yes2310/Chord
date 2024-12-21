package client.model;

import java.util.Date;

public class Message implements Comparable<Message>{
    private int messageId;
    private String userId;
    private String userName;
    private String messageType;
    private String message;
    private Date sendTime;

    public Message(int messageId, String userId, String userName, String messageType, String message, Date sendTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.userName = userName;
        this.messageType = messageType;
        this.message = message;
        this.sendTime = sendTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public int compareTo(Message message) {
        if (this.getSendTime().after(message.getSendTime()))
            return 1;
        else if (this.getSendTime().before(message.getSendTime()))
            return -1;
        else
            return 0;
    }
}
