package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonDropdownModel implements Parcelable {

    protected CommonDropdownModel(Parcel in) {
        isSelected = in.readByte() != 0;
        name = in.readString();
        severity = in.readString();
    }
    public CommonDropdownModel(){

    }

    public static final Creator<CommonDropdownModel> CREATOR = new Creator<CommonDropdownModel>() {
        @Override
        public CommonDropdownModel createFromParcel(Parcel in) {
            return new CommonDropdownModel(in);
        }

        @Override
        public CommonDropdownModel[] newArray(int size) {
            return new CommonDropdownModel[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }


    private String name;
    private String severity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(name);
        parcel.writeString(severity);
    }
}
