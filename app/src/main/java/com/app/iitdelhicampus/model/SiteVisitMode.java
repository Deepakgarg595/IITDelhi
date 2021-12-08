package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SiteVisitMode implements Parcelable {
    public SiteVisitMode(){

    }
    protected SiteVisitMode(Parcel in) {
        reportId = in.readString();
        visitDate = in.readString();
        lastVisitDate = in.readString();
        siteCode = in.readString();
        clientName = in.readString();
        deploymentLocation = in.readString();
        serviceType = in.readString();
        serviceTypeOther = in.readString();
        numberOfManpower = in.readString();
        shortage = in.readString();
        isSOPIssued = in.readByte() != 0;
        sopReason = in.readString();
        uniformAccessory = in.readString();
        guardGrooming = in.readString();
        guardUniform = in.readString();
        guardAwarenessOfDuty = in.readString();
        isLastNightCheck = in.readByte() != 0;
        lastNightCheckDate = in.readString();
        lastNightCheckReason = in.readString();
        isLastTrainingConducted = in.readByte() != 0;
        lastTrainingDate = in.readString();
        lastTrainingReason = in.readString();
        lastInvoice = in.readString();
        paymentReceivedMonth = in.readString();
        metWhom = in.readString();
        anyClientConcern = in.readString();
        anySecurityHazard = in.readString();
        anyIncidentInLastWeek = in.readByte() != 0;
        incidentDate = in.readString();
        incidentReason = in.readString();

        securityDate = in.readString();
        securityReason = in.readString();
        remark = in.readString();
        createdDate = in.readString();
        countryCode = in.readString();
        mobile = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        userId = in.readString();
        empCode = in.readString();
        name = in.readString();
        lastNightChecObservation=in.readString();
        lastTrainingSubjectCovered=in.readString();
        securityHazardList=in.createTypedArrayList(CommonDropdownModel.CREATOR);
        isSecurityHazardStatus = in.readByte() != 0;
        securityEquipmentProvided = in.readByte() != 0;
        securityEquipmentProvidedList=in.createTypedArrayList(CommonDropdownModel.CREATOR);
        clientLocation=in.readString();
        images = in.createStringArrayList();
        designation=in.readString();
        branchId=in.readString();
        branchName=in.readString();

    }

    public static final Creator<SiteVisitMode> CREATOR = new Creator<SiteVisitMode>() {
        @Override
        public SiteVisitMode createFromParcel(Parcel in) {
            return new SiteVisitMode(in);
        }

        @Override
        public SiteVisitMode[] newArray(int size) {
            return new SiteVisitMode[size];
        }
    };

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    private String reportId;
    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDeploymentLocation() {
        return deploymentLocation;
    }

    public void setDeploymentLocation(String deploymentLocation) {
        this.deploymentLocation = deploymentLocation;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getNumberOfManpower() {
        return numberOfManpower;
    }

    public void setNumberOfManpower(String numberOfManpower) {
        this.numberOfManpower = numberOfManpower;
    }

    public String getShortage() {
        return shortage;
    }

    public void setShortage(String shortage) {
        this.shortage = shortage;
    }

    public boolean isSOPIssued() {
        return isSOPIssued;
    }

    public void setSOPIssued(boolean SOPIssued) {
        isSOPIssued = SOPIssued;
    }

    public String getSopReason() {
        return sopReason;
    }

    public void setSopReason(String sopReason) {
        this.sopReason = sopReason;
    }

    public String getUniformAccessory() {
        return uniformAccessory;
    }

    public void setUniformAccessory(String uniformAccessory) {
        this.uniformAccessory = uniformAccessory;
    }

    public String getGuardGrooming() {
        return guardGrooming;
    }

    public void setGuardGrooming(String guardGrooming) {
        this.guardGrooming = guardGrooming;
    }

    public String getGuardUniform() {
        return guardUniform;
    }

    public void setGuardUniform(String guardUniform) {
        this.guardUniform = guardUniform;
    }

    public String getGuardAwarenessOfDuty() {
        return guardAwarenessOfDuty;
    }

    public void setGuardAwarenessOfDuty(String guardAwarenessOfDuty) {
        this.guardAwarenessOfDuty = guardAwarenessOfDuty;
    }

    public boolean isLastNightCheck() {
        return isLastNightCheck;
    }

    public void setLastNightCheck(boolean lastNightCheck) {
        isLastNightCheck = lastNightCheck;
    }

    public String getLastNightCheckDate() {
        return lastNightCheckDate;
    }

    public void setLastNightCheckDate(String lastNightCheckDate) {
        this.lastNightCheckDate = lastNightCheckDate;
    }

    public String getLastNightCheckReason() {
        return lastNightCheckReason;
    }

    public void setLastNightCheckReason(String lastNightCheckReason) {
        this.lastNightCheckReason = lastNightCheckReason;
    }

    public boolean isLastTrainingConducted() {
        return isLastTrainingConducted;
    }

    public void setLastTrainingConducted(boolean lastTrainingConducted) {
        isLastTrainingConducted = lastTrainingConducted;
    }

    public String getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(String lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }

    public String getLastTrainingReason() {
        return lastTrainingReason;
    }

    public void setLastTrainingReason(String lastTrainingReason) {
        this.lastTrainingReason = lastTrainingReason;
    }

    public String getLastInvoice() {
        return lastInvoice;
    }

    public void setLastInvoice(String lastInvoice) {
        this.lastInvoice = lastInvoice;
    }

    public String getPaymentReceivedMonth() {
        return paymentReceivedMonth;
    }

    public void setPaymentReceivedMonth(String paymentReceivedMonth) {
        this.paymentReceivedMonth = paymentReceivedMonth;
    }

    public String getMetWhom() {
        return metWhom;
    }

    public void setMetWhom(String metWhom) {
        this.metWhom = metWhom;
    }

    public String getAnyClientConcern() {
        return anyClientConcern;
    }

    public void setAnyClientConcern(String anyClientConcern) {
        this.anyClientConcern = anyClientConcern;
    }

    public String getAnySecurityHazard() {
        return anySecurityHazard;
    }

    public void setAnySecurityHazard(String anySecurityHazard) {
        this.anySecurityHazard = anySecurityHazard;
    }

    public boolean isAnyIncidentInLastWeek() {
        return anyIncidentInLastWeek;
    }

    public void setAnyIncidentInLastWeek(boolean anyIncidentInLastWeek) {
        this.anyIncidentInLastWeek = anyIncidentInLastWeek;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentReason() {
        return incidentReason;
    }

    public void setIncidentReason(String incidentReason) {
        this.incidentReason = incidentReason;
    }

    public boolean isSecurityEquipmentProvided() {
        return securityEquipmentProvided;
    }

    public void setSecurityEquipmentProvided(boolean securityEquipmentProvided) {
        this.securityEquipmentProvided = securityEquipmentProvided;
    }

    public String getSecurityDate() {
        return securityDate;
    }

    public void setSecurityDate(String securityDate) {
        this.securityDate = securityDate;
    }

    public String getSecurityReason() {
        return securityReason;
    }

    public void setSecurityReason(String securityReason) {
        this.securityReason = securityReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    private String visitDate;
    private String lastVisitDate;
    private String siteCode;
    private String clientName;
    private String deploymentLocation;
    private String serviceType;

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    private String clientLocation;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    private ArrayList<String> images;


    public String getServiceTypeOther() {
        return serviceTypeOther;
    }

    public void setServiceTypeOther(String serviceTypeOther) {
        this.serviceTypeOther = serviceTypeOther;
    }

    private String serviceTypeOther;
    private String numberOfManpower;
    private String shortage;
    private boolean isSOPIssued;
    private String sopReason ;
    private String uniformAccessory;
    private String guardGrooming;
    private String guardUniform;
    private String guardAwarenessOfDuty;
    private boolean isLastNightCheck;
    private String lastNightCheckDate;
    private String lastNightCheckReason;

    public boolean isSecurityHazardStatus() {
        return isSecurityHazardStatus;
    }

    public void setSecurityHazardStatus(boolean securityHazardStatus) {
        isSecurityHazardStatus = securityHazardStatus;
    }

    private boolean isSecurityHazardStatus;


    public String getLastNightChecObservation() {
        return lastNightChecObservation;
    }

    public void setLastNightChecObservation(String lastNightChecObservation) {
        this.lastNightChecObservation = lastNightChecObservation;
    }

    private String lastNightChecObservation;
    private boolean isLastTrainingConducted;
    private String lastTrainingDate;
    private String lastTrainingReason;

    public String getLastTrainingSubjectCovered() {
        return lastTrainingSubjectCovered;
    }

    public void setLastTrainingSubjectCovered(String lastTrainingSubjectCovered) {
        this.lastTrainingSubjectCovered = lastTrainingSubjectCovered;
    }

    private String lastTrainingSubjectCovered;
    private String lastInvoice;
    private String paymentReceivedMonth;
    private String metWhom;
    private String anyClientConcern;
    private String anySecurityHazard;
    private boolean anyIncidentInLastWeek ;
    private String incidentDate;
    private String incidentReason;

    private String securityDate;
    private String securityReason;
    private String remark;
    private String createdDate;
    private String countryCode;
    private String mobile;
    private String latitude;
    private String longitude;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    private String userId;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    private String designation;
    private String empCode;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    private String branchId;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    private String branchName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public ArrayList<CommonDropdownModel> getSecurityHazardList() {
        return securityHazardList;
    }

    public void setSecurityHazardList(ArrayList<CommonDropdownModel> securityHazardList) {
        this.securityHazardList = securityHazardList;
    }

    private ArrayList<CommonDropdownModel> securityHazardList;

    public ArrayList<CommonDropdownModel> getSecurityEquipmentProvidedList() {
        return securityEquipmentProvidedList;
    }

    public void setSecurityEquipmentProvidedList(ArrayList<CommonDropdownModel> securityEquipmentProvidedList) {
        this.securityEquipmentProvidedList = securityEquipmentProvidedList;
    }

    private ArrayList<CommonDropdownModel> securityEquipmentProvidedList;


    private boolean securityEquipmentProvided;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reportId);
        parcel.writeString(visitDate);
        parcel.writeString(lastVisitDate);
        parcel.writeString(siteCode);
        parcel.writeString(clientName);
        parcel.writeString(deploymentLocation);
        parcel.writeString(serviceType);
        parcel.writeString(serviceTypeOther);
        parcel.writeString(numberOfManpower);
        parcel.writeString(shortage);
        parcel.writeByte((byte) (isSOPIssued ? 1 : 0));
        parcel.writeString(sopReason);
        parcel.writeString(uniformAccessory);
        parcel.writeString(guardGrooming);
        parcel.writeString(guardUniform);
        parcel.writeString(guardAwarenessOfDuty);
        parcel.writeByte((byte) (isLastNightCheck ? 1 : 0));
        parcel.writeString(lastNightCheckDate);
        parcel.writeString(lastNightCheckReason);
        parcel.writeByte((byte) (isLastTrainingConducted ? 1 : 0));
        parcel.writeString(lastTrainingDate);
        parcel.writeString(lastTrainingReason);
        parcel.writeString(lastInvoice);
        parcel.writeString(paymentReceivedMonth);
        parcel.writeString(metWhom);
        parcel.writeString(anyClientConcern);
        parcel.writeString(anySecurityHazard);
        parcel.writeByte((byte) (anyIncidentInLastWeek ? 1 : 0));
        parcel.writeString(incidentDate);
        parcel.writeString(incidentReason);

        parcel.writeString(securityDate);
        parcel.writeString(securityReason);
        parcel.writeString(remark);
        parcel.writeString(createdDate);
        parcel.writeString(countryCode);
        parcel.writeString(mobile);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(userId);
        parcel.writeString(empCode);
        parcel.writeString(name);
        parcel.writeString(lastNightChecObservation);
        parcel.writeString(lastTrainingSubjectCovered);
        parcel.writeTypedList(securityHazardList);
        parcel.writeByte((byte) (isSecurityHazardStatus ? 1 : 0));
//        parcel.writeTypedList(securityEquipmentProvided);
        parcel.writeByte((byte) (securityEquipmentProvided ? 1 : 0));
        parcel.writeTypedList(securityEquipmentProvidedList);
        parcel.writeString(clientLocation);
        parcel.writeStringList(images);
        parcel.writeString(designation);
        parcel.writeString(branchId);
        parcel.writeString(branchName);



    }
}