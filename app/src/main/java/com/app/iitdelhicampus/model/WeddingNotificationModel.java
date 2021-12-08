package com.app.iitdelhicampus.model;

import java.util.ArrayList;

public class WeddingNotificationModel {

    private boolean status;
    private String totalCount;
    private ArrayList<NotificationContentModel> allData;
    private String currentPage;
    private String nextPage;
    private String requestId;



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<NotificationContentModel> getAllData() {
        return allData;
    }

    public void setAllData(ArrayList<NotificationContentModel> allData) {
        this.allData = allData;
    }


    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
