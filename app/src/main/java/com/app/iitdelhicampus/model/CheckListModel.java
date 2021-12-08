package com.app.iitdelhicampus.model;

import java.util.ArrayList;

public class CheckListModel {
    private boolean perimeterRoundTaken;
    private boolean securityGuardAvailable;
    private boolean acoutdoorUnitOK;
    private String latitude;
    private String empCode;
    private String userId;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    private String designation;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    private String clientCode;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeam_lead_id() {
        return team_lead_id;
    }

    public void setTeam_lead_id(String team_lead_id) {
        this.team_lead_id = team_lead_id;
    }

    public String getTeamlead() {
        return teamlead;
    }

    public void setTeamlead(String teamlead) {
        this.teamlead = teamlead;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String team_lead_id;
    private String teamlead;
    private String department;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    private ArrayList<String> images;

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

    private String longitude;

    private String countryCode;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

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

    public String getTeamLeadName() {
        return teamLeadName;
    }

    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSolId() {
        return solId;
    }

    public void setSolId(String solId) {
        this.solId = solId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    private String teamLeadName;
    private String clientName;
    private String solId;
    private String createdDate;




    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    private String date;
    private String lastDate;

    public boolean isPerimeterRoundTaken() {
        return perimeterRoundTaken;
    }

    public void setPerimeterRoundTaken(boolean perimeterRoundTaken) {
        this.perimeterRoundTaken = perimeterRoundTaken;
    }

    public boolean isSecurityGuardAvailable() {
        return securityGuardAvailable;
    }

    public void setSecurityGuardAvailable(boolean securityGuardAvailable) {
        this.securityGuardAvailable = securityGuardAvailable;
    }

    public boolean isAcoutdoorUnitOK() {
        return acoutdoorUnitOK;
    }

    public void setAcoutdoorUnitOK(boolean acoutdoorUnitOK) {
        this.acoutdoorUnitOK = acoutdoorUnitOK;
    }

    public boolean isBranchLightandACOff() {
        return branchLightandACOff;
    }

    public void setBranchLightandACOff(boolean branchLightandACOff) {
        this.branchLightandACOff = branchLightandACOff;
    }

    public boolean isBranchShutterLockTestedChecked() {
        return branchShutterLockTestedChecked;
    }

    public void setBranchShutterLockTestedChecked(boolean branchShutterLockTestedChecked) {
        this.branchShutterLockTestedChecked = branchShutterLockTestedChecked;
    }

    public boolean isSignatureWorking() {
        return signatureWorking;
    }

    public void setSignatureWorking(boolean signatureWorking) {
        this.signatureWorking = signatureWorking;
    }

    public boolean isAtmWorking() {
        return atmWorking;
    }

    public void setAtmWorking(boolean atmWorking) {
        this.atmWorking = atmWorking;
    }

    public boolean isCheckedATMSkimmingDeviceFound() {
        return checkedATMSkimmingDeviceFound;
    }

    public void setCheckedATMSkimmingDeviceFound(boolean checkedATMSkimmingDeviceFound) {
        this.checkedATMSkimmingDeviceFound = checkedATMSkimmingDeviceFound;
    }

    public boolean isAtmACWorking() {
        return atmACWorking;
    }

    public void setAtmACWorking(boolean atmACWorking) {
        this.atmACWorking = atmACWorking;
    }

    public boolean isAtmroomClean() {
        return atmroomClean;
    }

    public void setAtmroomClean(boolean atmroomClean) {
        this.atmroomClean = atmroomClean;
    }

    public boolean isFireExtinguisherAvailable() {
        return fireExtinguisherAvailable;
    }

    public void setFireExtinguisherAvailable(boolean fireExtinguisherAvailable) {
        this.fireExtinguisherAvailable = fireExtinguisherAvailable;
    }

    public boolean isAtmbackRoomLocked() {
        return atmbackRoomLocked;
    }

    public void setAtmbackRoomLocked(boolean atmbackRoomLocked) {
        this.atmbackRoomLocked = atmbackRoomLocked;
    }

    public boolean isDgsetBatteryChecked() {
        return dgsetBatteryChecked;
    }

    public void setDgsetBatteryChecked(boolean dgsetBatteryChecked) {
        this.dgsetBatteryChecked = dgsetBatteryChecked;
    }

    public boolean isPolicePatrolMet() {
        return policePatrolMet;
    }

    public void setPolicePatrolMet(boolean policePatrolMet) {
        this.policePatrolMet = policePatrolMet;
    }

    public boolean isPoliceStationVisitChecked() {
        return policeStationVisitChecked;
    }

    public void setPoliceStationVisitChecked(boolean policeStationVisitChecked) {
        this.policeStationVisitChecked = policeStationVisitChecked;
    }

    public String getAnyOtherObservation() {
        return anyOtherObservation;
    }

    public void setAnyOtherObservation(String anyOtherObservation) {
        this.anyOtherObservation = anyOtherObservation;
    }

    private boolean branchLightandACOff;
    private boolean branchShutterLockTestedChecked;
    private boolean signatureWorking;
    private boolean atmWorking;
    private boolean checkedATMSkimmingDeviceFound;
    private boolean atmACWorking;
    private boolean atmroomClean;
    private boolean fireExtinguisherAvailable;
    private boolean atmbackRoomLocked;
    private boolean dgsetBatteryChecked;
    private boolean policePatrolMet;
    private boolean policeStationVisitChecked;
    private String anyOtherObservation;


}