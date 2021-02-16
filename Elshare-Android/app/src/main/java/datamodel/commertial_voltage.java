package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_voltage
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltageComSocketCommertial() {
        return voltage;
    }

    public void setVoltageComSocketCommertial(String voltage) {
        this.voltage = voltage;
    }

    public commertial_voltage withVoltageComSocketCommertial(String voltage) {
        this.voltage = voltage;
        return this;
    }


}
