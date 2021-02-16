package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_rate_socket
{

    @SerializedName("rate_structure")
    @Expose
    private String rateStructure;

    public String getRateStructureSocketPublic() {
        return rateStructure;
    }

    public void setRateStructureSocketPublic(String rateStructure) {
        this.rateStructure = rateStructure;
    }

    public public_rate_socket withRateStructureSocketPublic(String rateStructure) {
        this.rateStructure = rateStructure;
        return this;
    }

}
