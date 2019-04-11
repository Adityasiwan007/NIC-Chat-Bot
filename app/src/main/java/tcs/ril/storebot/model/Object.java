
package tcs.ril.storebot.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Object {

    @SerializedName("displayText")
    @Expose
    private String displayText;
    @SerializedName("view")
    @Expose
    private ViewBtn view;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("controls")
    @Expose
    private List<Control> controls = null;
    @SerializedName("auxiliary")
    @Expose
    private String auxiliary;

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public ViewBtn getViewBtn() {
        return view;
    }

    public void setViewBtn(ViewBtn view) {
        this.view = view;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Control> getControls() {
        return controls;
    }

    public void setControls(List<Control> controls) {
        this.controls = controls;
    }

    public String getAuxiliary() { return auxiliary; }

    public void setAuxiliary(String auxiliary) { this.auxiliary = auxiliary; }
}
