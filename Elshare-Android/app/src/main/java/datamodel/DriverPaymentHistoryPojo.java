package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverPaymentHistoryPojo
{
    @SerializedName("Billing Date")
    @Expose
    private String billingDate;
    @SerializedName("Amount (INR)")
    @Expose
    private Double amountINR;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public Double getAmountINR() {
        return amountINR;
    }

    public void setAmountINR(Double amountINR) {
        this.amountINR = amountINR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
