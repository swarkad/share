package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver_result {

    @SerializedName("error")
    @Expose
    private String error;


    public String getError() {
        return error;
    }
    @SerializedName("driver")
    @Expose
    private driver driver;


    public driver getDriver() {
        return driver;
    }

    public void setDriver(driver driver) {
        this.driver = driver;
    }




}
