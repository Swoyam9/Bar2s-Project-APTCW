<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recent Products - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <div>
            <h1>Recent Products</h1>
            <p class="muted">Latest spare parts added to the store.</p>
        </div>
        <a class="button small" href="${pageContext.request.contextPath}/admin/products/add">Add Product</a>
    </div>

    <c:choose>
        <c:when test="${empty products}">
            <div class="empty-state">No products are available.</div>
        </c:when>
        <c:otherwise>
            <table class="table admin-table">
                <thead>
                <tr><th>Name</th><th>Brand</th><th>Category</th><th>Vehicle</th><th>Part No.</th><th>Price</th><th>Stock</th><th>Status</th></tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td><strong>${product.name}</strong></td>
                        <td>${product.brand}</td>
                        <td>${product.categoryName}</td>
                        <td>${product.vehicleType}</td>
                        <td>${product.partNumber}</td>
                        <td>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></td>
                        <td>${product.stockQuantity}</td>
                        <td>
                            <c:choose>
                                <c:when test="${product.stockQuantity gt 0}">In Stock</c:when>
                                <c:otherwise>Out of Stock</c:otherwise>
                            </c:choose>
                        </td>
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
