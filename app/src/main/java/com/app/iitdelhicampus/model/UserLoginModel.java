package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserLoginModel {
    @SerializedName("success")
    @Expose
    private List<Data> success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Data> getSuccess() {
        return success;
    }

    public void setSuccess(List<Data> success) {
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

    public class Data{
        @SerializedName("employee_id")
        @Expose
        private String employeeId;
        @SerializedName("employee_code")
        @Expose
        private String employeeCode;
        @SerializedName("employee_card")
        @Expose
        private String employee_card;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("branch_id")
        @Expose
        private String branchId;

        @SerializedName("branch")
        @Expose
        private List<BranchModel> branch;

        @SerializedName("branch_code")
        @Expose
        private String branchCode;
        @SerializedName("branch_name")
        @Expose
        private String branchName;

        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("role_id")
        @Expose
        private String roleId;
        @SerializedName("role_name")
        @Expose
        private String roleName;
        @SerializedName("qr_code")
        @Expose
        private String qrCode;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("radius")
        @Expose
        private String radius;
        @SerializedName("company_ids")
        @Expose
        private String companyIds;
        @SerializedName("branch_ids")
        @Expose
        private String branchIds;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("department_id")
        @Expose
        private String departmentId;
        @SerializedName("department_name")
        @Expose
        private String departmentName;
        @SerializedName("designation_id")
        @Expose
        private String designationId;
        @SerializedName("designation_name")
        @Expose
        private String designationName;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("token_id")
        @Expose
        private String tokenId;
        @SerializedName("shift1")
        @Expose
        private String shift1;
        @SerializedName("shift_code")
        @Expose
        private String shiftCode;
        @SerializedName("shift_name")
        @Expose
        private String shiftName;
        @SerializedName("weekly_off")
        @Expose
        private String weeklyOff;
        @SerializedName("active")
        @Expose
        private String active;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public String getEmployee_card() {
            return employee_card;
        }

        public void setEmployee_card(String employee_card) {
            this.employee_card = employee_card;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public String getCompanyIds() {
            return companyIds;
        }

        public void setCompanyIds(String companyIds) {
            this.companyIds = companyIds;
        }

        public String getBranchIds() {
            return branchIds;
        }

        public void setBranchIds(String branchIds) {
            this.branchIds = branchIds;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getDesignationId() {
            return designationId;
        }

        public void setDesignationId(String designationId) {
            this.designationId = designationId;
        }

        public String getDesignationName() {
            return designationName;
        }

        public void setDesignationName(String designationName) {
            this.designationName = designationName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getShift1() {
            return shift1;
        }

        public void setShift1(String shift1) {
            this.shift1 = shift1;
        }

        public String getShiftCode() {
            return shiftCode;
        }

        public void setShiftCode(String shiftCode) {
            this.shiftCode = shiftCode;
        }

        public String getShiftName() {
            return shiftName;
        }

        public void setShiftName(String shiftName) {
            this.shiftName = shiftName;
        }

        public String getWeeklyOff() {
            return weeklyOff;
        }

        public void setWeeklyOff(String weeklyOff) {
            this.weeklyOff = weeklyOff;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public List<BranchModel> getBranch() {
            return branch;
        }

        public void setBranch(List<BranchModel> branch) {
            this.branch = branch;
        }
    }

}