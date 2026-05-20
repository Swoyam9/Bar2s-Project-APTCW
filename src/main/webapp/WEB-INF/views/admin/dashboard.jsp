<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <h1>Admin Dashboard</h1>
        <a class="button small" href="${pageContext.request.contextPath}/admin/products/add">Add Product</a>
    </div>
    <div class="dashboard-grid">
        <div class="stat-card"><span>Total Products</span><strong class="stat-number">${fn:length(products)}</strong></div>
        <div class="stat-card"><span>Low Stock</span><strong class="stat-number"><c:set var="low" value="0"/><c:forEach var="p" items="${products}"><c:if test="${p.stockQuantity lt 10}"><c:set var="low" value="${low + 1}"/></c:if></c:forEach>${low}</strong></div>
        <div class="stat-card"><span>Categories</span><strong class="stat-number">5</strong></div>
        <div class="stat-card"><span>Revenue</span><strong class="stat-number">Nrs <fmt:formatNumber value="${revenue}" minFractionDigits="2"/></strong></div>
        <div class="stat-card wide-stat"><span>Highest Selling Product</span><strong class="stat-number">${topSellingProduct}</strong></div>
    </div>

    <div class="section-title compact">
        <h2>Orders</h2>
        <span class="muted">${fn:length(orders)} orders</span>
    </div>
    <c:choose>
        <c:when test="${empty orders}">
            <div class="empty-state">No orders have been placed yet.</div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr><th>Order ID</th><th>Customer</th><th>Items</th><th>Total</th><th>Status</th><th>Date</th></tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>#${order.id}</td>
                        <td>${order.customerName}</td>
                        <td>
                            <c:forEach var="item" items="${order.items}" varStatus="loop">
                                ${item.productName} x ${item.quantity}<c:if test="${not loop.last}"><br></c:if>
                            </c:forEach>
                        </td>
                        <td>Nrs <fmt:formatNumber value="${order.totalAmount}" minFractionDigits="2"/></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/admin/orders/status" class="status-form">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <select name="status">
                                    <option value="CONFIRMED" ${order.status eq 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                                    <option value="ON_PROCESS" ${order.status eq 'ON_PROCESS' ? 'selected' : ''}>On-process</option>
                                    <option value="DELIVERED" ${order.status eq 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                                </select>
                                <button class="button small" type="submit">Update</button>
                            </form>
                        </td>
                        <td>${order.orderDate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
