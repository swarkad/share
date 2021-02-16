
package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Make {

    @SerializedName("make")
    @Expose
    private String my_make;

    public String getMy_make() {
        return my_make;
    }

    public void setMy_make(String my_make) {
        this.my_make =my_make;
    }

}
