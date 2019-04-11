
package tcs.ril.storebot.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pick {

    @SerializedName("associate")
    @Expose
    private Associate associate;


    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }


    @SerializedName("users")
    @Expose
    private List<String> users;

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }
}
