package tcs.ril.storebot.model;import com.google.gson.annotations.Expose;import com.google.gson.annotations.SerializedName;public class Control {    @SerializedName("controlType")    @Expose    private String controlType;    @SerializedName("text")    @Expose    private String text;    @SerializedName("value")    @Expose    private String value;    public String getControlType() {        return controlType;    }    public void setControlType(String controlType) {        this.controlType = controlType;    }    public String getText() {        return text;    }    public void setText(String text) {        this.text = text;    }    public String getValue() {        return value;    }    public void setValue(String value) {        this.value = value;    }}