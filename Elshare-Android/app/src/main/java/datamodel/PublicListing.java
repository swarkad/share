package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicListing
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private address_residential address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  address_residential getAddress () {
        return  address;
    }





}
