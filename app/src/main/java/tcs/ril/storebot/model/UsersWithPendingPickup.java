
package tcs.ril.storebot.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersWithPendingPickup {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("orderHistory")
    @Expose
    private List<OrderHistory> orderHistory = null;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

}
