<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main>
    <section class="hero">
        <div>
            <p class="eyebrow">Automobile Spare Parts</p>
            <h1>Find reliable parts for repairs, servicing, and upgrades.</h1>
            <a class="button" href="${pageContext.request.contextPath}/products">Browse Products</a>
        </div>
    </section>
    <section class="section">
        <div class="section-title">
            <h2 class="plain-title">Latest Parts</h2>
            <a class="plain-link" href="${pageContext.request.contextPath}/products">View all</a>
        </div>
        <div class="product-grid home-product-grid">
            <c:forEach var="product" items="${products}" end="5">
                <c:set var="imageSrc" value="${product.imageUrl}"/>
                <c:if test="${not fn:startsWith(imageSrc, 'http')}">
                    <c:set var="imageSrc" value="${pageContext.request.contextPath}/${imageSrc}"/>
                </c:if>
                <article class="product-card">
                    <a href="${pageContext.request.contextPath}/product?id=${product.id}">
                        <img class="product-photo" src="${imageSrc}" alt="${product.name}">
                    </a>
                    <h3><a class="product-link" href="${pageContext.request.contextPath}/product?id=${product.id}">${product.name}</a></h3>
                    <p>${product.vehicleType} · ${product.brand} · ${product.partNumber}</p>
                    <strong>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></strong>
                    <c:choose>
                        <c:when test="${product.stockQuantity le 0}">
                            <span class="stock out">Out of stock</span>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="${pageContext.request.contextPath}/cart/add">
                                <input type="hidden" name="productId" value="${product.id}">
                                <input type="hidden" name="quantity" value="1">
                                <button class="button small" type="submit">Add to Cart</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </article>
            </c:forEach>
        </div>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
