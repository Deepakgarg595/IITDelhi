package com.app.iitdelhicampus.utility.Patrolling;

import android.os.Parcel;
import android.os.Parcelable;

public class RecordDetailModel implements Parcelable {
    private String PunchDate;
    private String PunchTime;
    private String InOut;
    private String EmployeeCode;
    private String WarehouseId;
    private String Latitude;
    private String Longitude;
    private String ImageURL;
    private String UserId;

    public RecordDetailModel() {

    }
    protected RecordDetailModel(Parcel in) {
        PunchDate = in.readString();
        PunchTime = in.readString();
        InOut = in.readString();
        EmployeeCode = in.readString();
        WarehouseId = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
        ImageURL = in.readString();
        UserId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PunchDate);
        dest.writeString(PunchTime);
        dest.writeString(InOut);
        dest.writeString(EmployeeCode);
        dest.writeString(WarehouseId);
        dest.writeString(Latitude);
        dest.writeString(Longitude);
        dest.writeString(ImageURL);
        dest.writeString(UserId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecordDetailModel> CREATOR = new Creator<RecordDetailModel>() {
        @Override
        public RecordDetailModel createFromParcel(Parcel in) {
            return new RecordDetailModel(in);
        }

        @Override
        public RecordDetailModel[] newArray(int size) {
            return new RecordDetailModel[size];
        }
    };

    public String getPunchDate() {
        return PunchDate;
    }

    public void setPunchDate(String punchDate) {
        PunchDate = punchDate;
    }

    public String getPunchTime() {
        return PunchTime;
    }

    public void setPunchTime(String punchTime) {
        PunchTime = punchTime;
    }

    public String getInOut() {
        return InOut;
    }

    public void setInOut(String inOut) {
        InOut = inOut;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        EmployeeCode = employeeCode;
    }

    public String getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        WarehouseId = warehouseId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
