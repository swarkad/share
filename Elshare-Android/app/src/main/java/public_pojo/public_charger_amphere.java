package public_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_charger_amphere
{
    @SerializedName("amphere")
    @Expose
    private String amphere;

    public String getChargerAmpherePublic() {
        return amphere;
    }

    public void setAmphere(String amphere) {
        this.amphere = amphere;
    }
}
