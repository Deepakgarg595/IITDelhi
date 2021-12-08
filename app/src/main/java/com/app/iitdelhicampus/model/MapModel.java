package com.app.iitdelhicampus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapModel {
    @SerializedName("warehousid")
    @Expose
    private String warehousid;
    @SerializedName("warehousname")
    @Expose
    private String warehousname;
    @SerializedName("latlong")
    @Expose
    private List<Latlong> latlong = null;

    public String getWarehousid() {
        return warehousid;
    }

    public void setWarehousid(String warehousid) {
        this.warehousid = warehousid;
    }

    public String getWarehousname() {
        return warehousname;
    }

    public void setWarehousname(String warehousname) {
        this.warehousname = warehousname;
    }

    public List<Latlong> getLatlong() {
        return latlong;
    }

    public void setLatlong(List<Latlong> latlong) {
        this.latlong = latlong;
    }

}
