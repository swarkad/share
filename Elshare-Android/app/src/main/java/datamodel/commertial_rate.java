package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_rate
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

    public commertial_rate withRateStructure(String rateStructure) {
        this.rateStructure = rateStructure;
        return this;
    }

}
