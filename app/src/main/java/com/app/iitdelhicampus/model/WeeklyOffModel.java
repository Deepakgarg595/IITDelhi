package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WeeklyOffModel implements Parcelable {

    private boolean isSelected;
    private String day;
    private TimeModel timeModel;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public TimeModel getTimeModel() {
        return timeModel;
    }

    public void setTimeModel(TimeModel timeModel) {
        this.timeModel = timeModel;
    }

    public static Creator<WeeklyOffModel> getCREATOR() {
        return CREATOR;
    }

    public WeeklyOffModel(String day, TimeModel timeModel) {
        this.day = day;
        this.timeModel = timeModel;
    }

    protected WeeklyOffModel(Parcel in) {
        isSelected = in.readByte() != 0;
        day = in.readString();
        timeModel = in.readParcelable(TimeModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeString(day);
        dest.writeParcelable(timeModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeeklyOffModel> CREATOR = new Creator<WeeklyOffModel>() {
        @Override
        public WeeklyOffModel createFromParcel(Parcel in) {
            return new WeeklyOffModel(in);
        }

        @Override
        public WeeklyOffModel[] newArray(int size) {
            return new WeeklyOffModel[size];
        }
    };
}