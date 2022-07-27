package com.example.itassettracking;

public class SearchDataModel {
    String auditid,rfidNo,status,assetId,assetName,assetTag,serialNo,model,category,assetStatus,company,manufacturer,location,warranty,purchaseCost,orderNo,purchaseDate,notes,StatusF="False",Color;

    public SearchDataModel() {
    }

    public SearchDataModel(String auditid, String rfidNo, String status, String assetId, String assetName, String assetTag, String serialNo, String model, String category, String assetStatus, String company, String manufacturer, String location, String warranty, String purchaseCost, String orderNo, String purchaseDate, String notes) {
        this.auditid = auditid;
        this.rfidNo = rfidNo;
        this.status = status;
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetTag = assetTag;
        this.serialNo = serialNo;
        this.model = model;
        this.category = category;
        this.assetStatus = assetStatus;
        this.company = company;
        this.manufacturer = manufacturer;
        this.location = location;
        this.warranty = warranty;
        this.purchaseCost = purchaseCost;
        this.orderNo = orderNo;
        this.purchaseDate = purchaseDate;
        this.notes = notes;
    }

    public String getStatusF() {
        return StatusF;
    }

    public void setStatusF(String statusF) {
        StatusF = statusF;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getAuditid() {
        return auditid;
    }

    public void setAuditid(String auditid) {
        this.auditid = auditid;
    }

    public String getRfidNo() {
        return rfidNo;
    }

    public void setRfidNo(String rfidNo) {
        this.rfidNo = rfidNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(String purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
