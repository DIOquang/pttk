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

import com.mycompany.app.dao.SupplierInvoicesDAO;
import com.mycompany.app.model.ImportInvoice;
import com.mycompany.app.model.Manager;
import com.mycompany.app.model.Supplier;

@WebServlet("/supplierImportInvoices")
public class SupplierInvoicesController extends HttpServlet {

    private SupplierInvoicesDAO supplierInvoicesDAO;

    @Override
    public void init() {
        supplierInvoicesDAO = new SupplierInvoicesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        Manager manager = (Manager) session.getAttribute("manager");

        if (!"manager".equals(userType) || manager == null) {
            request.setAttribute("error", "Access Denied! Only managers can view supplier import invoices.");
            request.getRequestDispatcher("uiMain.jsp").forward(request, response);
            return;
        }

        String supplierID = request.getParameter("supplierID");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        if (supplierID != null && startDate != null && endDate != null) {
            Supplier supplier = supplierInvoicesDAO.getSupplierInfo(supplierID);
            request.setAttribute("supplier", supplier);

            List<ImportInvoice> importInvoiceList = supplierInvoicesDAO.getSupplierImportInvoices(supplierID, startDate, endDate);
            request.setAttribute("importInvoiceList", importInvoiceList);

            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("uiImportInvoiceList.jsp");
        dispatcher.forward(request, response);
    }
}
