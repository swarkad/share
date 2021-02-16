package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_voltage_socket
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltageSocketRes() {
        return voltage;
    }

    public void setVoltageSocketRes(String voltage) {
        this.voltage = voltage;
    }

    public residential_voltage_socket withVoltageSocketRes(String voltage) {
        this.voltage = voltage;
        return this;
    }


}
