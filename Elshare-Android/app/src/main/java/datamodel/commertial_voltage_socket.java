package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_voltage_socket
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltageComSocket() {
        return voltage;
    }

    public void setVoltageComSocket(String voltage) {
        this.voltage = voltage;
    }

    public commertial_voltage_socket withVoltageComSocket(String voltage) {
        this.voltage = voltage;
        return this;
    }


}
