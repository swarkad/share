package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_charegr_brand
{

    @SerializedName("charger_brand")
    @Expose
    private String chargerBrand;

    public String getChargerBrandPublic() {
        return chargerBrand;
    }

    public void setChargerBrandPublic(String chargerBrand) {
        this.chargerBrand = chargerBrand;
    }

    public public_charegr_brand withChargerBrandPublic(String chargerBrand) {
        this.chargerBrand = chargerBrand;
        return this;
    }

}
