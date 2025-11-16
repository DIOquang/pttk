<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Chi tiết Phiếu Nhập (Import Invoice)</title>
    <meta charset="UTF-8"/>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #525561; color: #eee; line-height: 1.6; width: 100vw;
        }
        .header { width: 96vw; color: white; padding: 20px 30px; display:flex; justify-content: space-between; }
        .header-left { display:flex; align-items:center; }
        .circle { width:3rem; height:3rem; background:#1d90f5; border-radius:50%; margin-right:1rem; }
        .tabs { width:93%; min-height:82%; background: linear-gradient(to right, rgb(39,42,55) 40%, rgba(39,42,55,.8)), url("${pageContext.request.contextPath}/images/bg1.jpg") center no-repeat; background-size: cover; border-radius:2rem; box-shadow:0 1rem 5rem rgba(0,0,0,.4); margin:0 auto; display:flex; flex-direction:column; padding-bottom:30px; }
        .tabs h2 { padding-left:5%; margin-top:5%; margin-bottom:10px; font-size:2.4em; }
        .meta { padding-left:5%; padding-right:5%; color:#fff; margin-bottom: 6px; }
        table { width: 90%; margin: 10px auto 20px; border-collapse: collapse; border: 2px solid #fff; border-radius: 12px; overflow: hidden; background: rgba(255,255,255,0.05); color:#fff; }
        th, td { border: 1px solid #fff; padding: 12px 18px; text-align: left; }
        th { background: rgba(255,255,255,0.15); font-size: 1.1em; }
        tr:hover { background: rgba(255,255,255,0.1); transition: .2s; }
        a { color:#1d90f5; text-decoration:none; font-weight:600; }
        a:hover { color:#00b7ff; text-decoration: underline; }
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
        <h2>Chi tiết Phiếu Nhập</h2>
        <div class="meta">
            <div><strong>Mã phiếu:</strong> ${importInvoice.importInvoiceID}</div>
            <div><strong>Ngày nhập:</strong> <fmt:formatDate value="${importInvoice.importDate}" pattern="yyyy-MM-dd"/></div>
            <div><strong>Nhà cung cấp:</strong> ${supplier != null ? supplier.name : importInvoice.supplierName}</div>
            <div style="margin-top:8px;"><a href="supplierImportInvoices?supplierID=${supplier != null ? supplier.supplierID : ''}&startDate=${startDate}&endDate=${endDate}">← Quay lại danh sách</a></div>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Mã sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="d" items="${importDetails}">
                    <tr>
                        <td>${d.productID}</td>
                        <td>${d.productName}</td>
                        <td>${d.quantity}</td>
                        <td><fmt:formatNumber value="${d.importPrice}" type="number" groupingUsed="true"/></td>
                        <td><fmt:formatNumber value="${d.subTotal}" type="number" groupingUsed="true"/></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty importDetails}">
                    <tr>
                        <td colspan="5" style="color:#9ca3af">Không có chi tiết sản phẩm.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>