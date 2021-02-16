package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommertialListing
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("address_id")
    @Expose
    private int address_id;
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

    public int getAddress_Id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
    public  address_residential getAddress () {
        return  address;
    }





}
