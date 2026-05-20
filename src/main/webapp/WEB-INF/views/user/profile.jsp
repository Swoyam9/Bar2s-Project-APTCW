<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section narrow">
    <article class="profile-card">
        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.authUser.profilePhoto}">
                    <img class="profile-photo" src="${pageContext.request.contextPath}/${sessionScope.authUser.profilePhoto}" alt="${sessionScope.authUser.fullName}">
                </c:when>
                <c:otherwise>
                    <div class="profile-placeholder">${fn:substring(sessionScope.authUser.fullName, 0, 1)}</div>
                </c:otherwise>
            </c:choose>
        </div>
        <div>
            <h1>${sessionScope.authUser.fullName}</h1>
            <p class="muted">${sessionScope.authUser.email}</p>
            <p>${sessionScope.authUser.phone}</p>
            <p>${sessionScope.authUser.address}</p>
            <form method="post" action="${pageContext.request.contextPath}/profile" enctype="multipart/form-data" class="form">
                <label>Profile photo
                    <input type="file" name="profilePhoto" accept="image/png,image/jpeg,image/gif" required>
                </label>
                <button class="button" type="submit">Upload Photo</button>
            </form>
        </div>
    </article>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
