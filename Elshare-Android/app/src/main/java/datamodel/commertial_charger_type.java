package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_charger_type
{
    @SerializedName("charger_brand")
    @Expose
    private String chargerBrand;

    public String getChargerBrand() {
        return chargerBrand;
    }

    public void setChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
    }

    public commertial_charger_type withChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
        return this;
    }
}
