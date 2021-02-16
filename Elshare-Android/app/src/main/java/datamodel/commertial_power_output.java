package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_power_output
{
    @SerializedName("power_output")
    @Expose
    private String powerOutput;

    public String getPowerOutputCommertial() {
        return powerOutput;
    }

    public void setPowerOutputCommertial(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public commertial_power_output withPowerOutputCommertial(String powerOutput) {
        this.powerOutput = powerOutput;
        return this;
    }
}
