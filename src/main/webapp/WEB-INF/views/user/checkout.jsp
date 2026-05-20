<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell">
    <section class="auth-card">
        <h1>Checkout</h1>
        <p class="summary">Order total: <strong>Nrs <fmt:formatNumber value="${total}" minFractionDigits="2"/></strong></p>
        <form method="post" action="${pageContext.request.contextPath}/order/place" class="form">
            <label>Delivery address
                <textarea name="shippingAddress" rows="4" required>${defaultAddress}</textarea>
            </label>
            <p class="password-hint">You can keep this address or enter another delivery address before placing the order.</p>
            <button class="button" type="submit">Place Order</button>
        </form>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
