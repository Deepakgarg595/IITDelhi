package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginManagerModel {
    @SerializedName("success")
    @Expose
    private List<SuccessLoginModel> success = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tstatus")
    @Expose
    private String tstatus;

    public List<SuccessLoginModel> getSuccess() {
        return success;
    }

    public void setSuccess(List<SuccessLoginModel> success) {
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
