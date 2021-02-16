package datamodel.residential_show_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class show_residential_rate_structure
{
        @SerializedName("rate_structure")
        @Expose
        private String rateStructure;

        public String getRateStructure() {
            return rateStructure;
        }

        public void setRateStructure(String rateStructure) {
            this.rateStructure = rateStructure;
        }
}
