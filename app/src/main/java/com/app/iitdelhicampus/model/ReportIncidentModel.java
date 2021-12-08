package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ReportIncidentModel implements Parcelable {

    private ArrayList<String> images;
    private String reportId;
    private String mobile;
    private String name;
    private String medicalEvacuationTime;
    private String medicalDescription;
    private String countryCode;
    private ArrayList<CommonDropdownModel> typeOfIncident;
    private String dateOfIncident;
    private String timeOfIncident;
    private String placeOfIncident;
    private String latitude;
    private String longitude;
    private String createdDate;
    private String timeOfReporting;
    private String officerReportedAtSite;
    private String nameOfPersonInvolved;
    private String numberOfPersonInvolved;
    private String empCode;
    private String team_lead_id;
    private String teamlead;
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

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    private String reportedDate;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    private String clientName;
    private String clientCode;
    private String clientLocation;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String department;


    protected ReportIncidentModel(Parcel in) {
        images = in.createStringArrayList();
        reportId = in.readString();
        mobile = in.readString();
        name = in.readString();
        medicalEvacuationTime = in.readString();
        medicalDescription = in.readString();
        countryCode = in.readString();
        typeOfIncident = in.createTypedArrayList(CommonDropdownModel.CREATOR);
        dateOfIncident = in.readString();
        timeOfIncident = in.readString();
        placeOfIncident = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        createdDate = in.readString();
        timeOfReporting = in.readString();
        officerReportedAtSite = in.readString();
        nameOfPersonInvolved = in.readString();
        numberOfPersonInvolved = in.readString();
        numberOfSecurityPersonInvolved = in.readString();
        nameOfSecurityPersonInvolved = in.readString();
        lossStatus = in.readByte() != 0;
        lossItemList = in.createTypedArrayList(LossItemModel.CREATOR);
        incidentReported = in.readByte() != 0;
        policeReportList = in.createTypedArrayList(LocalIncidentReportedModel.CREATOR);
        anyMedicalEmergency = in.readByte() != 0;
        medicalEmergencyData = in.readString();
        courseOfAction = in.createTypedArrayList(CommonDropdownModel.CREATOR);
        observation = in.createTypedArrayList(CommonDropdownModel.CREATOR);
        correctionAction = in.createTypedArrayList(CommonDropdownModel.CREATOR);
        clientName=in.readString();
        clientCode=in.readString();
        clientLocation=in.readString();
        designation =in.readString();
        branchId=in.readString();
        branchName=in.readString();
    }

    public static final Creator<ReportIncidentModel> CREATOR = new Creator<ReportIncidentModel>() {
        @Override
        public ReportIncidentModel createFromParcel(Parcel in) {
            return new ReportIncidentModel(in);
        }

        @Override
        public ReportIncidentModel[] newArray(int size) {
            return new ReportIncidentModel[size];
        }
    };

    public String getNumberOfPersonInvolved() {
        return numberOfPersonInvolved;
    }

    public void setNumberOfPersonInvolved(String numberOfPersonInvolved) {
        this.numberOfPersonInvolved = numberOfPersonInvolved;
    }

    public String getNumberOfSecurityPersonInvolved() {
        return numberOfSecurityPersonInvolved;
    }

    public void setNumberOfSecurityPersonInvolved(String numberOfSecurityPersonInvolved) {
        this.numberOfSecurityPersonInvolved = numberOfSecurityPersonInvolved;
    }

    private String numberOfSecurityPersonInvolved;
    private String nameOfSecurityPersonInvolved;
    private boolean lossStatus;
    private ArrayList<LossItemModel> lossItemList;
    private boolean incidentReported;

    public ArrayList<LocalIncidentReportedModel> getPoliceReportList() {
        return policeReportList;
    }

    public void setPoliceReportList(ArrayList<LocalIncidentReportedModel> policeReportList) {
        this.policeReportList = policeReportList;
    }

    private ArrayList<LocalIncidentReportedModel> policeReportList;
    private boolean anyMedicalEmergency;
    private String medicalEmergencyData;
    private ArrayList<CommonDropdownModel> courseOfAction;
    private ArrayList<CommonDropdownModel> observation;
    private ArrayList<CommonDropdownModel> correctionAction;
    public ReportIncidentModel() {

    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalEvacuationTime() {
        return medicalEvacuationTime;
    }

    public void setMedicalEvacuationTime(String medicalEvacuationTime) {
        this.medicalEvacuationTime = medicalEvacuationTime;
    }

    public String getMedicalDescription() {
        return medicalDescription;
    }

    public void setMedicalDescription(String medicalDescription) {
        this.medicalDescription = medicalDescription;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ArrayList<CommonDropdownModel> getTypeOfIncident() {
        return typeOfIncident;
    }

    public void setTypeOfIncident(ArrayList<CommonDropdownModel> typeOfIncident) {
        this.typeOfIncident = typeOfIncident;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(String dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public String getTimeOfIncident() {
        return timeOfIncident;
    }

    public void setTimeOfIncident(String timeOfIncident) {
        this.timeOfIncident = timeOfIncident;
    }

    public String getPlaceOfIncident() {
        return placeOfIncident;
    }

    public void setPlaceOfIncident(String placeOfIncident) {
        this.placeOfIncident = placeOfIncident;
    }

    public String getTimeOfReporting() {
        return timeOfReporting;
    }

    public void setTimeOfReporting(String timeOfReporting) {
        this.timeOfReporting = timeOfReporting;
    }

    public String getOfficerReportedAtSite() {
        return officerReportedAtSite;
    }

    public void setOfficerReportedAtSite(String officerReportedAtSite) {
        this.officerReportedAtSite = officerReportedAtSite;
    }

    public String getNameOfPersonInvolved() {
        return nameOfPersonInvolved;
    }

    public void setNameOfPersonInvolved(String nameOfPersonInvolved) {
        this.nameOfPersonInvolved = nameOfPersonInvolved;
    }

    public String getNameOfSecurityPersonInvolved() {
        return nameOfSecurityPersonInvolved;
    }

    public void setNameOfSecurityPersonInvolved(String nameOfSecurityPersonInvolved) {
        this.nameOfSecurityPersonInvolved = nameOfSecurityPersonInvolved;
    }

    public boolean isLossStatus() {
        return lossStatus;
    }

    public void setLossStatus(boolean lossStatus) {
        this.lossStatus = lossStatus;
    }

    public boolean isIncidentReported() {
        return incidentReported;
    }

    public void setIncidentReported(boolean incidentReported) {
        this.incidentReported = incidentReported;
    }

    public boolean isAnyMedicalEmergency() {
        return anyMedicalEmergency;
    }

    public void setAnyMedicalEmergency(boolean anyMedicalEmergency) {
        this.anyMedicalEmergency = anyMedicalEmergency;
    }

    public String getMedicalEmergencyData() {
        return medicalEmergencyData;
    }

    public void setMedicalEmergencyData(String medicalEmergencyData) {
        this.medicalEmergencyData = medicalEmergencyData;
    }

    public ArrayList<LossItemModel> getLossItemList() {
        return lossItemList;
    }

    public void setLossItemList(ArrayList<LossItemModel> lossItemList) {
        this.lossItemList = lossItemList;
    }


    public ArrayList<CommonDropdownModel> getCourseOfAction() {
        return courseOfAction;
    }

    public void setCourseOfAction(ArrayList<CommonDropdownModel> courseOfAction) {
        this.courseOfAction = courseOfAction;
    }

    public ArrayList<CommonDropdownModel> getObservation() {
        return observation;
    }

    public void setObservation(ArrayList<CommonDropdownModel> observation) {
        this.observation = observation;
    }

    public ArrayList<CommonDropdownModel> getCorrectionAction() {
        return correctionAction;
    }

    public void setCorrectionAction(ArrayList<CommonDropdownModel> correctionAction) {
        this.correctionAction = correctionAction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(images);
        parcel.writeString(reportId);
        parcel.writeString(mobile);
        parcel.writeString(name);
        parcel.writeString(medicalEvacuationTime);
        parcel.writeString(medicalDescription);
        parcel.writeString(countryCode);
        parcel.writeTypedList(typeOfIncident);
        parcel.writeString(dateOfIncident);
        parcel.writeString(timeOfIncident);
        parcel.writeString(placeOfIncident);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(createdDate);
        parcel.writeString(timeOfReporting);
        parcel.writeString(officerReportedAtSite);
        parcel.writeString(nameOfPersonInvolved);
        parcel.writeString(numberOfPersonInvolved);
        parcel.writeString(numberOfSecurityPersonInvolved);
        parcel.writeString(nameOfSecurityPersonInvolved);
        parcel.writeByte((byte) (lossStatus ? 1 : 0));
        parcel.writeTypedList(lossItemList);
        parcel.writeByte((byte) (incidentReported ? 1 : 0));
        parcel.writeTypedList(policeReportList);
        parcel.writeByte((byte) (anyMedicalEmergency ? 1 : 0));
        parcel.writeString(medicalEmergencyData);
        parcel.writeTypedList(courseOfAction);
        parcel.writeTypedList(observation);
        parcel.writeTypedList(correctionAction);
        parcel.writeString(clientName);
        parcel.writeString(clientCode);
        parcel.writeString(clientLocation);
        parcel.writeString(designation);
        parcel.writeString(branchId);
        parcel.writeString(branchName);
    }
}