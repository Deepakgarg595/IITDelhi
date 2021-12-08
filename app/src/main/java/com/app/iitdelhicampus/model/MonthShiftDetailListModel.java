package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthShiftDetailListModel {
    @SerializedName("PunchDate")
    @Expose
    private String punchDate;
    @SerializedName("EmployeeId")
    @Expose
    private String employeeId;
    @SerializedName("EmployeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("ShiftId")
    @Expose
    private String shiftId;
    @SerializedName("ShiftSName")
    @Expose
    private String shiftSName;
    @SerializedName("ShiftName")
    @Expose
    private String shiftName;

    public String getPunchDate() {
        return punchDate;
    }

    public void setPunchDate(String punchDate) {
        this.punchDate = punchDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftSName() {
        return shiftSName;
    }

    public void setShiftSName(String shiftSName) {
        this.shiftSName = shiftSName;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
}
