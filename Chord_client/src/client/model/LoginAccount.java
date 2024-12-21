package client.model;

public class LoginAccount {

    private Member myInfo = null;

    private static LoginAccount instance = null;

    public static LoginAccount getInstance() {
        if (instance == null)
            instance = new LoginAccount();
        return instance;
    }

    public Member getMyInfo() {
        return myInfo;
    }

    public void setMyInfo(Member myInfo) {
        this.myInfo = myInfo;
    }
}
