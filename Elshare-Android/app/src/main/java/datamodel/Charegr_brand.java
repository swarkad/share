package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Charegr_brand
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

    public Charegr_brand withChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
        return this;
    }

}
