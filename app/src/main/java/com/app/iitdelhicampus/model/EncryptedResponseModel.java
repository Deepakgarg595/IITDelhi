package com.app.iitdelhicampus.model;

/**
 * Created by nancy on 26/4/17.
 */

public class EncryptedResponseModel {
    String response;
    String requestId;
    String serverTime;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
