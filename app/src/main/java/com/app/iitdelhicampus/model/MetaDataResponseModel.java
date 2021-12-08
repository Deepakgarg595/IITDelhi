package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MetaDataResponseModel implements Parcelable {

    private String status;
    private String requestId;
    ArrayList<MetaDataDetailModel>experience;
    ArrayList<MetaDataDetailModel>speciesSpecialisation;
    ArrayList<MetaDataDetailModel>qualification;
    ArrayList<MetaDataDetailModel>services;
    ArrayList<MetaDataDetailModel>specialities;


    protected MetaDataResponseModel(Parcel in) {
        status = in.readString();
        requestId = in.readString();
    }

    public static final Creator<MetaDataResponseModel> CREATOR = new Creator<MetaDataResponseModel>() {
        @Override
        public MetaDataResponseModel createFromParcel(Parcel in) {
            return new MetaDataResponseModel(in);
        }

        @Override
        public MetaDataResponseModel[] newArray(int size) {
            return new MetaDataResponseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(requestId);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ArrayList<MetaDataDetailModel> getExperience() {
        return experience;
    }

    public void setExperience(ArrayList<MetaDataDetailModel> experience) {
        this.experience = experience;
    }

    public ArrayList<MetaDataDetailModel> getSpeciesSpecialisation() {
        return speciesSpecialisation;
    }

    public void setSpeciesSpecialisation(ArrayList<MetaDataDetailModel> speciesSpecialisation) {
        this.speciesSpecialisation = speciesSpecialisation;
    }

    public ArrayList<MetaDataDetailModel> getQualification() {
        return qualification;
    }

    public void setQualification(ArrayList<MetaDataDetailModel> qualification) {
        this.qualification = qualification;
    }

    public ArrayList<MetaDataDetailModel> getServices() {
        return services;
    }

    public void setServices(ArrayList<MetaDataDetailModel> services) {
        this.services = services;
    }

    public ArrayList<MetaDataDetailModel> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(ArrayList<MetaDataDetailModel> specialities) {
        this.specialities = specialities;
    }
}

