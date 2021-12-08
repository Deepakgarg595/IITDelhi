package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeModel implements Parcelable {

    private String toTime;
    private String fromTime;
    private String morningFrom;
    private String morningTo;
    private String eveningFrom;
    private String eveningTo;

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getMorningFrom() {
        return morningFrom;
    }

    public void setMorningFrom(String morningFrom) {
        this.morningFrom = morningFrom;
    }

    public String getMorningTo() {
        return morningTo;
    }

    public void setMorningTo(String morningTo) {
        this.morningTo = morningTo;
    }

    public String getEveningFrom() {
        return eveningFrom;
    }

    public void setEveningFrom(String eveningFrom) {
        this.eveningFrom = eveningFrom;
    }

    public String getEveningTo() {
        return eveningTo;
    }

    public void setEveningTo(String eveningTo) {
        this.eveningTo = eveningTo;
    }

    public static Creator<TimeModel> getCREATOR() {
        return CREATOR;
    }

    protected TimeModel(Parcel in) {
        toTime = in.readString();
        fromTime = in.readString();
        morningFrom = in.readString();
        morningTo = in.readString();
        eveningFrom = in.readString();
        eveningTo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toTime);
        dest.writeString(fromTime);
        dest.writeString(morningFrom);
        dest.writeString(morningTo);
        dest.writeString(eveningFrom);
        dest.writeString(eveningTo);
    }

    public TimeModel(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeModel> CREATOR = new Creator<TimeModel>() {
        @Override
        public TimeModel createFromParcel(Parcel in) {
            return new TimeModel(in);
        }

        @Override
        public TimeModel[] newArray(int size) {
            return new TimeModel[size];
        }
    };
}
