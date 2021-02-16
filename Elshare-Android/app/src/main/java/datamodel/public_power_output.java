package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_power_output
{
    @SerializedName("power_output")
    @Expose
    private String powerOutput;

    public String getPowerOutputPublic() {
        return powerOutput;
    }

    public void setPowerOutputPublic(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public public_power_output withPowerOutputPublic(String powerOutput) {
        this.powerOutput = powerOutput;
        return this;
    }
}
