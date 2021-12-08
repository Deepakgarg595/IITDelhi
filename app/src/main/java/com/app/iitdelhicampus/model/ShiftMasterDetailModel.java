package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShiftMasterDetailModel {
    @SerializedName("ShiftId")
    @Expose
    private String shiftId;
    @SerializedName("IsSelected")
    @Expose
    private boolean isSelected;
    @SerializedName("ShiftName")
    @Expose
    private String shiftName;
    @SerializedName("ShiftSName")
    @Expose
    private String shiftSName;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getShiftSName() {
        return shiftSName;
    }

    public void setShiftSName(String shiftSName) {
        this.shiftSName = shiftSName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
