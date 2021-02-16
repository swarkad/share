package commertial_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class show_amphere_C {
    @SerializedName("amphere")
    @Expose
    private String amphere;

    public String getAmphere() {
        return amphere;
    }

    public void setAmphere(String amphere) {
        this.amphere = amphere;
    }
}
