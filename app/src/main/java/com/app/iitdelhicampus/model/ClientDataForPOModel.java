package com.app.iitdelhicampus.model;

import java.util.ArrayList;

public class ClientDataForPOModel {
    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    private ArrayList<Data> data;
     public class  Data{
          public String getClientName() {
              return clientName;
          }

          public void setClientName(String clientName) {
              this.clientName = clientName;
          }

          public String getClientCode() {
              return clientCode;
          }

          public void setClientCode(String clientCode) {
              this.clientCode = clientCode;
          }

          public String getLocation() {
              return location;
          }

          public void setLocation(String location) {
              this.location = location;
          }

          private String clientName;
        private String clientCode;
        private String location;
    }

}
