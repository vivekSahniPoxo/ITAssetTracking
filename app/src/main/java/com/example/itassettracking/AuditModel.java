package com.example.itassettracking;

public class AuditModel {
String auditId,auditDate,auditor,remarks,total,found,notFound,StatusAudit;

    public AuditModel(String auditId, String auditDate, String auditor, String remarks, String total, String found, String notFound, String statusAudit) {
        this.auditId = auditId;
        this.auditDate = auditDate;
        this.auditor = auditor;
        this.remarks = remarks;
        this.total = total;
        this.found = found;
        this.notFound = notFound;
        StatusAudit = statusAudit;
    }

    public String getStatusAudit() {
        return StatusAudit;
    }

    public void setStatusAudit(String statusAudit) {
        StatusAudit = statusAudit;
    }

//    public AuditModel(String auditId, String auditDate, String auditor, String remarks, String total, String found, String notFound) {
//        this.auditId = auditId;
//        this.auditDate = auditDate;
//        this.auditor = auditor;
//        this.remarks = remarks;
//        this.total = total;
//        this.found = found;
//        this.notFound = notFound;
//    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getNotFound() {
        return notFound;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }
}
