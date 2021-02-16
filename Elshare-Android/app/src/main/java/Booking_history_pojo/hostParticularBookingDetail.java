package Booking_history_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class hostParticularBookingDetail
{
    @SerializedName("booking")
    @Expose
    private hostParticularBookingDetailBooking booking;
    @SerializedName("payment")
    @Expose
    private HostBookingHistoryPayment payment;
    @SerializedName("address")
    @Expose
    private hostParticularBookingDetailAddress address;


    public hostParticularBookingDetailBooking getBooking() {
        return booking;
    }

    public void setBooking(hostParticularBookingDetailBooking booking) {
        this.booking = booking;
    }

    public HostBookingHistoryPayment getPayment() {
        return payment;
    }

    public void setPayment(HostBookingHistoryPayment payment) {
        this.payment = payment;
    }

    public hostParticularBookingDetailAddress getAddress() {
        return address;
    }

    public void setAddress(hostParticularBookingDetailAddress address) {
        this.address = address;
    }
}
