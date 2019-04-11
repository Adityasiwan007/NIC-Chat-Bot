
package tcs.ril.storebot.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("varient")
    @Expose
    private Varient varient;
    @SerializedName("name")
    @Expose
    private String name;

    public Varient getVarient() {
        return varient;
    }

    public void setVarient(Varient varient) {
        this.varient = varient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
