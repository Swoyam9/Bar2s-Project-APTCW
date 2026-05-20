<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="currentUri" value="${pageContext.request.requestURI}" scope="request"/>
<header class="site-header">
    <a class="brand" href="${pageContext.request.contextPath}${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN' ? '/admin/dashboard' : '/home'}">
        <img src="${pageContext.request.contextPath}/images/bar2s-logo.png" alt="BAR2S Automobile Parts">
        <span>BAR2S</span>
    </a>
    <nav class="nav">
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role ne 'ADMIN' && not fn:contains(currentUri, '/home')}">
            <a href="${pageContext.request.contextPath}/home">Home</a>
        </c:if>
        <c:if test="${not hideLoginNav && not hideProductsNav && (empty sessionScope.authUser || sessionScope.authUser.role ne 'ADMIN') && not fn:contains(currentUri, '/products') && not fn:contains(currentUri, '/product') && not fn:contains(currentUri, '/register')}">
            <a href="${pageContext.request.contextPath}/products">Products</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role ne 'ADMIN' && not fn:contains(currentUri, '/about')}">
            <a href="${pageContext.request.contextPath}/about">About Us</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN' && not fn:contains(currentUri, '/admin/dashboard')}">
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN' && not fn:contains(currentUri, '/admin/products')}">
            <a href="${pageContext.request.contextPath}/admin/products">Manage Products</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN' && not fn:contains(currentUri, '/admin/recent-products')}">
            <a href="${pageContext.request.contextPath}/admin/recent-products">Recent Products</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN' && not fn:contains(currentUri, '/admin/users')}">
            <a href="${pageContext.request.contextPath}/admin/users">Users</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role ne 'ADMIN' && not fn:contains(currentUri, '/cart')}">
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role ne 'ADMIN' && not fn:contains(currentUri, '/orders')}">
            <a href="${pageContext.request.contextPath}/orders">Orders</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role ne 'ADMIN' && not fn:contains(currentUri, '/profile')}">
            <a href="${pageContext.request.contextPath}/profile">Profile</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role ne 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:if>
        <c:if test="${empty sessionScope.authUser}">
            <c:if test="${not hideLoginNav && not fn:contains(currentUri, '/login')}">
                <a href="${pageContext.request.contextPath}/login">Login</a>
            </c:if>
            <c:if test="${not fn:contains(currentUri, '/register')}">
                <a class="button small" href="${pageContext.request.contextPath}/register">Register</a>
            </c:if>
        </c:if>
    </nav>
</header>

<c:if test="${not empty sessionScope.success}">
    <div class="alert success">${sessionScope.success}</div>
    <c:remove var="success" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.error}">
    <div class="alert error">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>
<c:if test="${not empty success}">
    <div class="alert success">${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert error">${error}</div>
</c:if>
