package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfileUser
{

    @SerializedName("user")
    @Expose
    private myProfileUserDetail user;

    public myProfileUserDetail getUser() {
        return user;
    }

    public void setUser(myProfileUserDetail user) {
        this.user = user;
    }
}
