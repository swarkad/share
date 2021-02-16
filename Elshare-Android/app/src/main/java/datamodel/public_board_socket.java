package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class public_board_socket
{
    @SerializedName("electricity_board")
    @Expose
    private String electricityBoard;

    public String getElectricityBoardSocketPublic() {
        return electricityBoard;
    }

    public void setElectricityBoardSocketPublic(String electricityBoard) {
        this.electricityBoard = electricityBoard;
    }

    public public_board_socket withElectricityBoardSocketPublic(String electricityBoard) {
        this.electricityBoard = electricityBoard;
        return this;
    }

}
