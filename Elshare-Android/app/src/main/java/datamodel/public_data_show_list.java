package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_data_show_list
{
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("address_id")
    @Expose
    private Long addressId;
    @SerializedName("business_mobile")
    @Expose
    private Object businessMobile;
    @SerializedName("provider_id")
    @Expose
    private Long providerId;
    @SerializedName("charger_id")
    @Expose
    private Object chargerId;
    @SerializedName("socket_id")
    @Expose
    private Long socketId;
    @SerializedName("power_output")
    @Expose
    private String powerOutput;
    @SerializedName("profit")
    @Expose
    private String profit;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Object getBusinessMobile() {
        return businessMobile;
    }

    public void setBusinessMobile(Object businessMobile) {
        this.businessMobile = businessMobile;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Object getChargerId() {
        return chargerId;
    }

    public void setChargerId(Object chargerId) {
        this.chargerId = chargerId;
    }

    public Long getSocketId() {
        return socketId;
    }

    public void setSocketId(Long socketId) {
        this.socketId = socketId;
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
