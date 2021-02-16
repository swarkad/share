package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_rate
{

    @SerializedName("rate_structure")
    @Expose
    private String rateStructure;

    public String getRateStructurePublic() {
        return rateStructure;
    }

    public void setRateStructurePublic(String rateStructure) {
        this.rateStructure = rateStructure;
    }

    public public_rate withRateStructurePublic(String rateStructure) {
        this.rateStructure = rateStructure;
        return this;
    }

}
