package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class charger_residential_show_list
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
//    @SerializedName("id")
//    @Expose
//    private int id;
//    @SerializedName("address_1")
//    @Expose
//    private String address_1;
//    @SerializedName("address_2")
//    @Expose
//    private String address_2;
//    @SerializedName("city")
//    @Expose
//    private String city;
//    @SerializedName("landmark")
//    @Expose
//    private String landmark;
//    @SerializedName("state")
//    @Expose
//    private String state;
//    @SerializedName("country")
//    @Expose
//    private String country;
//    @SerializedName("pin")
//    @Expose
//    private int pin;
//
//    @SerializedName("lat")
//    @Expose
//    private Double lat;
//
//    @SerializedName("lng")
//    @Expose
//    private Double lng;
//
//    public int getPin() {
//        return pin;
//    }
//
//    public void setPin(int pin) {
//        this.pin = pin;
//    }
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getAddress_1() {
//        return address_1;
//    }
//
//    public void setAddress_1(String address_1) {
//        this.address_1 = address_1;
//    }
//
//    public String getAddress_2() {
//        return address_2;
//    }
//
//    public void setAddress_2(String address2) {
//        this.address_2 = address_2;
//    }
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//    public String getLandmark() {
//        return landmark;
//    }
//
//    public void setLandmark(String landmark) {
//        this.landmark = landmark;
//    }
//    public Double getLat() {
//        return lat;
//    }
//
//    public void setLat(Double lat) {
//        this.lat = lat;
//    }
//    public Double getLng() {
//        return lng;
//    }
//
//    public void setLng(Double lng) {
//        this.lng = lng;
//    }




