package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class address_residential
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("address_1")
    @Expose
    private String address_1;
    @SerializedName("address_2")
    @Expose
    private String address_2;
    @SerializedName("city")
    @Expose
    private String city;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address2) {
        this.address_2 = address_2;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
