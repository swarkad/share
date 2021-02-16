package residential_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_charger_amphere {


    @SerializedName("amphere")
    @Expose
    private String amphere;

    public String getChargerAmphereRes() {
        return amphere;
    }

    public void setAmphere(String amphere) {
        this.amphere = amphere;
    }
}
