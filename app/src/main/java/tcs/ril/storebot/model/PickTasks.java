
package tcs.ril.storebot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickTasks {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("object")
    @Expose
    private Pick pick;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Pick getPick() {
        return pick;
    }

    public void setPick(Pick pick) {
        this.pick = pick;
    }

}
