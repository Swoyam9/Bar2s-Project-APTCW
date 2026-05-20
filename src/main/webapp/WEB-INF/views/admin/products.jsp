<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:if test="${empty param.embedded}">
<!DOCTYPE html>
<html>
<head>
    <title>Manage Products - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <h1>Manage Products</h1>
        <a class="button small" href="${pageContext.request.contextPath}/admin/products/add">Add Product</a>
    </div>
</c:if>
    <table class="table admin-table">
        <thead>
        <tr><th>Name</th><th>Category</th><th>Vehicle</th><th>Part No.</th><th>Price</th><th>Stock</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td><strong>${product.name}</strong><br><span class="muted">${product.brand}</span></td>
                <td>${product.categoryName}</td>
                <td>${product.vehicleType}</td>
                <td>${product.partNumber}</td>
                <td>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></td>
                <td>${product.stockQuantity}</td>
                <td>
                    <div class="actions">
                    <a class="button ghost small" href="${pageContext.request.contextPath}/admin/products/edit?id=${product.id}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/admin/products/delete">
                        <input type="hidden" name="id" value="${product.id}">
                        <button class="button danger small" type="submit">Delete</button>
                    </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<c:if test="${empty param.embedded}">
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
</c:if>
