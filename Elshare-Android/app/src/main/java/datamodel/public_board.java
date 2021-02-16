package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_board
{
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;

    public String getElectricityBoardPublic() {
        return electricityBoard;
    }

    public void setElectricityBoardPublic(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public public_board withElectricityBoard(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }
}
