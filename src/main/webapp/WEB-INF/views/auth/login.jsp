<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - AutoSpares</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:set var="hideLoginNav" value="true" scope="request"/>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell login-page">
    <section class="auth-card">
        <p class="eyebrow form-eyebrow">Welcome back</p>
        <h1>Login</h1>
        <form method="post" action="${pageContext.request.contextPath}/login" class="form">
            <label>Email
                <input type="email" name="email" required>
            </label>
            <label>Password
                <input class="password-field" type="password" name="password" autocomplete="current-password" required>
            </label>
            <div class="login-options">
                <label class="checkbox">
                    <input id="showPasswords" type="checkbox">
                    Show password
                </label>
                <label class="checkbox">
                    <input type="checkbox" name="remember"> Remember me
                </label>
            </div>
            <a class="plain-link forgot-password" href="${pageContext.request.contextPath}/forgot-password">Forgot password?</a>
            <button class="button" type="submit">Login</button>
        </form>
        <p class="muted">New customer? <a class="plain-link" href="${pageContext.request.contextPath}/register">Create an account</a></p>
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
