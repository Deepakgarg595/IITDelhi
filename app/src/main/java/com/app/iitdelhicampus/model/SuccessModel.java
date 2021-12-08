package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuccessModel implements Parcelable {
    @SerializedName("Employees")
    @Expose
    private List<Employee> employees = null;
    @SerializedName("WarehouseId")
    @Expose
    private String warehouseId;
    @SerializedName("WarehouseName")
    @Expose
    private String warehouseName;

    protected SuccessModel(Parcel in) {
        employees = in.createTypedArrayList(Employee.CREATOR);
        warehouseId = in.readString();
        warehouseName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(employees);
        dest.writeString(warehouseId);
        dest.writeString(warehouseName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SuccessModel> CREATOR = new Creator<SuccessModel>() {
        @Override
        public SuccessModel createFromParcel(Parcel in) {
            return new SuccessModel(in);
        }

        @Override
        public SuccessModel[] newArray(int size) {
            return new SuccessModel[size];
        }
    };

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

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
}
