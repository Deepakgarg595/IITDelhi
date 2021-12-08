package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MetaDataDetailModel implements Parcelable {

    private String id;
    private String status;
    private String createdOn;
    private String updatedOn;
    private String experience;
    private String speciesSpecialisation;
    private String otherName;
    private String qualification;
    private String services;
    private String specialities;
    private boolean isSelected;

    protected MetaDataDetailModel(Parcel in) {
        id = in.readString();
        status = in.readString();
        createdOn = in.readString();
        updatedOn = in.readString();
        experience = in.readString();
        speciesSpecialisation = in.readString();
        otherName = in.readString();
        qualification = in.readString();
        services = in.readString();
        specialities = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(createdOn);
        dest.writeString(updatedOn);
        dest.writeString(experience);
        dest.writeString(speciesSpecialisation);
        dest.writeString(otherName);
        dest.writeString(qualification);
        dest.writeString(services);
        dest.writeString(specialities);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MetaDataDetailModel> CREATOR = new Creator<MetaDataDetailModel>() {
        @Override
        public MetaDataDetailModel createFromParcel(Parcel in) {
            return new MetaDataDetailModel(in);
        }

        @Override
        public MetaDataDetailModel[] newArray(int size) {
            return new MetaDataDetailModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSpeciesSpecialisation() {
        return speciesSpecialisation;
    }

    public void setSpeciesSpecialisation(String speciesSpecialisation) {
        this.speciesSpecialisation = speciesSpecialisation;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

