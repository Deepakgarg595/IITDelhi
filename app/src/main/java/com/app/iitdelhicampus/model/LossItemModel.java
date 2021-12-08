package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LossItemModel implements Parcelable {
    private String lossType;
    public LossItemModel(){

    }

    public LossItemModel(Parcel in) {
        lossType = in.readString();
        lossItem = in.readString();
    }

    public static final Creator<LossItemModel> CREATOR = new Creator<LossItemModel>() {
        @Override
        public LossItemModel createFromParcel(Parcel in) {
            return new LossItemModel(in);
        }

        @Override
        public LossItemModel[] newArray(int size) {
            return new LossItemModel[size];
        }
    };

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getLossItem() {
        return lossItem;
    }

    public void setLossItem(String lossItem) {
        this.lossItem = lossItem;
    }

    private String lossItem;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lossType);
        parcel.writeString(lossItem);
    }
}
