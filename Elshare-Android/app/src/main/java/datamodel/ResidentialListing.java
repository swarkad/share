package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResidentialListing
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("address")
    @Expose
    private address_residential address;
    @SerializedName("address_id")
    @Expose
    private int address_id;
    @SerializedName("socket_id")
    @Expose
    private int socket_id;
    @SerializedName("power_output")
    @Expose
    private String power_output;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAddress_Id() {
        return address_id;
    }
    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getSocket_id() {
        return socket_id;
    }
    public void setSocket_id(int socket_id) {
        this.socket_id = socket_id;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPower_output() {
        return power_output;
    }

    public void setPower_output(String power_output) {
        this.power_output = power_output;
    }

    public  address_residential getAddress () {
        return  address;
    }





}
