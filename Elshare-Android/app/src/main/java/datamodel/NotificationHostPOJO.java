package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationHostPOJO
{
    @SerializedName("notification")
    @Expose
    private List<NotificationHost> notification;

    public List<NotificationHost> getNotification() {
        return  notification;
    }
    public void setNotification(List<NotificationHost> notification) {
        this.notification = notification;
    }

}
