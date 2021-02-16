package datamodel.residential_show_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class amphere
{
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
