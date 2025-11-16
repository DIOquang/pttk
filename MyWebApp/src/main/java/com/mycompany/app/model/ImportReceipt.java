package com.mycompany.app.model;

import java.util.Date;

/**
 * Model cho ImportReceipt (Phiếu nhập hàng từ nhà cung cấp)
 * Khác với Invoice (hóa đơn của khách hàng)
 */
public class ImportReceipt {
    private String importReceiptID;
    private Date importDate;
    private double totalValue;
    private String status;
    private String supplierID;
    
    // Constructor
    public ImportReceipt() {
    }
    
    public ImportReceipt(String importReceiptID, Date importDate, double totalValue, String status, String supplierID) {
        this.importReceiptID = importReceiptID;
        this.importDate = importDate;
        this.totalValue = totalValue;
        this.status = status;
        this.supplierID = supplierID;
    }
    
    // Getters and Setters
    public String getImportReceiptID() {
        return importReceiptID;
    }
    
    public void setImportReceiptID(String importReceiptID) {
        this.importReceiptID = importReceiptID;
    }
    
    public Date getImportDate() {
        return importDate;
    }
    
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
    
    public double getTotalValue() {
        return totalValue;
    }
    
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSupplierID() {
        return supplierID;
    }
    
    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }
}
