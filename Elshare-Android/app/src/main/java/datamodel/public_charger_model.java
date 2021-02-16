package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_charger_model
{
    @SerializedName("charger_model")
    @Expose
    private String chargerModel;

    public String getChargerModelPublic() {
        return chargerModel;
    }

    public void setChargerModelPublic(String chargerModel) {
        this.chargerModel = chargerModel;
    }

    public public_charger_model withChargerModelPublic(String chargerModel) {
        this.chargerModel = chargerModel;
        return this;
    }
}
