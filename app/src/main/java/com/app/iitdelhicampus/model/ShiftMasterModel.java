package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShiftMasterModel {
    @SerializedName("success")
    @Expose
    private List<ShiftMasterDetailModel> success = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tstatus")
    @Expose
    private String tstatus;

    public List<ShiftMasterDetailModel> getSuccess() {
        return success;
    }

    public void setSuccess(List<ShiftMasterDetailModel> success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTstatus() {
        return tstatus;
    }

    public void setTstatus(String tstatus) {
        this.tstatus = tstatus;
    }
}
