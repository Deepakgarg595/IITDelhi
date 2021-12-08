package com.app.iitdelhicampus.model;

import java.util.ArrayList;

public class EmployeeModel {


    public ArrayList<EmpList> getSuccess() {
        return success;
    }

    public void setSuccess(ArrayList<EmpList> success) {
        this.success = success;
    }

    private ArrayList<EmpList> success;
    public class EmpList {
        String EmployeeCode;
        String EmployeeName;
        String CompanyId;
        String CompanyName;
        String LocationId;
        String LocationName;
        String BranchId;
        String BranchName;
        String WarehouseId;
        String QRCode;
        String WarehouseName;
        String Shift1;

        public String getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(String deviceId) {
            DeviceId = deviceId;
        }

        String DeviceId;

        public String getRadius() {
            return Radius;
        }

        public void setRadius(String radius) {
            Radius = radius;
        }

        String Radius;

        public String getEmployeeCode() {
            return EmployeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            EmployeeCode = employeeCode;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public void setEmployeeName(String employeeName) {
            EmployeeName = employeeName;
        }

        public String getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(String companyId) {
            CompanyId = companyId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public String getLocationId() {
            return LocationId;
        }

        public void setLocationId(String locationId) {
            LocationId = locationId;
        }

        public String getLocationName() {
            return LocationName;
        }

        public void setLocationName(String locationName) {
            LocationName = locationName;
        }

        public String getBranchId() {
            return BranchId;
        }

        public void setBranchId(String branchId) {
            BranchId = branchId;
        }

        public String getBranchName() {
            return BranchName;
        }

        public void setBranchName(String branchName) {
            BranchName = branchName;
        }

        public String getWarehouseId() {
            return WarehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            WarehouseId = warehouseId;
        }

        public String getQRCode() {
            return QRCode;
        }

        public void setQRCode(String QRCode) {
            this.QRCode = QRCode;
        }

        public String getWarehouseName() {
            return WarehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            WarehouseName = warehouseName;
        }

        public String getShift1() {
            return Shift1;
        }

        public void setShift1(String shift1) {
            Shift1 = shift1;
        }

        public String getWeeklyOff() {
            return WeeklyOff;
        }

        public void setWeeklyOff(String weeklyOff) {
            WeeklyOff = weeklyOff;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public String getAccess() {
            return Access;
        }

        public void setAccess(String access) {
            Access = access;
        }

        String WeeklyOff;
        String Latitude;
        String Longitude;
        String Access;

    }
}
