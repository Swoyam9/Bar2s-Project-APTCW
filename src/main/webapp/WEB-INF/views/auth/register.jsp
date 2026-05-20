<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="hideProductsNav" value="true" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>Register - AutoSpares</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell">
    <section class="auth-card wide">
        <h1>Create Account</h1>
        <form method="post" action="${pageContext.request.contextPath}/register" class="form grid-form">
            <label>Full name
                <input type="text" name="fullName" required>
            </label>
            <label>Email
                <input type="email" name="email" required>
            </label>
            <label>Phone
                <input type="tel" name="phone" required>
            </label>
            <label>Address
                <input type="text" name="address">
            </label>
            <label>Password
                <input class="password-field" type="password" name="password" autocomplete="new-password" minlength="8" pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}" required>
            </label>
            <label>Confirm password
                <input class="password-field" type="password" name="confirmPassword" autocomplete="new-password" minlength="8" pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}" required>
            </label>
            <label class="checkbox span-2">
                <input id="showPasswords" type="checkbox">
                Show password
            </label>
            <p class="password-hint span-2">Password must contain one capital letter, one small letter, one number, and one special character.</p>
            <button class="button span-2" type="submit">Register</button>
        </form>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
<script>
    document.getElementById('showPasswords').addEventListener('change', function () {
        document.querySelectorAll('.password-field').forEach(function (field) {
            field.type = this.checked ? 'text' : 'password';
        }, this);
    });
</script>
</body>
</html>
