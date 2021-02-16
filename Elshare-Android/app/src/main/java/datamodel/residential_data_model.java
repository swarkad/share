package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_data_model
{
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("address_id")
    @Expose
    private int address_id;

    @SerializedName("mYear")
    @Expose
    private long mYear;



    public int getId() {
        return id;
    }
    public int getaddress_id() {
        return address_id;
    }


    public long getmYear() {
        return mYear;
    }
}
