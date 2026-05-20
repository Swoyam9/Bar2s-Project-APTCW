package com.ecommerce.dao;

import com.ecommerce.models.User;
import com.ecommerce.queries.Queries;
import com.ecommerce.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public boolean register(User user) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.INSERT_USER)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getPasswordSalt());
            ps.setString(7, user.getRole() == null ? "USER" : user.getRole());
            return ps.executeUpdate() == 1;
        }
    }

    public User findByEmail(String email) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.FIND_USER_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    public User findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.FIND_USER_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    public User findByRememberToken(String token) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.FIND_USER_BY_REMEMBER_TOKEN)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.LIST_USERS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }
        return users;
    }

    public boolean deleteUser(int userId) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement deleteOrders = con.prepareStatement(Queries.DELETE_USER_ORDERS);
                 PreparedStatement deleteUser = con.prepareStatement(Queries.DELETE_USER)) {
                deleteOrders.setInt(1, userId);
                deleteOrders.executeUpdate();
                deleteUser.setInt(1, userId);
                boolean deleted = deleteUser.executeUpdate() == 1;
                con.commit();
                return deleted;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public void saveRememberToken(int userId, String token) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.UPDATE_REMEMBER_TOKEN)) {
            ps.setString(1, token);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void clearRememberToken(int userId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.CLEAR_REMEMBER_TOKEN)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    public void updateProfilePhoto(int userId, String profilePhoto) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.UPDATE_PROFILE_PHOTO)) {
            ps.setString(1, profilePhoto);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPasswordSalt(rs.getString("password_salt"));
        user.setRole(rs.getString("role"));
        user.setProfilePhoto(rs.getString("profile_photo"));
        if (rs.getTimestamp("created_at") != null) {
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return user;
    }
}
