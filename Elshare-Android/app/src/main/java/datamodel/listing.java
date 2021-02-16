package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class listing
{
    @SerializedName("user")
    @Expose
    private driver1 driver3;

    public driver1 getDriver() {
        return driver3;
    }

    public void setDriver(driver1 driver3) {
        this.driver3 = driver3;
    }
    public listing withDriver(driver1 driver3) {
        this.driver3 = driver3;
        return this;
    }
}
