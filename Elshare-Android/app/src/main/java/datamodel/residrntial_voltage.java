package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residrntial_voltage
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltageRes() {
        return voltage;
    }

    public void setVoltageRes(String voltage) {
        this.voltage = voltage;
    }

    public residrntial_voltage withVoltageRes(String voltage) {
        this.voltage = voltage;
        return this;
    }


}
