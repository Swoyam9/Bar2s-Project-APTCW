<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Orders - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section narrow">
    <h1>My Orders</h1>
    <c:choose>
        <c:when test="${empty orders}">
            <div class="empty-state">You have not placed any orders yet.</div>
        </c:when>
        <c:otherwise>
            <c:forEach var="order" items="${orders}">
                <article class="order-card">
                    <div class="section-title compact">
                        <h2>Order #${order.id}</h2>
                        <span class="badge">${order.displayStatus}</span>
                    </div>
                    <p class="muted">${order.shippingAddress}</p>
                    <table class="table">
                        <tbody>
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <td>${item.productName}<br><span class="muted">${item.partNumber}</span></td>
                                <td>${item.quantity}</td>
                                <td>Nrs <fmt:formatNumber value="${item.subtotal}" minFractionDigits="2"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <strong>Total: Nrs <fmt:formatNumber value="${order.totalAmount}" minFractionDigits="2"/></strong>
                </article>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
