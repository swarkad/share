package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class charger_commertial_show_list
{
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("charger_brand")
    @Expose
    private String chargerBrand;
    @SerializedName("charger_model")
    @Expose
    private String chargerModel;
    @SerializedName("power_output")
    @Expose
    private String powerOutput;
    @SerializedName("voltage")
    @Expose
    private String voltage;
    @SerializedName("connector_type")
    @Expose
    private String connectorType;
    @SerializedName("rate_structure")
    @Expose
    private Double rateStructure;
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getChargerBrand() {
        return chargerBrand;
    }

    public void setChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
    }

    public String getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(String chargerModel) {
        this.chargerModel = chargerModel;
    }

    public String getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public Double getRateStructure() {
        return rateStructure;
    }

    public void setRateStructure(Double rateStructure) {
        this.rateStructure = rateStructure;
    }

    public String getElectricityBoard() {
        return electricityBoard;
    }

    public void setElectricityBoard(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
