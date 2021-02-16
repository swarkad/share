package datamodel.residential_show_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargerType
{
    @SerializedName("charger_type")
    @Expose
    private String chargerType;

    public String getChargerType() {
        return chargerType;
    }

    public void setChargerType(String chargerType) {
        this.chargerType = chargerType;
    }
}
