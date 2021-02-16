package Booking_history_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HostBookingHistoryPojo
{
    @SerializedName("data")
    @Expose
    private List<HostBookingHistoryDetail> data;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("last_page")
    @Expose
    private String last_page;
    @SerializedName("current_page")
    @Expose
    private Integer current_page;
    @SerializedName("to")
    @Expose
    private Integer to;



    public List<HostBookingHistoryDetail> getData() {
        return data;
    }

    public void setData(List<HostBookingHistoryDetail> payment) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }
    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }
    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }


}

