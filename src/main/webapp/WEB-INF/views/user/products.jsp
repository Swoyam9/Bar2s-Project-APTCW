<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <h1 class="plain-title">Spare Parts Catalog</h1>
        <span class="muted">${fn:length(products)} products</span>
    </div>
    <div class="catalog-layout">
        <aside class="filter-panel">
            <form method="get" action="${pageContext.request.contextPath}/products" class="form">
                <label>Search product
                    <input type="search" name="search" value="${search}" placeholder="Search parts">
                </label>
                <div>
                    <p class="filter-title">Vehicle Category</p>
                    <a class="${empty vehicleType ? 'active-filter' : ''}" href="${pageContext.request.contextPath}/products">All</a>
                    <a class="${vehicleType eq 'Bike' ? 'active-filter' : ''}" href="${pageContext.request.contextPath}/products?vehicleType=Bike">Bike</a>
                    <a class="${vehicleType eq 'Car' ? 'active-filter' : ''}" href="${pageContext.request.contextPath}/products?vehicleType=Car">Car</a>
                    <a class="${vehicleType eq 'Scooter' ? 'active-filter' : ''}" href="${pageContext.request.contextPath}/products?vehicleType=Scooter">Scooter</a>
                </div>
                <c:if test="${not empty vehicleType}">
                    <input type="hidden" name="vehicleType" value="${vehicleType}">
                </c:if>
                <button class="button" type="submit">Search</button>
            </form>
        </aside>
        <section>
            <c:choose>
                <c:when test="${empty products}">
                    <div class="empty-state">product not found</div>
                </c:when>
                <c:otherwise>
                    <div class="product-grid">
                        <c:forEach var="product" items="${products}">
                            <c:set var="imageSrc" value="${product.imageUrl}"/>
                            <c:if test="${not fn:startsWith(imageSrc, 'http')}">
                                <c:set var="imageSrc" value="${pageContext.request.contextPath}/${imageSrc}"/>
                            </c:if>
                            <article class="product-card">
                                <a href="${pageContext.request.contextPath}/product?id=${product.id}">
                                    <img class="product-photo" src="${imageSrc}" alt="${product.name}">
                                </a>
                                <h3><a class="product-link" href="${pageContext.request.contextPath}/product?id=${product.id}">${product.name}</a></h3>
                                <p>${product.description}</p>
                                <p class="muted">${product.vehicleType} · ${product.brand} · ${product.partNumber}</p>
                                <strong>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></strong>
                                <c:choose>
                                    <c:when test="${product.stockQuantity le 0}">
                                        <span class="stock out">Out of stock</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="stock">${product.stockQuantity} in stock</span>
                                        <form method="post" action="${pageContext.request.contextPath}/cart/add" class="inline-form">
                                            <input type="hidden" name="productId" value="${product.id}">
                                            <input type="number" name="quantity" min="1" max="${product.stockQuantity}" value="1">
                                            <button class="button small" type="submit">Add</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </article>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
