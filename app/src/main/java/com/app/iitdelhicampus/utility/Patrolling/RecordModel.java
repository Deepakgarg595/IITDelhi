package com.app.iitdelhicampus.utility.Patrolling;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecordModel implements Parcelable{
    private ArrayList<RecordDetailModel> data;

    protected RecordModel(Parcel in) {
        data = in.createTypedArrayList(RecordDetailModel.CREATOR);
    }

public RecordModel() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecordModel> CREATOR = new Creator<RecordModel>() {
        @Override
        public RecordModel createFromParcel(Parcel in) {
            return new RecordModel(in);
        }

        @Override
        public RecordModel[] newArray(int size) {
            return new RecordModel[size];
        }
    };

    public ArrayList<RecordDetailModel> getData() {
        return data;
    }

    public void setData(ArrayList<RecordDetailModel> data) {
        this.data = data;
    }
}
