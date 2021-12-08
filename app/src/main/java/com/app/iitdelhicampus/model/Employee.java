package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee implements Parcelable {
    @SerializedName("WarehouseId")
    @Expose
    private String warehouseId;
    @SerializedName("WarehouseName")
    @Expose
    private String warehouseName;
    @SerializedName("EmployeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Shift1")
    @Expose
    private String shift1;
    @SerializedName("ShiftName")
    @Expose
    private String shiftName;

    protected Employee(Parcel in) {
        warehouseId = in.readString();
        warehouseName = in.readString();
        employeeCode = in.readString();
        employeeName = in.readString();
        phone = in.readString();
        shift1 = in.readString();
        shiftName = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShift1() {
        return shift1;
    }

    public void setShift1(String shift1) {
        this.shift1 = shift1;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(warehouseId);
        parcel.writeString(warehouseName);
        parcel.writeString(employeeCode);
        parcel.writeString(employeeName);
        parcel.writeString(phone);
        parcel.writeString(shift1);
        parcel.writeString(shiftName);
    }
}
