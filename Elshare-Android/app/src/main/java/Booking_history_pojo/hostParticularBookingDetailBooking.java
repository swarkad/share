package Booking_history_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class hostParticularBookingDetailBooking {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("host_id")
    @Expose
    private Integer hostId;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("minute")
    @Expose
    private Integer minute;
    @SerializedName("unit")
    @Expose
    private Integer unit;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("is_completed")
    @Expose
    private String isCompleted;
    @SerializedName("is_cancelled")
    @Expose
    private String isCancelled;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("is_rejected")
    @Expose
    private String isRejected;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("deduct")
    @Expose
    private Object deduct;
    @SerializedName("charged_to_host")
    @Expose
    private String chargedToHost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(String isRejected) {
        this.isRejected = isRejected;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Object getDeduct() {
        return deduct;
    }

    public void setDeduct(Object deduct) {
        this.deduct = deduct;
    }

    public String getChargedToHost() {
        return chargedToHost;
    }

    public void setChargedToHost(String chargedToHost) {
        this.chargedToHost = chargedToHost;
    }

}
