package tcs.ril.storebot.model;

public class UserCredential {
    String userName;
    String pwd;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserCredential(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
