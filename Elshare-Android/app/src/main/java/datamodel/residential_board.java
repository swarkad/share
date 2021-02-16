package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_board
{
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;

    public String getElectricityBoard() {
        return electricityBoard;
    }

    public void setElectricityBoard(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public residential_board withElectricityBoard(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }
}
