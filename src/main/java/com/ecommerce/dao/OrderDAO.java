package com.ecommerce.dao;

import com.ecommerce.models.CartItem;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.queries.Queries;
import com.ecommerce.utils.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static boolean orderStatusValuesChecked;

    public int createOrder(int userId, String shippingAddress, List<CartItem> cartItems) throws SQLException {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new SQLException("Cart is empty");
        }

        BigDecimal total = cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        try (Connection con = DBConnection.getConnection()) {
            ensureOrderStatusValues(con);
            con.setAutoCommit(false);
            try (PreparedStatement orderPs = con.prepareStatement(Queries.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                orderPs.setInt(1, userId);
                orderPs.setBigDecimal(2, total);
                orderPs.setString(3, shippingAddress);
                orderPs.executeUpdate();

                int orderId;
                try (ResultSet keys = orderPs.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Could not create order");
                    }
                    orderId = keys.getInt(1);
                }

                for (CartItem item : cartItems) {
                    reduceStock(con, item);
                    insertOrderItem(con, orderId, item);
                }

                con.commit();
                return orderId;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public List<Order> findByUser(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            ensureOrderStatusValues(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.LIST_ORDERS_BY_USER)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Order order = new Order();
                        order.setId(rs.getInt("id"));
                        order.setUserId(rs.getInt("user_id"));
                        order.setTotalAmount(rs.getBigDecimal("total_amount"));
                        order.setStatus(rs.getString("status"));
                        order.setShippingAddress(rs.getString("shipping_address"));
                        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                        order.setItems(findItems(con, order.getId()));
                        orders.add(order);
                    }
                }
            }
        }
        return orders;
    }

    public List<Order> findAll() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            ensureOrderStatusValues(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.LIST_ALL_ORDERS);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapOrder(rs);
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setItems(findItems(con, order.getId()));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public boolean updateStatus(int orderId, String status) throws SQLException {
        if (!isAllowedStatus(status)) {
            return false;
        }
        try (Connection con = DBConnection.getConnection()) {
            ensureOrderStatusValues(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.UPDATE_ORDER_STATUS)) {
                ps.setString(1, status);
                ps.setInt(2, orderId);
                return ps.executeUpdate() == 1;
            }
        }
    }

    public BigDecimal getTotalRevenue() throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.TOTAL_REVENUE);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getBigDecimal("revenue") : BigDecimal.ZERO;
        }
    }

    public String getTopSellingProduct() throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.TOP_SELLING_PRODUCT);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("name") + " (" + rs.getInt("sold_count") + " sold)";
            }
            return "No sales yet";
        }
    }

    private void reduceStock(Connection con, CartItem item) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(Queries.REDUCE_STOCK)) {
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getProduct().getId());
            ps.setInt(3, item.getQuantity());
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Insufficient stock for " + item.getProduct().getName());
            }
        }
    }

    private void insertOrderItem(Connection con, int orderId, CartItem item) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(Queries.INSERT_ORDER_ITEM)) {
            ps.setInt(1, orderId);
            ps.setInt(2, item.getProduct().getId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getProduct().getPrice());
            ps.executeUpdate();
        }
    }

    private List<OrderItem> findItems(Connection con, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Queries.LIST_ORDER_ITEMS)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setPartNumber(rs.getString("part_number"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    private boolean isAllowedStatus(String status) {
        return "CONFIRMED".equals(status) || "ON_PROCESS".equals(status) || "DELIVERED".equals(status);
    }

    private void ensureOrderStatusValues(Connection con) throws SQLException {
        if (orderStatusValuesChecked) {
            return;
        }
        synchronized (OrderDAO.class) {
            if (orderStatusValuesChecked) {
                return;
            }
            try (Statement st = con.createStatement()) {
                st.executeUpdate("ALTER TABLE orders MODIFY status ENUM('PENDING','CONFIRMED','ON_PROCESS','SHIPPED','DELIVERED','CANCELLED') NOT NULL DEFAULT 'PENDING'");
            }
            orderStatusValuesChecked = true;
        }
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        return order;
    }
}
