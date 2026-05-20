<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>About Us - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section about-section">
    <h1 class="plain-title team-heading">Meet Our Team</h1>
    <section class="team-grid">
        <article class="team-card">
            <img src="${pageContext.request.contextPath}/images/Aashika.jpeg" alt="Aashika Ranabhat">
            <h2>Aashika Ranabhat</h2>
            <p>Role: Frontend developer</p>
            <p>Address: Kaukhola, Pokhara</p>
            <p>Phone: +977 1234567890</p>
            <p>Email: aashikaranabhat@gmail.com</p>
        </article>
        <article class="team-card">
            <img src="${pageContext.request.contextPath}/images/Bipeen.jpeg" alt="Bipeen Baral">
            <h2>Bipeen Baral</h2>
            <p>Role: Web developer, Quality Assurance & Testing</p>
            <p>Address: Kaika Chowk-26, Pokhara</p>
            <p>Phone: +977 1234567890</p>
            <p>Email: Bipinbaral@gmail.com</p>
        </article>
        <article class="team-card">
            <img src="${pageContext.request.contextPath}/images/riyaz.jpg" alt="Riyaz Shrestha">
            <h2>Riyaz Shrestha</h2>
            <p>Role: Project Manager, Web developer</p>
            <p>Address: Shuklagandaki-4, Tanahun</p>
            <p>Phone: +977 1234567890</p>
            <p>Email: riyazstha@gmail.com</p>
        </article>
        <article class="team-card">
            <img src="${pageContext.request.contextPath}/images/Swoyam.jpeg" alt="Swoyam Pradhan">
            <h2>Swoyam Pradhan</h2>
            <p>Role: Web developer, Quality Assurance & Testing</p>
            <p>Address: Shuklagandaki-5, Tanahun</p>
            <p>Phone: +977 1234567890</p>
            <p>Email: swoyampradhan@gmail.com</p>
        </article>
        <article class="team-card">
            <img src="${pageContext.request.contextPath}/images/Rojan.jpeg" alt="Rojan Gurung">
            <h2>Rojan Gurung</h2>
            <p>Role: Designer</p>
            <p>Address: Miyapatan-13, Pokhara</p>
            <p>Phone: +977 1234567890</p>
            <p>Email: rojangrg@gmail.com</p>
        </article>
    </section>
    <section class="contact-section">
        <h2>Contact Us</h2>
        <p>Email: bar2s@gmail.com</p>
        <p>Phone: 9800000000</p>
        <p>Location: Pokhara, Nepal</p>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
