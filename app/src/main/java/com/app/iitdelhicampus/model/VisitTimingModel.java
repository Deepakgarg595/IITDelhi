package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VisitTimingModel implements Parcelable {

    private boolean isSelected;
    private String day;
    private TimeModel timeModel;

    public VisitTimingModel(String day, TimeModel timeModel) {
        this.day = day;
        this.timeModel = timeModel;
    }

    protected VisitTimingModel(Parcel in) {
        isSelected = in.readByte() != 0;
        day = in.readString();
        timeModel = in.readParcelable(TimeModel.class.getClassLoader());
    }

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

    public static Creator<VisitTimingModel> getCREATOR() {
        return CREATOR;
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

    public static final Creator<VisitTimingModel> CREATOR = new Creator<VisitTimingModel>() {
        @Override
        public VisitTimingModel createFromParcel(Parcel in) {
            return new VisitTimingModel(in);
        }

        @Override
        public VisitTimingModel[] newArray(int size) {
            return new VisitTimingModel[size];
        }
    };
}
