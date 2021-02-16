package commertial_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class show_chargerModel_C {
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
