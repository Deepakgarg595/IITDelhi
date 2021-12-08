package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocalIncidentReportedModel implements Parcelable {
    private String reportedTitle;

    public String getReportedDescription() {
        return reportedDescription;
    }

    public void setReportedDescription(String reportedDescription) {
        this.reportedDescription = reportedDescription;
    }

    private String reportedDescription;

    public LocalIncidentReportedModel(Parcel in) {
        reportedTitle = in.readString();
        firNumber = in.readString();
        firStatus = in.readString();
        reportedDescription =in.readString();
    }

    public LocalIncidentReportedModel(){

    }

    public static final Creator<LocalIncidentReportedModel> CREATOR = new Creator<LocalIncidentReportedModel>() {
        @Override
        public LocalIncidentReportedModel createFromParcel(Parcel in) {
            return new LocalIncidentReportedModel(in);
        }

        @Override
        public LocalIncidentReportedModel[] newArray(int size) {
            return new LocalIncidentReportedModel[size];
        }
    };

    public String getReportedTitle() {
        return reportedTitle;
    }

    public void setReportedTitle(String reportedTitle) {
        this.reportedTitle = reportedTitle;
    }

    public String getFirNumber() {
        return firNumber;
    }

    public void setFirNumber(String firNumber) {
        this.firNumber = firNumber;
    }

    public String getFirStatus() {
        return firStatus;
    }

    public void setFirStatus(String firStatus) {
        this.firStatus = firStatus;
    }

    private String firNumber;
    private String firStatus;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reportedTitle);
        parcel.writeString(firNumber);
        parcel.writeString(firStatus);
        parcel.writeString(reportedDescription);
    }
}
