package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Socket_commertial
{
    @SerializedName("socket")
    @Expose
    private String socket;

    public String getSocketCommertial() {
        return socket;
    }

    public void setSocketCommertial(String socket) {
        this.socket = socket;
    }

    public Socket_commertial withSockeCommertialt(String socket) {
        this.socket = socket;
        return this;
    }
}
