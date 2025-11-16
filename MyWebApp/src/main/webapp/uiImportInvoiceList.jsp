<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Danh sách Phiếu Nhập (Import Invoices)</title>
    <meta charset="UTF-8"/>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #525561;
            color: #eee;       
            line-height: 1.6;
            width: 100vw;
        }
        .header {
            width: 96vw;
            color: white;
            padding: 20px 30px;
            display: flex;
            justify-content: space-between;
            align-self: flex-start;
        }
        .header-left { display:flex; align-items:center; }
        .circle { width: 3rem; height: 3rem; background-color: #1d90f5; border-radius: 50%; margin-right: 1rem; }
        .tabs {
            width: 93%;
            min-height: 82%;
            background: linear-gradient(
                to right,
                rgb(39, 42, 55) 40%,
                rgba(39, 42, 55, 0.8)
            ), url("${pageContext.request.contextPath}/images/bg1.jpg") center no-repeat;
            background-size: cover;
            border-radius: 2rem;
            box-shadow: 0 1rem 5rem rgba(0, 0, 0, 0.4);
            margin: 0 auto; display:flex; flex-direction: column; padding-bottom: 30px;
        }
        .tabs h2 { padding-left: 5%; margin-top: 5%; margin-bottom: 10px; font-size: 2.4em; }
        .form-row { display:flex; gap: 16px; flex-wrap: wrap; padding-left:5%; padding-right:5%; }
        .form-group { margin-bottom: 12px; text-align:left; }
        .form-group label { display:block; margin-bottom: 8px; font-size: 1.1em; color:#fff; }
        .form-group input { width: 260px; padding: 12px; border: 10px solid #e1e8ed; border-radius: 16px; font-size: 16px; background: rgba(255,255,255,0.9); }
        .btn { padding: 12px 18px; font-size: 16px; background: #1d90f5; color: white; border: none; border-radius: 20px; cursor: pointer; font-weight: 600; }
        table {
            width: 90%; margin: 20px auto; border-collapse: collapse; border: 2px solid #fff; border-radius: 12px; overflow: hidden; background: rgba(255, 255, 255, 0.05); color: #fff;
        }
        th, td { border: 1px solid #fff; padding: 12px 18px; text-align: left; }
        th { background-color: rgba(255, 255, 255, 0.15); font-size: 1.1em; }
        tr:hover { background-color: rgba(255, 255, 255, 0.1); transition: 0.2s; }
        a { color: #1d90f5; text-decoration: none; font-weight: 600; }
        a:hover { color: #00b7ff; text-decoration: underline; }
        .summary { padding-left:5%; color:#fff; margin-top: 6px; }
    </style>
    <fmt:setLocale value="vi_VN"/>
    <fmt:setTimeZone value="Asia/Ho_Chi_Minh"/>
    
</head>
<body>
    <header class="header">
        <div class="header-left">
            <span class="circle"></span>
            <h1>Go Anywhere</h1>
        </div>
        <a href="logout" style="color: white; text-decoration: none; padding: 10px 20px; background: transparent; font-weight: 600;">Logout</a>
    </header>
    <div class="tabs">
        <h2>Danh sách Phiếu Nhập</h2>
        <form class="form-row" action="supplierImportInvoices" method="get">
            <input type="hidden" name="supplierID" value="${param.supplierID != null ? param.supplierID : supplier.supplierID}" />
            <div class="form-group">
                <label for="startDate">Từ ngày:</label>
                <input type="date" id="startDate" name="startDate" value="${startDate}" />
            </div>
            <div class="form-group">
                <label for="endDate">Đến ngày:</label>
                <input type="date" id="endDate" name="endDate" value="${endDate}" />
            </div>
            <button type="submit" class="btn">Lọc</button>
        </form>
        <div class="summary">
            <strong>Nhà cung cấp:</strong> ${supplier != null ? supplier.name : ''}
        </div>
        <table>
            <thead>
                <tr>
                    <th>Mã phiếu</th>
                    <th>Ngày nhập</th>
                    <th>Nhà cung cấp</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${importInvoiceList}">
                    <tr>
                        <td>${r.importInvoiceID}</td>
                        <td><fmt:formatDate value="${r.importDate}" pattern="yyyy-MM-dd"/></td>
                        <td>${r.supplierName}</td>
                        <td><a href="importInvoiceDetail?importInvoiceID=${r.importInvoiceID}">Xem chi tiết</a></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty importInvoiceList}">
                    <tr>
                        <td colspan="4" style="color:#9ca3af">Không có phiếu nhập nào trong khoảng đã chọn.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <a href="uiMain.jsp" style="padding-left:5%; margin-bottom:20px; color:#1d90f5;">← Về trang chính</a>
    </div>
</body>
</html>