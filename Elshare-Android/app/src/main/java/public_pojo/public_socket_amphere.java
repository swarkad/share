package public_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_socket_amphere
{
    @SerializedName("amphere")
    @Expose
    private String amphere;

    public String getSocketAmpherePublic() {
        return amphere;
    }

    public void setAmphere(String amphere) {
        this.amphere = amphere;
    }
}
