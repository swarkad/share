package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_provider_object
{
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("charger_id")
    @Expose
    private Object chargerId;
    @SerializedName("socket_id")
    @Expose
    private Long socketId;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("pricing_unit")
    @Expose
    private String pricingUnit;
    @SerializedName("address_id")
    @Expose
    private Long addressId;
    @SerializedName("instant_booking")
    @Expose
    private Long instantBooking;
    @SerializedName("single_day")
    @Expose
    private Long singleDay;
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

    public String getPricingUnit() {
        return pricingUnit;
    }

    public void setPricingUnit(String pricingUnit) {
        this.pricingUnit = pricingUnit;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(Long instantBooking) {
        this.instantBooking = instantBooking;
    }

    public Long getSingleDay() {
        return singleDay;
    }

    public void setSingleDay(Long singleDay) {
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
}
