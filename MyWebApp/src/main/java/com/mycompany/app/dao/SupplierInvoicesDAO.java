package com.mycompany.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.model.ImportInvoice;
import com.mycompany.app.model.Supplier;
import com.mycompany.app.util.DatabaseUtil;

/**
 * DAO phục vụ danh sách hóa đơn nhập (trước đây ImportReceipt) của supplier
 */
public class SupplierInvoicesDAO {

    /**
     * Lấy danh sách ImportInvoice (hóa đơn nhập) của một supplier trong khoảng thời gian.
     * @param supplierID supplier
     * @param startDate yyyy-MM-dd
     * @param endDate yyyy-MM-dd
     */
    public List<ImportInvoice> getSupplierImportInvoices(String supplierID, String startDate, String endDate) {
        List<ImportInvoice> list = new ArrayList<>();
        String sql = "SELECT ir.*, s.name AS supplierName " +
                     "FROM ImportReceipt ir " +
                     "JOIN Supplier s ON ir.supplierID = s.supplierID " +
                     "WHERE ir.supplierID = ? AND ir.importDate BETWEEN ? AND ? " +
                     "ORDER BY ir.importDate DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, supplierID);
            ps.setString(2, startDate);
            ps.setString(3, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportInvoice invoice = new ImportInvoice();
                    invoice.setImportInvoiceID(rs.getString("importReceiptID"));
                    invoice.setImportDate(rs.getDate("importDate"));
                    invoice.setTotalValue(rs.getDouble("totalValue"));
                    invoice.setStatus(rs.getString("status"));
                    invoice.setSupplierID(rs.getString("supplierID"));
                    invoice.setSupplierName(rs.getString("supplierName"));
                    list.add(invoice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy thông tin chi tiết supplier
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
