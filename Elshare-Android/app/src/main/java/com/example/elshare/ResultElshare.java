package com.example.elshare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import datamodel.User;
import datamodel.UserId;

public class ResultElshare {

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("success")
    @Expose
    private String success;

    public String getError() {
        return error;
    }


    @SerializedName("user")
    private UserId user;

    public ResultElshare(String error, String success, UserId user) {
        this.error = error;
        this.success = success;
        this.user = user;
    }

    public String getMessage() {
        return success;
    }

    public void setMessage(String success) {
        this.success = success;
    }


}
