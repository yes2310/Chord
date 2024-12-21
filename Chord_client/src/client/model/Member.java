package client.model;

public class Member implements Comparable<Member>{

    private String userId;
    private String name;
    private String statusMessage;

    public Member(String userId, String name, String status_message) {
        this.userId = userId;
        this.name = name;
        this.statusMessage = status_message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public int compareTo(Member member) {
        return this.getName().compareTo(member.getName());
    }
}
