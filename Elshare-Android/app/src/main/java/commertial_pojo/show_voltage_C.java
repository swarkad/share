package commertial_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class show_voltage_C {
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
