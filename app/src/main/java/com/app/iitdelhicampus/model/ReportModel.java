package com.app.iitdelhicampus.model;

import java.util.ArrayList;

public class ReportModel {
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public ArrayList<ReportIncidentModel> getData() {
        return data;
    }

    private ArrayList<ReportIncidentModel> data;
}
