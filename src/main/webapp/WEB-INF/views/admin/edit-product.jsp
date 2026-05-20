<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product - AutoSpares</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell">
    <section class="auth-card wide">
        <h1>Edit Product</h1>
        <form method="post" action="${pageContext.request.contextPath}/admin/products/edit" class="form grid-form">
            <input type="hidden" name="id" value="${product.id}">
            <label>Category
                <select name="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.key}" ${category.key eq product.categoryId ? 'selected' : ''}>${category.value}</option>
                    </c:forEach>
                </select>
            </label>
            <label>Vehicle category
                <select name="vehicleType" required>
                    <option value="Bike" ${product.vehicleType eq 'Bike' ? 'selected' : ''}>Bike</option>
                    <option value="Car" ${product.vehicleType eq 'Car' ? 'selected' : ''}>Car</option>
                    <option value="Scooter" ${product.vehicleType eq 'Scooter' ? 'selected' : ''}>Scooter</option>
                </select>
            </label>
            <label>Name
                <input type="text" name="name" value="${product.name}" required>
            </label>
            <label>Brand
                <input type="text" name="brand" value="${product.brand}" required>
            </label>
            <label>Part number
                <input type="text" name="partNumber" value="${product.partNumber}" required>
            </label>
            <label>Price
                <input type="number" name="price" min="0.01" step="0.01" value="${product.price}" required>
            </label>
            <label>Stock quantity
                <input type="number" name="stockQuantity" min="0" value="${product.stockQuantity}" required>
            </label>
            <label class="span-2">Description
                <textarea name="description" rows="4">${product.description}</textarea>
            </label>
            <button class="button span-2" type="submit">Update Product</button>
        </form>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
