package com.mycompany.app.model;

import java.util.Date;

/**
 * Model cho ImportInvoice (Hóa đơn nhập hàng từ nhà cung cấp)
 * Khác với Invoice (hóa đơn bán ra cho khách hàng)
 */
public class ImportInvoice {
    private String importInvoiceID; // trước đây importReceiptID
    private Date importDate;
    private double totalValue;
    private String status;
    private String supplierID;
    private String supplierName; // tên nhà cung cấp (để hiển thị)

    public ImportInvoice() {
    }

    public ImportInvoice(String importInvoiceID, Date importDate, double totalValue, String status, String supplierID) {
        this.importInvoiceID = importInvoiceID;
        this.importDate = importDate;
        this.totalValue = totalValue;
        this.status = status;
        this.supplierID = supplierID;
    }

    public String getImportInvoiceID() {
        return importInvoiceID;
    }

    public void setImportInvoiceID(String importInvoiceID) {
        this.importInvoiceID = importInvoiceID;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Alias method cho JSP tương thích
    public Date getDate() {
        return importDate;
    }
}
