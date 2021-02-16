package datamodel.residential_show_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class voltage
{

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
}
