package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverDetail
{
    @SerializedName("user")
    @Expose
    private DriverUser user;
    public DriverUser getUser() {
        return user;
    }


    public void setUser(DriverUser user) {
        this.user = user;
    }
}
