package com.mycompany.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.model.ImportReceipt;
import com.mycompany.app.model.Supplier;
import com.mycompany.app.util.DatabaseUtil;

/**
 * DAO phục vụ danh sách phiếu nhập của supplier (SupplierReceiptsController)
 * Chức năng: Lấy danh sách ImportReceipt và thông tin Supplier
 */
public class SupplierReceiptsDAO {
    
    /**
     * Lấy danh sách ImportReceipt (phiếu nhập hàng) của một supplier
     * KHÔNG phải Invoice của khách hàng
     * 
     * @param supplierID ID của nhà cung cấp
     * @param startDate Ngày bắt đầu (format: yyyy-MM-dd)
     * @param endDate Ngày kết thúc (format: yyyy-MM-dd)
     * @return Danh sách ImportReceipt trong khoảng thời gian
     */
    public List<ImportReceipt> getSupplierImportReceipts(String supplierID, String startDate, String endDate) {
        List<ImportReceipt> list = new ArrayList<>();
        
        String sql = "SELECT * FROM ImportReceipt " +
                     "WHERE supplierID = ? AND importDate BETWEEN ? AND ? " +
                     "ORDER BY importDate DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, supplierID);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportReceipt receipt = new ImportReceipt();
                    receipt.setImportReceiptID(rs.getString("importReceiptID"));
                    receipt.setImportDate(rs.getDate("importDate"));
                    receipt.setTotalValue(rs.getDouble("totalValue"));
                    receipt.setStatus(rs.getString("status"));
                    receipt.setSupplierID(rs.getString("supplierID"));
                    list.add(receipt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy thông tin chi tiết của một supplier
     * 
     * @param supplierID ID của nhà cung cấp
     * @return Thông tin Supplier (ID, name, address)
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
