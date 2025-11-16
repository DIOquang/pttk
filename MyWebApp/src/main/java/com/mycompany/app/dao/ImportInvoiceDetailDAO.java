package com.mycompany.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.model.ImportDetail;
import com.mycompany.app.model.ImportInvoice;
import com.mycompany.app.model.Supplier;
import com.mycompany.app.util.DatabaseUtil;

/**
 * DAO phục vụ chi tiết hóa đơn nhập (ImportInvoiceDetailController)
 * Chức năng: Lấy thông tin ImportInvoice, Supplier và chi tiết sản phẩm nhập
 */
public class ImportInvoiceDetailDAO {

    /**
     * Lấy thông tin ImportInvoice theo ID
     * @param importInvoiceID ID của hóa đơn nhập
     * @return ImportInvoice (ID, date, totalValue, status, supplierID)
     */
    public ImportInvoice getImportInvoiceByID(String importInvoiceID) {
        ImportInvoice invoice = null;
        String sql = "SELECT * FROM ImportReceipt WHERE importReceiptID = ?"; // bảng giữ nguyên tên

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, importInvoiceID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    invoice = new ImportInvoice();
                    invoice.setImportInvoiceID(rs.getString("importReceiptID"));
                    invoice.setImportDate(rs.getDate("importDate"));
                    invoice.setTotalValue(rs.getDouble("totalValue"));
                    invoice.setStatus(rs.getString("status"));
                    invoice.setSupplierID(rs.getString("supplierID"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoice;
    }

    /**
     * Lấy chi tiết các sản phẩm trong hóa đơn nhập
     * @param importInvoiceID ID hóa đơn nhập
     * @return danh sách ImportDetail
     */
    public List<ImportDetail> getImportDetails(String importInvoiceID) {
        List<ImportDetail> list = new ArrayList<>();
        String sql = "SELECT id.*, p.name AS productName, p.description AS productDescription " +
                     "FROM ImportDetail id JOIN Product p ON id.productID = p.productID " +
                     "WHERE id.importReceiptID = ?"; // cột vẫn importReceiptID

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, importInvoiceID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportDetail detail = new ImportDetail();
                    detail.setImportDetailID(rs.getString("importDetailID"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setImportPrice(rs.getDouble("importPrice"));
                    detail.setImportInvoiceID(rs.getString("importReceiptID"));
                    detail.setProductID(rs.getString("productID"));
                    detail.setProductName(rs.getString("productName"));
                    detail.setProductDescription(rs.getString("productDescription"));
                    list.add(detail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy thông tin Supplier
     */
    public Supplier getSupplierInfo(String supplierID) {
        Supplier supplier = null;
        String sql = "SELECT * FROM Supplier WHERE supplierID = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, supplierID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    supplier = new Supplier();
                    supplier.setSupplierID(rs.getString("supplierID"));
                    supplier.setName(rs.getString("name"));
                    supplier.setAddress(rs.getString("address"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplier;
    }
}
