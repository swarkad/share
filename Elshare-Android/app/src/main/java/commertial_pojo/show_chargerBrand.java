package commertial_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class show_chargerBrand {
    @SerializedName("charger_brand")
    @Expose
    private String chargerBrand;

    public String getChargerBrand() {
        return chargerBrand;
    }

    public void setChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
    }

}
