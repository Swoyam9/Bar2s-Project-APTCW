<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product - AutoSpares</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell">
    <section class="auth-card wide">
        <h1>Add Product</h1>
        <form method="post" action="${pageContext.request.contextPath}/admin/products/add" class="form grid-form" enctype="multipart/form-data">
            <label class="span-2">Product image
                <input type="file" name="productImage" accept="image/jpeg,image/png,image/gif">
            </label>
            <label>Category
                <select name="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.key}">${category.value}</option>
                    </c:forEach>
                </select>
            </label>
            <label>Vehicle category
                <select name="vehicleType" required>
                    <option value="Bike">Bike</option>
                    <option value="Car">Car</option>
                    <option value="Scooter">Scooter</option>
                </select>
            </label>
            <label>Name
                <input type="text" name="name" required>
            </label>
            <label>Brand
                <input type="text" name="brand" required>
            </label>
            <label>Part number
                <input type="text" name="partNumber" required>
            </label>
            <label>Price
                <input type="number" name="price" min="0.01" step="0.01" required>
            </label>
            <label>Stock quantity
                <input type="number" name="stockQuantity" min="0" required>
            </label>
            <label class="span-2">Description
                <textarea name="description" rows="4"></textarea>
            </label>
            <button class="button span-2" type="submit">Save Product</button>
        </form>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
