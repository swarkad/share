package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationHost
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("notifiable_type")
    @Expose
    private String notifiableType;
    @SerializedName("notifiable_id")
    @Expose
    private Integer notifiableId;
    @SerializedName("data")
    @Expose
    private NotificationHostData data;
    @SerializedName("is_accepted")
    @Expose
    private Integer isAccepted;
    @SerializedName("read_at")
    @Expose
    private String readAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotifiableType() {
        return notifiableType;
    }

    public void setNotifiableType(String notifiableType) {
        this.notifiableType = notifiableType;
    }

    public Integer getNotifiableId() {
        return notifiableId;
    }

    public void setNotifiableId(Integer notifiableId) {
        this.notifiableId = notifiableId;
    }

    public NotificationHostData getData() {
        return data;
    }

    public void setData(NotificationHostData data) {
        this.data = data;
    }

    public Integer getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Integer isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
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
