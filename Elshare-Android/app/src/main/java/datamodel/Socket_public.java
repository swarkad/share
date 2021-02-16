package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Socket_public
{

    @SerializedName("socket")
    @Expose
    private String socket;

    public String getSocketPublic() {
        return socket;
    }

    public void setSocketPublic(String socket) {
        this.socket = socket;
    }

    public Socket_public withSocketPublic(String socket) {
        this.socket = socket;
        return this;
    }
}
