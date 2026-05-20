<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>${product.name} - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section narrow">
    <article class="detail-card">
        <a class="plain-link" href="${pageContext.request.contextPath}/products">Back to products</a>
        <c:set var="imageSrc" value="${product.imageUrl}"/>
        <c:if test="${not fn:startsWith(imageSrc, 'http')}">
            <c:set var="imageSrc" value="${pageContext.request.contextPath}/${imageSrc}"/>
        </c:if>
        <img class="detail-photo" src="${imageSrc}" alt="${product.name}">
        <h1>${product.name}</h1>
        <p class="muted">${product.vehicleType} · ${product.categoryName} · ${product.partNumber}</p>
        <dl class="detail-list">
            <div>
                <dt>Brand</dt>
                <dd>${product.brand}</dd>
            </div>
            <div>
                <dt>Rating</dt>
                <dd><span class="star-rating">${rating}</span></dd>
            </div>
            <div>
                <dt>Price</dt>
                <dd>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></dd>
            </div>
            <div>
                <dt>Availability</dt>
                <dd>
                    <c:choose>
                        <c:when test="${product.stockQuantity le 0}">Out of stock</c:when>
                        <c:otherwise>${product.stockQuantity} in stock</c:otherwise>
                    </c:choose>
                </dd>
            </div>
        </dl>
        <h2 class="plain-title">Product Description</h2>
        <p>${product.description}</p>
        <c:choose>
            <c:when test="${product.stockQuantity le 0}">
                <span class="stock out">Out of stock</span>
            </c:when>
            <c:otherwise>
                <form method="post" action="${pageContext.request.contextPath}/cart/add" class="inline-form">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" min="1" max="${product.stockQuantity}" value="1">
                    <button class="button small" type="submit">Add to Cart</button>
                </form>
            </c:otherwise>
        </c:choose>
    </article>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
