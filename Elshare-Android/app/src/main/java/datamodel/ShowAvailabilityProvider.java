package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowAvailabilityProvider
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("address_id")
    @Expose
    private int addressId;
    @SerializedName("instant_booking")
    @Expose
    private int instantBooking;
    @SerializedName("single_day")
    @Expose
    private int singleDay;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("charger_brand")
    @Expose
    private String chargerBrand;
    @SerializedName("charger_model")
    @Expose
    private String chargerModel;
    @SerializedName("connector_type")
    @Expose
    private String connectorType;
    @SerializedName("socket")
    @Expose
    private String socket;
    @SerializedName("voltage")
    @Expose
    private String voltage;
    @SerializedName("amphere")
    @Expose
    private String amphere;
    @SerializedName("power_output")
    @Expose
    private String powerOutput;
    @SerializedName("profit")
    @Expose
    private String profit;
    @SerializedName("electricity")
    @Expose
    private String electricity;
    @SerializedName("rate_structure")
    @Expose
    private String rateStructure;
    @SerializedName("final_price")
    @Expose
    private String finalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(int instantBooking) {
        this.instantBooking = instantBooking;
    }

    public int getSingleDay() {
        return singleDay;
    }

    public void setSingleDay(int singleDay) {
        this.singleDay = singleDay;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getAmphere() {
        return amphere;
    }

    public void setAmphere(String amphere) {
        this.amphere = amphere;
    }

    public String getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(String powerOutput) {
        this.powerOutput = powerOutput;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getRateStructure() {
        return rateStructure;
    }

    public void setRateStructure(String rateStructure) {
        this.rateStructure = rateStructure;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }
}
