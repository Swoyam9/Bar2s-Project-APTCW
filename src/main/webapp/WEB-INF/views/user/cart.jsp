<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cart - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section narrow">
    <div class="section-title">
        <h1>Your Cart</h1>
        <a class="plain-link" href="${pageContext.request.contextPath}/products">Continue shopping</a>
    </div>
    <c:choose>
        <c:when test="${empty cart}">
            <div class="empty-state">Your cart is empty.</div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr><th>Product</th><th>Qty</th><th>Price</th><th>Subtotal</th><th>Action</th></tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cart}">
                    <tr>
                        <td>${item.product.name}</td>
                        <td>${item.quantity}</td>
                        <td>Nrs <fmt:formatNumber value="${item.product.price}" minFractionDigits="2"/></td>
                        <td>Nrs <fmt:formatNumber value="${item.subtotal}" minFractionDigits="2"/></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/cart/remove" class="remove-form">
                                <input type="hidden" name="productId" value="${item.product.id}">
                                <button class="button ghost small" type="submit">Remove</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="total-row">
                <strong>Total: Nrs <fmt:formatNumber value="${total}" minFractionDigits="2"/></strong>
                <a class="button" href="${pageContext.request.contextPath}/checkout">Checkout</a>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
