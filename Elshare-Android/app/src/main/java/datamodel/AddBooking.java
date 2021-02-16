package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBooking
{
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("receipt")
    @Expose
    private String receipt;
    @SerializedName("razorpayId")
    @Expose
    private String razorpayId;
    @SerializedName("razorpayKey")
    @Expose
    private String razorpayKey;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contactNumber")
    @Expose
    private Double contactNumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getRazorpayId() {
        return razorpayId;
    }

    public void setRazorpayId(String razorpayId) {
        this.razorpayId = razorpayId;
    }

    public String getRazorpayKey() {
        return razorpayKey;
    }

    public void setRazorpayKey(String razorpayKey) {
        this.razorpayKey = razorpayKey;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Double contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

}
