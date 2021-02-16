package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class residential_board_socket
{
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;

    public String getElectricityBoardSocketRes() {
        return electricityBoard;
    }

    public void setElectricityBoardSocketRes(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public residential_board_socket withElectricityBoardSocketRes(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }

}
