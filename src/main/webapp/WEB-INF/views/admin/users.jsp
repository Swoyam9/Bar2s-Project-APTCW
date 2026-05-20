<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registered Users - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <h1>Registered Users</h1>
        <span class="muted">${fn:length(users)} users</span>
    </div>
    <table class="table admin-table">
        <thead>
        <tr><th>Name</th><th>Email</th><th>Phone</th><th>Address</th><th>Role</th><th>Joined</th><th>Action</th></tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.fullName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.address}</td>
                <td>${user.role}</td>
                <td>${user.createdAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.role eq 'ADMIN'}">
                            <span class="muted">Protected</span>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="${pageContext.request.contextPath}/admin/users/delete" class="remove-form">
                                <input type="hidden" name="id" value="${user.id}">
                                <button class="button danger small" type="submit">Delete</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
