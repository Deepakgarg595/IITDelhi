package com.app.iitdelhicampus.model;

import java.util.ArrayList;

public class SiteObservationModel {
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public ArrayList<SiteVisitMode> getData() {
        return data;
    }

    private ArrayList<SiteVisitMode> data;
}