package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOSModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sos_code")
    @Expose
    private String sosCode;
    @SerializedName("sos_msg")
    @Expose
    private String sosMessage;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSosCode() {
        return sosCode;
    }

    public void setSosCode(String sosCode) {
        this.sosCode = sosCode;
    }

    public String getSosMessage() {
        return sosMessage;
    }

    public void setSosMessage(String sosMessage) {
        this.sosMessage = sosMessage;
    }

}
