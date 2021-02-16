package datamodel.residential_show_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class charger_model
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
}
