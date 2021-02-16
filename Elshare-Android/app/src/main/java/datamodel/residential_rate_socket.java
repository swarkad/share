package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_rate_socket
{

    @SerializedName("rate_structure")
    @Expose
    private String rateStructure;

    public String getRateStructureSocketRes() {
        return rateStructure;
    }

    public void setRateStructureSocketRes(String rateStructure) {
        this.rateStructure = rateStructure;
    }

    public residential_rate_socket withRateStructureSocketRes(String rateStructure) {
        this.rateStructure = rateStructure;
        return this;
    }

}
