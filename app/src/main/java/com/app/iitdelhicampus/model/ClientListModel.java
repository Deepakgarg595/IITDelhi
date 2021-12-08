package com.app.iitdelhicampus.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ClientListModel implements Parcelable{
    protected ClientListModel(Parcel in) {
        data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<ClientListModel> CREATOR = new Creator<ClientListModel>() {
        @Override
        public ClientListModel createFromParcel(Parcel in) {
            return new ClientListModel(in);
        }

        @Override
        public ClientListModel[] newArray(int size) {
            return new ClientListModel[size];
        }
    };

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    private ArrayList<Data> data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(data);
    }

    public static class Data implements Parcelable {
        private String id;
        private String unit_name;
        private String unit_code;
        private String location;
        private String branch;
        private String service;
        private String tagging_id;

        protected Data(Parcel in) {
            id = in.readString();
            unit_name = in.readString();
            unit_code = in.readString();
            location = in.readString();
            branch = in.readString();
            service = in.readString();
            tagging_id = in.readString();
            isSelected = in.readByte() != 0;
            tagging_date = in.readString();
            fo_id = in.readString();
            user_id = in.readString();
            lat = in.readString();
            lng = in.readString();
            isactive = in.readByte() != 0;
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        private boolean isSelected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getUnit_code() {
            return unit_code;
        }

        public void setUnit_code(String unit_code) {
            this.unit_code = unit_code;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getTagging_id() {
            return tagging_id;
        }

        public void setTagging_id(String tagging_id) {
            this.tagging_id = tagging_id;
        }

        public String getTagging_date() {
            return tagging_date;
        }

        public void setTagging_date(String tagging_date) {
            this.tagging_date = tagging_date;
        }

        public String getFo_id() {
            return fo_id;
        }

        public void setFo_id(String fo_id) {
            this.fo_id = fo_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public boolean isIsactive() {
            return isactive;
        }

        public void setIsactive(boolean isactive) {
            this.isactive = isactive;
        }

        private String tagging_date;
        private String fo_id;
        private String user_id;
        private String lat;
        private String lng;
        private boolean isactive;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(unit_name);
            parcel.writeString(unit_code);
            parcel.writeString(location);
            parcel.writeString(branch);
            parcel.writeString(service);
            parcel.writeString(tagging_id);
            parcel.writeByte((byte) (isSelected ? 1 : 0));
            parcel.writeString(tagging_date);
            parcel.writeString(fo_id);
            parcel.writeString(user_id);
            parcel.writeString(lat);
            parcel.writeString(lng);
            parcel.writeByte((byte) (isactive ? 1 : 0));
        }
    }
}
