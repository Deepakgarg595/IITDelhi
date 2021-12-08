package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Success {
    @SerializedName("message_id")
    @Expose
    private String messageId;
    @SerializedName("message_code")
    @Expose
    private String messageCode;
    @SerializedName("message_detail")
    @Expose
    private String messageDetail;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }
}
