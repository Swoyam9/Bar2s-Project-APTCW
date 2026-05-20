package com.ecommerce.queries;

public final class Queries {
    private Queries() {
    }

    public static final String INSERT_USER =
            "INSERT INTO users (full_name, email, phone, address, password_hash, password_salt, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String FIND_USER_BY_REMEMBER_TOKEN =
            "SELECT * FROM users WHERE remember_token = ? AND remember_token_expiry > NOW()";
    public static final String LIST_USERS =
            "SELECT * FROM users ORDER BY created_at DESC";
    public static final String DELETE_USER_ORDERS =
            "DELETE FROM orders WHERE user_id = ?";
    public static final String DELETE_USER =
            "DELETE FROM users WHERE id = ? AND role <> 'ADMIN'";
    public static final String UPDATE_REMEMBER_TOKEN =
            "UPDATE users SET remember_token = ?, remember_token_expiry = DATE_ADD(NOW(), INTERVAL 7 DAY) WHERE id = ?";
    public static final String CLEAR_REMEMBER_TOKEN =
            "UPDATE users SET remember_token = NULL, remember_token_expiry = NULL WHERE id = ?";
    public static final String UPDATE_PROFILE_PHOTO =
            "UPDATE users SET profile_photo = ? WHERE id = ?";

    public static final String LIST_PRODUCTS =
            "SELECT p.*, c.name AS category_name FROM products p JOIN categories c ON p.category_id = c.id ORDER BY p.id DESC";
    public static final String LIST_FILTERED_PRODUCTS =
            "SELECT p.*, c.name AS category_name FROM products p JOIN categories c ON p.category_id = c.id " +
            "WHERE (? IS NULL OR p.vehicle_type = ?) " +
            "AND (? IS NULL OR LOWER(p.name) LIKE ? OR LOWER(p.brand) LIKE ? OR LOWER(p.part_number) LIKE ? OR LOWER(p.description) LIKE ?) " +
            "ORDER BY p.id DESC";
    public static final String FIND_PRODUCT_BY_ID =
            "SELECT p.*, c.name AS category_name FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
    public static final String INSERT_PRODUCT =
            "INSERT INTO products (category_id, name, brand, part_number, vehicle_type, description, price, stock_quantity, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_PRODUCT =
            "UPDATE products SET category_id=?, name=?, brand=?, part_number=?, vehicle_type=?, description=?, price=?, stock_quantity=? WHERE id=?";
    public static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    public static final String LIST_CATEGORIES = "SELECT * FROM categories ORDER BY name";

    public static final String CREATE_ORDER =
            "INSERT INTO orders (user_id, total_amount, status, shipping_address) VALUES (?, ?, 'CONFIRMED', ?)";
    public static final String INSERT_ORDER_ITEM =
            "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
    public static final String REDUCE_STOCK =
            "UPDATE products SET stock_quantity = stock_quantity - ? WHERE id = ? AND stock_quantity >= ?";
    public static final String LIST_ORDERS_BY_USER =
            "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
    public static final String LIST_ALL_ORDERS =
            "SELECT o.*, u.full_name AS customer_name FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";
    public static final String UPDATE_ORDER_STATUS =
            "UPDATE orders SET status = ? WHERE id = ?";
    public static final String LIST_ORDER_ITEMS =
            "SELECT oi.*, p.name AS product_name, p.part_number FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
    public static final String TOTAL_REVENUE =
            "SELECT COALESCE(SUM(total_amount), 0) AS revenue FROM orders";
    public static final String TOP_SELLING_PRODUCT =
            "SELECT p.name, COALESCE(SUM(oi.quantity), 0) AS sold_count " +
            "FROM order_items oi JOIN products p ON oi.product_id = p.id " +
            "GROUP BY p.id, p.name ORDER BY sold_count DESC LIMIT 1";
}
