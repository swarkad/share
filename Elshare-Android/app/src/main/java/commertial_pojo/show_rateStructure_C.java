package commertial_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class show_rateStructure_C
{
    @SerializedName("rate_structure")
    @Expose
    private String rateStructure;

    public String getRateStructure() {
        return rateStructure;
    }

    public void setRateStructure(String rateStructure) {
        this.rateStructure = rateStructure;
    }
}
