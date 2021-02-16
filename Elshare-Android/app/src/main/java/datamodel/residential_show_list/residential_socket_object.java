package datamodel.residential_show_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_socket_object
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

}
