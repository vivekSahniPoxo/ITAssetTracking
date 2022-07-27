package com.example.itassettracking;

public class DetailModel {
    public String rfidNo;
    public String assetName;
    public String model;
    public String category;
    public String location;
    public String Color;
    String StatusF="false";

    public DetailModel(String rfidNo, String assetName, String model, String category, String location) {
        this.rfidNo = rfidNo;
        this.assetName = assetName;
        this.model = model;
        this.category = category;
        this.location = location;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getStatusF() {
        return StatusF;
    }

    public void setStatusF(String statusF) {
        StatusF = statusF;
    }

    public String getRfidNo() {
        return rfidNo;
    }

    public void setRfidNo(String rfidNo) {
        this.rfidNo = rfidNo;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
