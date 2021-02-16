package datamodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class charger_type {

        @SerializedName("charger_type")
        @Expose
        private String chargerType;

        public String getChargerType() {
            return chargerType;
        }

        public void setChargerType(String chargerType) {
            this.chargerType = chargerType;
        }

        public charger_type withChargerType(String chargerType) {
            this.chargerType = chargerType;
            return this;
        }


}
