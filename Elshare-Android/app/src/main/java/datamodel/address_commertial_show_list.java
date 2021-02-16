package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class address_commertial_show_list
{
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("pin")
    @Expose
    private Long pin;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPin() {
        return pin;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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




