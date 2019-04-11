
package tcs.ril.storebot.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory {

    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("addedThrough")
    @Expose
    private List<String> addedThrough = null;
    @SerializedName("status")
    @Expose
    private List<Status> status = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("transcationID")
    @Expose
    private String transcationID;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("purchasedOn")
    @Expose
    private String purchasedOn;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<String> getAddedThrough() {
        return addedThrough;
    }

    public void setAddedThrough(List<String> addedThrough) {
        this.addedThrough = addedThrough;
    }

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTranscationID() {
        return transcationID;
    }

    public void setTranscationID(String transcationID) {
        this.transcationID = transcationID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(String purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

}
