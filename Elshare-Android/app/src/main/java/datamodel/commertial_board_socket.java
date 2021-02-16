package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class commertial_board_socket
{
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;

    public String getElectricityBoardCommertialSocket() {
        return electricityBoard;
    }

    public void setElectricityBoardCommertialSocket(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public commertial_board_socket withElectricityBoardCommertialSocket(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }

}
