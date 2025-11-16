package com.mycompany.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.app.dao.SupplierReceiptsDAO;
import com.mycompany.app.model.ImportReceipt;
import com.mycompany.app.model.Manager;
import com.mycompany.app.model.Supplier;

@WebServlet("/supplierImportReceipts")
public class SupplierReceiptsController extends HttpServlet {
    
    private SupplierReceiptsDAO supplierReceiptsDAO;

    @Override
    public void init() {
        supplierReceiptsDAO = new SupplierReceiptsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        Manager manager = (Manager) session.getAttribute("manager");
        
        // Check if user is manager
        if (!"manager".equals(userType) || manager == null) {
            request.setAttribute("error", "Access Denied! Only managers can view supplier import receipts.");
            request.getRequestDispatcher("uiMain.jsp").forward(request, response);
            return;
        }
        
        String supplierID = request.getParameter("supplierID");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (supplierID != null && startDate != null && endDate != null) {
            // Get supplier information
                Supplier supplier = supplierReceiptsDAO.getSupplierInfo(supplierID);
            request.setAttribute("supplier", supplier);
            
            // Get import receipts for this supplier (NOT invoices)
                List<ImportReceipt> importReceiptList = supplierReceiptsDAO.getSupplierImportReceipts(supplierID, startDate, endDate);
            request.setAttribute("importReceiptList", importReceiptList);
            
            // Pass date range back to the view
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("uiReceiptList.jsp");
        dispatcher.forward(request, response);
    }
}
