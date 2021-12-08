package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FcmDataModel implements Parcelable {

    protected FcmDataModel(Parcel in) {
        isSelected = in.readByte() != 0;
        name = in.readString();
        mobile = in.readString();
        userType=in.readString();
        countryCode=in.readString();
        api=in.readString();
        port=in.readString();
        appVersion=in.readString();
        profileImage=in.readString();
    }

    public FcmDataModel(){

    }

    public static final Creator<FcmDataModel> CREATOR = new Creator<FcmDataModel>() {
        @Override
        public FcmDataModel createFromParcel(Parcel in) {
            return new FcmDataModel(in);
        }

        @Override
        public FcmDataModel[] newArray(int size) {
            return new FcmDataModel[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    private boolean isSelected;
    private String api;
    private int url;

    public static Creator<FcmDataModel> getCREATOR() {
        return CREATOR;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

//    private boolean isSelected;
//    private String api;
    private String port;
    private String name;
    private String mobile;
    private String appVersion;
    private String countryCode;
    private String userType;
    private String profileImage;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeString(name);
        parcel.writeString(mobile);
        parcel.writeString(userType);
        parcel.writeString(countryCode);
        parcel.writeString(api);
        parcel.writeString(port);
        parcel.writeString(appVersion);
        parcel.writeString(profileImage);
    }
}
