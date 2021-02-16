package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_power_output
{
    @SerializedName("power_output")
    @Expose
    private String powerOutput;

    public String getPowerOutputRes() {
        return powerOutput;
    }

    public void setPowerOutputRes(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public residential_power_output withPowerOutputRes(String powerOutput) {
        this.powerOutput = powerOutput;
        return this;
    }
}
