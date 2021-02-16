package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_rate_socket
{

    @SerializedName("rate_structure")
    @Expose
    private String rateStructure;

    public String getRateStructureCommertialSocket() {
        return rateStructure;
    }

    public void setRateStructureCommertialSocket(String rateStructure) {
        this.rateStructure = rateStructure;
    }

    public commertial_rate_socket withRateStructureCommertialSocket(String rateStructure) {
        this.rateStructure = rateStructure;
        return this;
    }

}
