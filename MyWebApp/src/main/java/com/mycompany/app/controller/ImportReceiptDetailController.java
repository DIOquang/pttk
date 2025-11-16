package com.mycompany.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.app.dao.ImportReceiptDetailDAO;
import com.mycompany.app.model.ImportDetail;
import com.mycompany.app.model.ImportReceipt;
import com.mycompany.app.model.Manager;
import com.mycompany.app.model.Supplier;

@WebServlet("/importReceiptDetail")
public class ImportReceiptDetailController extends HttpServlet {
    
    private ImportReceiptDetailDAO importReceiptDetailDAO;
    
    @Override
    public void init() {
        importReceiptDetailDAO = new ImportReceiptDetailDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        Manager manager = (Manager) session.getAttribute("manager");
        
        // Kiểm tra quyền manager
        if (!"manager".equals(userType) || manager == null) {
            request.setAttribute("error", "Access denied! Only Managers can view import receipt details.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        String importReceiptID = request.getParameter("importReceiptID");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (importReceiptID != null && !importReceiptID.isEmpty()) {
            // Lấy thông tin phiếu nhập
                ImportReceipt importReceipt = importReceiptDetailDAO.getImportReceiptByID(importReceiptID);
            
            if (importReceipt != null) {
                // Lấy thông tin supplier
                    Supplier supplier = importReceiptDetailDAO.getSupplierInfo(importReceipt.getSupplierID());
                
                // Lấy chi tiết các sản phẩm trong phiếu nhập
                    List<ImportDetail> importDetails = importReceiptDetailDAO.getImportDetails(importReceiptID);
                
                // Đưa dữ liệu vào request
                request.setAttribute("importReceipt", importReceipt);
                request.setAttribute("supplier", supplier);
                request.setAttribute("importDetails", importDetails);
            } else {
                request.setAttribute("error", "Import receipt not found with ID: " + importReceiptID);
            }
        } else {
            request.setAttribute("error", "Missing import receipt ID.");
        }
        
        // Truyền lại khoảng thời gian để quay về danh sách với bối cảnh thống kê
        if (startDate != null) request.setAttribute("startDate", startDate);
        if (endDate != null) request.setAttribute("endDate", endDate);

        request.getRequestDispatcher("uiImportReceiptDetail.jsp").forward(request, response);
    }
}
