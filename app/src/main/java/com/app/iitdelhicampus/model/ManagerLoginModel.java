package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ManagerLoginModel implements Parcelable {
    @SerializedName("success")
    @Expose
    private List<SuccessModel> success = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tstatus")
    @Expose
    private String tstatus;

    protected ManagerLoginModel(Parcel in) {
        success = in.createTypedArrayList(SuccessModel.CREATOR);
        message = in.readString();
        tstatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(success);
        dest.writeString(message);
        dest.writeString(tstatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ManagerLoginModel> CREATOR = new Creator<ManagerLoginModel>() {
        @Override
        public ManagerLoginModel createFromParcel(Parcel in) {
            return new ManagerLoginModel(in);
        }

        @Override
        public ManagerLoginModel[] newArray(int size) {
            return new ManagerLoginModel[size];
        }
    };

    public List<SuccessModel> getSuccess() {
        return success;
    }

    public void setSuccess(List<SuccessModel> success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTstatus() {
        return tstatus;
    }

    public void setTstatus(String tstatus) {
        this.tstatus = tstatus;
    }

}
