package Booking_history_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HostBookingHistoryDetail
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("host_id")
    @Expose
    private Integer hostId;
    @SerializedName("residential_id")
    @Expose
    private Integer residentialId;
    @SerializedName("commercial_id")
    @Expose
    private Integer commercialId;
    @SerializedName("public_id")
    @Expose
    private Integer publicId;
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
    private Integer isCompleted;
    @SerializedName("is_cancelled")
    @Expose
    private Integer isCancelled;
    @SerializedName("is_approved")
    @Expose
    private Integer isApproved;
    @SerializedName("is_rejected")
    @Expose
    private Integer isRejected;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("deduct")
    @Expose
    private Double deduct;
    @SerializedName("charged_to_host")
    @Expose
    private String chargedToHost;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("payment")
    @Expose
    private HostBookingHistoryPayment payment;

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

    public Integer getResidentialId() {
        return residentialId;
    }

    public void setResidentialId(Integer residentialId) {
        this.residentialId = residentialId;
    }

    public Integer getCommercialId() {
        return commercialId;
    }

    public void setCommercialId(Integer commercialId) {
        this.commercialId = commercialId;
    }

    public Integer getPublicId() {
        return publicId;
    }

    public void setPublicId(Integer publicId) {
        this.publicId = publicId;
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

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Integer getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Integer isApproved) {
        this.isApproved = isApproved;
    }

    public Integer getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Integer isRejected) {
        this.isRejected = isRejected;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
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

    public Double getDeduct() {
        return deduct;
    }

    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }

    public String getChargedToHost() {
        return chargedToHost;
    }

    public void setChargedToHost(String chargedToHost) {
        this.chargedToHost = chargedToHost;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public HostBookingHistoryPayment getPayment() {
        return payment;
    }

    public void setPayment(HostBookingHistoryPayment payment) {
        this.payment = payment;
    }

}
