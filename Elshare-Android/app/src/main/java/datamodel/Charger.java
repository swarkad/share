
package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Charger {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Object voltage;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Charger withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Charger withHost(String host) {
        this.host = host;
        return this;
    }

    public String getChargerBrand() {
        return chargerBrand;
    }

    public void setChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
    }

    public Charger withChargerBrand(String chargerBrand) {
        this.chargerBrand = chargerBrand;
        return this;
    }

    public String getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(String chargerModel) {
        this.chargerModel = chargerModel;
    }

    public Charger withChargerModel(String chargerModel) {
        this.chargerModel = chargerModel;
        return this;
    }

    public String getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public Charger withPowerOutput(String powerOutput) {
        this.powerOutput = powerOutput;
        return this;
    }

    public Object getVoltage() {
        return voltage;
    }

    public void setVoltage(Object voltage) {
        this.voltage = voltage;
    }

    public Charger withVoltage(Object voltage) {
        this.voltage = voltage;
        return this;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public Charger withConnectorType(String connectorType) {
        this.connectorType = connectorType;
        return this;
    }

    public Double getRateStructure() {
        return rateStructure;
    }

    public void setRateStructure(Double rateStructure) {
        this.rateStructure = rateStructure;
    }

    public Charger withRateStructure(Double rateStructure) {
        this.rateStructure = rateStructure;
        return this;
    }

    public String getElectricityBoard() {
        return electricityBoard;
    }

    public void setElectricityBoard(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public Charger withElectricityBoard(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Charger withCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Charger withUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
