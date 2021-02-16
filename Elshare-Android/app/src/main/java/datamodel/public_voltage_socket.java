package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_voltage_socket
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltageSocketPublic() {
        return voltage;
    }

    public void setVoltageSocketPublic(String voltage) {
        this.voltage = voltage;
    }

    public public_voltage_socket withVoltageSocketPublic(String voltage) {
        this.voltage = voltage;
        return this;
    }


}
