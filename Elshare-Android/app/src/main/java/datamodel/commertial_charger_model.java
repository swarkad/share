package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_charger_model
{
    @SerializedName("charger_model")
    @Expose
    private String chargerModel;

    public String getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(String chargerModel) {
        this.chargerModel = chargerModel;
    }

    public commertial_charger_model withChargerModel(String chargerModel) {
        this.chargerModel = chargerModel;
        return this;
    }
}
