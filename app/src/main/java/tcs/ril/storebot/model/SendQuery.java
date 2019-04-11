package tcs.ril.storebot.model;

public class SendQuery {
    public SendQuery(String utterance) {
        this.utterance = utterance;
    }

    public String getUtterance() {
        return utterance;
    }

    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }


    String utterance;
}
