
package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserId {


    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("error")
    @Expose
    private String error;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }

//    public static myNew.User getUser(myNew.User user) {
//        return user;
//    }
//
//    public void setUser(myNew.User user) {
//        this.user = user;
//    }
    public String getError() {
        return error;
    }

}
