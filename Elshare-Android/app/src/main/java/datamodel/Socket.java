package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Socket
{


    @SerializedName("socket")
    @Expose
    private String socket;

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public Socket withSocket(String socket) {
        this.socket = socket;
        return this;
    }
}
