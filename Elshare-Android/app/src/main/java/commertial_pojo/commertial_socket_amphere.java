package commertial_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_socket_amphere
{
    @SerializedName("amphere")
    @Expose
    private String amphere;

    public String getSocketAmphereCom() {
        return amphere;
    }

    public void setAmphere(String amphere) {
        this.amphere = amphere;
    }

}
