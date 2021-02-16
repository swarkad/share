
package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("user")
    @Expose
    private User1 user;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Register withError(String error) {
        this.error = error;
        return this;
    }


    public User1 getUser() {
        return user;
    }


    public void setUser(User1 user) {
        this.user = user;
    }

    public Register withUser(User1 user) {
        this.user = user;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Register withToken(String token) {
        this.token = token;
        return this;
    }

}
