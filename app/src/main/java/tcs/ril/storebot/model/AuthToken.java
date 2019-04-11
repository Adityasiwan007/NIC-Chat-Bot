package tcs.ril.storebot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken {

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getToken() {

        return token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("profile_img")
    @Expose
    public String url;

    public void setToken(String token) {
        this.token = token;
    }

    String message, token;
    boolean success;

}