package com.mycompany.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.app.dao.ImportInvoiceDetailDAO;
import com.mycompany.app.model.ImportDetail;
import com.mycompany.app.model.ImportInvoice;
import com.mycompany.app.model.Manager;
import com.mycompany.app.model.Supplier;

@WebServlet("/importInvoiceDetail")
public class ImportInvoiceDetailController extends HttpServlet {

    private ImportInvoiceDetailDAO importInvoiceDetailDAO;

    @Override
    public void init() {
        importInvoiceDetailDAO = new ImportInvoiceDetailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        Manager manager = (Manager) session.getAttribute("manager");

        if (!"manager".equals(userType) || manager == null) {
            request.setAttribute("error", "Access denied! Only Managers can view import invoice details.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // chấp nhận cả tham số cũ importReceiptID để tránh gãy link
        String importInvoiceID = request.getParameter("importInvoiceID");
        if (importInvoiceID == null || importInvoiceID.isEmpty()) {
            importInvoiceID = request.getParameter("importReceiptID");
        }

        if (importInvoiceID != null && !importInvoiceID.isEmpty()) {
            ImportInvoice importInvoice = importInvoiceDetailDAO.getImportInvoiceByID(importInvoiceID);
            if (importInvoice != null) {
                Supplier supplier = importInvoiceDetailDAO.getSupplierInfo(importInvoice.getSupplierID());
                List<ImportDetail> importDetails = importInvoiceDetailDAO.getImportDetails(importInvoiceID);
                request.setAttribute("importInvoice", importInvoice);
                request.setAttribute("supplier", supplier);
                request.setAttribute("importDetails", importDetails);
            } else {
                request.setAttribute("error", "Import invoice not found with ID: " + importInvoiceID);
            }
        } else {
            request.setAttribute("error", "Missing import invoice ID.");
        }

        request.getRequestDispatcher("uiImportInvoiceDetail.jsp").forward(request, response);
    }
}
