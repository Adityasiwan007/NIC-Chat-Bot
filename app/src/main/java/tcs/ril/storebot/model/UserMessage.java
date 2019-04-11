package tcs.ril.storebot.model;

import java.util.List;
import java.util.Map;

public class UserMessage {
    String Message;
    public String getProfileURL() {
        return profileURL;
    }
    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    String profileURL;
    public int getTag() {
        return tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }

    int tag;
    public String getMessage() {
        return Message;
    }
    public void setMessage(String message) {
        Message = message;
    }

    int imageTag;
    public int getImageTag() {
        return imageTag;
    }
    public void setImageTag(int imageTag) {
        this.imageTag = imageTag;
    }

    int viewTag;
    public int getViewTag() { return viewTag; }
    public void setViewTag(int viewTag) { this.viewTag = viewTag; }

    String URL;
    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }

    String time;
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    List<String> btnValue;
    public List<String> getBtnValue() {
        return btnValue;
    }
    public void setBtnValue(List<String> btnValue) {
        this.btnValue = btnValue;
    }

    List<String> btnText;
    public List<String> getBtnText() {
        return btnText;
    }
    public void setBtnText(List<String> btnText) {
        this.btnText = btnText;
    }

    List<String> viewBtnTex;
    public List<String> getViewBtnTex() { return viewBtnTex; }
    public void setViewBtnTex(List<String> viewBtnTex) {this.viewBtnTex = viewBtnTex; }

    Map<String,List<OrderHistory>> viewBtnValue;

    public Map<String, List<OrderHistory>> getViewBtnValue() {
        return viewBtnValue;
    }

    public void setViewBtnValue(Map<String, List<OrderHistory>> viewBtnValue) {
        this.viewBtnValue = viewBtnValue;
    }

    int btnTag;
    public int getBtnTag() {
        return btnTag;
    }
    public void setBtnTag(int btnTag) {
        this.btnTag = btnTag;
    }

    String btnMessageText;
    public String getBtnMessageText() {return btnMessageText; }
    public void setBtnMessageText(String btnMessageText) { this.btnMessageText = btnMessageText; }

    List<UsersWithPendingPickup> pendingPickups;
    public List<UsersWithPendingPickup> getPendingPickups() { return pendingPickups; }
    public void setPendingPickups(List<UsersWithPendingPickup> pendingPickups) {this.pendingPickups = pendingPickups; }


    public int getAuxTag() {
        return auxTag;
    }

    public void setAuxTag(int auxTag) {
        this.auxTag = auxTag;
    }

    public int getAuxBtnTag() {
        return auxBtnTag;
    }

    public void setAuxBtnTag(int auxBtnTag) {
        this.auxBtnTag = auxBtnTag;
    }

    int auxTag;
    int auxBtnTag;

    String auxMessage;

    public String getAuxMessage() {
        return auxMessage;
    }

    public void setAuxMessage(String auxMessage) {
        this.auxMessage = auxMessage;
    }
}
