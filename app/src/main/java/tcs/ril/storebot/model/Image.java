package tcs.ril.storebot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("url")
    @Expose
    private String url;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
