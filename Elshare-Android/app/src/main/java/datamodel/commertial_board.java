package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_board
{
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;

    public String getElectricityBoardCommertial() {
        return electricityBoard;
    }

    public void setElectricityBoardCommertial(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public commertial_board withElectricityBoardCommertial(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }
}
