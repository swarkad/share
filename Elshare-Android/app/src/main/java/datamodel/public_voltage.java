package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_voltage
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltagePublic() {
        return voltage;
    }

    public void setVoltagePublic(String voltage) {
        this.voltage = voltage;
    }

    public public_voltage withVoltagePublic(String voltage) {
        this.voltage = voltage;
        return this;
    }


}
